package cc.orangejuice.srs.module.service;


import cc.orangejuice.srs.module.client.ProgrammeFeignClient;
import cc.orangejuice.srs.module.client.StudentFeignClient;
import cc.orangejuice.srs.module.client.dto.*;
import cc.orangejuice.srs.module.domain.ModuleGrade;
import cc.orangejuice.srs.module.domain.StudentModuleSelection;
import cc.orangejuice.srs.module.repository.StudentModuleSelectionRepository;
import cc.orangejuice.srs.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.module.service.mapper.StudentModuleSelectionMapper;
import cc.orangejuice.srs.module.utils.PartTranscriptGenerator;
import cc.orangejuice.srs.module.utils.SemesterTranscriptGenerator;
import cc.orangejuice.srs.module.utils.TranscriptGenerator;
import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing StudentModuleSelection.
 */
@Service
@Transactional
public class StudentModuleSelectionService {

        private final Logger log = LoggerFactory.getLogger(StudentModuleSelectionService.class);

        private final StudentModuleSelectionRepository studentModuleSelectionRepository;

        private final StudentModuleSelectionMapper studentModuleSelectionMapper;

        private final ProgrammeFeignClient programmeFeignClient;

        private final StudentFeignClient studentFeignClient;

        private final ModuleGradeService moduleGradeService;

        private final ModuleService moduleService;


        public StudentModuleSelectionService(StudentModuleSelectionRepository studentModuleSelectionRepository,
                                             StudentModuleSelectionMapper studentModuleSelectionMapper,
                                             ProgrammeFeignClient programmeFeignClient,
                                             ModuleGradeService moduleGradeService,
                                             ModuleService moduleService,
                                             StudentFeignClient studentFeignClient) {
            this.studentModuleSelectionRepository = studentModuleSelectionRepository;
            this.studentModuleSelectionMapper = studentModuleSelectionMapper;
            this.programmeFeignClient = programmeFeignClient;
            this.moduleGradeService = moduleGradeService;
            this.moduleService = moduleService;
            this.studentFeignClient = studentFeignClient;
        }

        /**
         * Save a studentModuleSelection.
         *
         * @param studentModuleSelectionDTO the entity to save
         * @return the persisted entity
         */
        public StudentModuleSelectionDTO save(StudentModuleSelectionDTO studentModuleSelectionDTO) {
            log.debug("Request to save StudentModuleSelection : {}", studentModuleSelectionDTO);
            StudentModuleSelection studentModuleSelection = studentModuleSelectionMapper.toEntity(studentModuleSelectionDTO);
            studentModuleSelection = studentModuleSelectionRepository.save(studentModuleSelection);
            return studentModuleSelectionMapper.toDto(studentModuleSelection);
        }

        /**
         * Get all the studentModuleSelections.
         *
         * @param pageable the pagination information
         * @return the list of entities
         */
        @Transactional(readOnly = true)
        public Page<StudentModuleSelectionDTO> findAll(Pageable pageable) {
            log.debug("Request to get all StudentModuleSelections");
            return studentModuleSelectionRepository.findAll(pageable)
                .map(studentModuleSelectionMapper::toDto);
        }


        /**
         * Get one studentModuleSelection by id.
         *
         * @param id the id of the entity
         * @return the entity
         */
        @Transactional(readOnly = true)
        public Optional<StudentModuleSelectionDTO> findOne(Long id) {
            log.debug("Request to get StudentModuleSelection : {}", id);
            return studentModuleSelectionRepository.findById(id)
                .map(studentModuleSelectionMapper::toDto);
        }

        /**
         * Delete the studentModuleSelection by id.
         *
         * @param id the id of the entity
         */
        public void delete(Long id) {
            log.debug("Request to delete StudentModuleSelection : {}", id);
            studentModuleSelectionRepository.deleteById(id);
        }

        public void getSemesterTranscript(Long studentId, Integer academicYear, Integer academicSemester) throws FileNotFoundException, DocumentException {
            // step 1: data gathering
            log.debug("request to get semester transcript for the student {} in academicYear: {}, academicSemester: {} ",
                studentId, academicYear, academicYear, academicSemester);

            // get module selections
            List<StudentModuleSelectionDTO> studentModuleSelectionDTOS = findAllByStudentIdAcademicYearAcademicSemester(studentId, academicYear, academicSemester);
            log.debug("Finish getting studentModuleSelectionDTOS for generating semester transcript with the size of {}", studentModuleSelectionDTOS.size());

            // get module code (detail) for each module
            List<ModuleDTO> moduleDTOS = getModuleCodeByModuleName(studentModuleSelectionDTOS);
            log.debug("Finish getting moduleDTOS for generating semester transcript with the size of {}", moduleDTOS.size());

            // get student info (student number, student name, address, phone number)
            StudentDTO studentDTO = studentFeignClient.getStudent(studentId).getBody();
            log.debug("Finish getting studentSTO for generating semester transcript with the student name of {}", studentDTO.getFirstName());

            // get enroll detail
            StudentEnrollDTO studentEnrollDTO = studentFeignClient.getStudentEnrollDetail(studentId);
            log.debug("Finish getting the studentEnrollDTO for generating semester transcript with the status of {}", studentEnrollDTO.getStatus());

            // get programme detail
            ProgrammeDTO programmeDTO = programmeFeignClient.getProgramme(studentEnrollDTO.getForProgrammeId()).getBody();
            log.debug("finish getting the programme dto for generating semester transcript with the programme name of {}", programmeDTO.getName());


            // get QCA
            StudentProgressionDTO studentProgressionDTO = getQCAByStudentAndAcademicYearAndAcademicSemester(studentDTO, academicYear, academicSemester);
            log.debug("Finish getting QCA for generating semester transcript with the QCA of {}", studentProgressionDTO.getQca());

            // step 2: generate pdf file (semester transcript)
            TranscriptGenerator tg = new SemesterTranscriptGenerator(studentModuleSelectionDTOS, moduleDTOS, studentDTO, studentEnrollDTO, programmeDTO, studentProgressionDTO);
            tg.generate();

        }

        public void getPartTranscript(Long studentId, Integer academicYear, String partNo) throws FileNotFoundException, DocumentException {
            log.debug("REST request to get part transcript for the student {} in academicYear: {], part: {} ", studentId, academicYear, partNo);

            // step 1: data gathering

            // get "part" info
            List<ProgrammePropDTO> programmePropDTOS = programmeFeignClient.getProgrammePropsByYearAndPart(academicYear, partNo);
            log.debug("Finish getting the programmeProps for generating Part Transcript with the size of {}", programmePropDTOS.size());

            //get module selection
            List<StudentModuleSelectionDTO> studentModuleSelectionDTOS = getStudentModulesSelectionsForPart(programmePropDTOS, studentId, academicYear);
            log.debug("Finish gettin the student result lists for generating Part Transcript with the size of {}", studentModuleSelectionDTOS.size());

            // get module code (detail) for each module
            List<ModuleDTO> moduleDTOS = getModuleCodeByModuleName(studentModuleSelectionDTOS);
            log.debug("Finish getting moduleDTOS for generating Part Transcript with the size of {} for generating Part Transcript", moduleDTOS.size());

            // get student info (student number, student name, address, phone number)
            StudentDTO studentDTO = studentFeignClient.getStudent(studentId).getBody();
            log.debug("Finish getting studentSTO for generating Part Transcript with the student name of {}", studentDTO.getFirstName());

            // get enroll detail
            StudentEnrollDTO studentEnrollDTO = studentFeignClient.getStudentEnrollDetail(studentId);
            log.debug("Finish getting the studentEnrollDTO for generating Part Transcript with the status of {}", studentEnrollDTO.getStatus());

            // get programme detail
            ProgrammeDTO programmeDTO = programmeFeignClient.getProgramme(studentEnrollDTO.getForProgrammeId()).getBody();
            log.debug("finish getting the programme dto for generating Part Transcript with the programme name of {}", programmeDTO.getName());

            // get QCA
            List<StudentProgressionDTO> studentProgressionDTOS = studentFeignClient.getProgressionInfoByAcademicYear(studentId, academicYear);
            log.debug("Finish getting QCA for generating Part Transcript with the size of {}",studentProgressionDTOS.size());

            // step 2: generate pdf file
            TranscriptGenerator tg = new PartTranscriptGenerator(programmePropDTOS, studentModuleSelectionDTOS, moduleDTOS, studentDTO, studentEnrollDTO, programmeDTO, studentProgressionDTOS);
            tg.generate();

        }

        private List<StudentModuleSelectionDTO> getStudentModulesSelectionsForPart(List<ProgrammePropDTO> programmePropDTOS, Long studentId, Integer academicYear) {
            List<StudentModuleSelectionDTO> studentModuleSelectionDTOS = new ArrayList<>();
            for(ProgrammePropDTO programmePropDTO : programmePropDTOS) {
                for(StudentModuleSelectionDTO studentModuleSelectionDTO : studentModuleSelectionMapper.toDto(studentModuleSelectionRepository.findAllByStudentIdAndAcademicYearAndYearNo(studentId, academicYear, programmePropDTO.getForYearNo()))){
                    studentModuleSelectionDTOS.add(studentModuleSelectionDTO);
                }
            }
            return studentModuleSelectionDTOS;
        }

    /**
     * get qca from svcStudent for transcript
     * @param studentDTO
     * @param academicYear
     * @param academicSemester
     * @return
     */
        private StudentProgressionDTO getQCAByStudentAndAcademicYearAndAcademicSemester(StudentDTO studentDTO, Integer academicYear, Integer academicSemester) {
            log.debug("request to get qca for forming transcript for student {} at academicYear {} and academicSemester {}", studentDTO.getFirstName(), academicYear, academicSemester);
            return studentFeignClient.getProgressionInfo(studentDTO.getId(), academicYear, academicSemester);
        }

    /**
     * get module code from svcModule for transcript
     * @param studentModuleSelectionDTOS for getting module ids
     * @return
     */
        private List<ModuleDTO> getModuleCodeByModuleName(List<StudentModuleSelectionDTO> studentModuleSelectionDTOS) {
            log.debug("request to get module code from student client for forming transcript");
            List<Long> moduleIds = getModuleIdByModuleSelections(studentModuleSelectionDTOS);
            List<ModuleDTO> moduleDTOS = new ArrayList<>();

            for(int i = 0; i < moduleIds.size(); i++) {
                moduleDTOS.add(moduleService.findOne(moduleIds.get(i)).get());
            }
            return moduleDTOS;
        }

    /**
     * get module ids for getting module code
     * @param studentModuleSelectionDTOS
     * @return list of module ids
     */
        private List<Long> getModuleIdByModuleSelections(List<StudentModuleSelectionDTO> studentModuleSelectionDTOS) {
            log.debug("request to get module id for forming the transcript");
            List<Long> moduleIds = new ArrayList<>();
            for(int i = 0; i < studentModuleSelectionDTOS.size(); i++) {
                moduleIds.add(studentModuleSelectionDTOS.get(i).getModuleId());
            }
            return moduleIds;
        }

    /**
     * get all results for transcript
     * @param studentId
     * @param academicYear
     * @param academicSemester
     * @return
     */
        private List<StudentModuleSelectionDTO> findAllByStudentIdAcademicYearAcademicSemester(Long studentId, Integer academicYear, Integer academicSemester) {
            log.debug("Request to get selections for Student: {} at academicYear: {}, academicSemester: {} for forming transcripts ",
                studentId, academicYear, academicSemester);
            List<StudentModuleSelection> studentModuleSelections = studentModuleSelectionRepository.findAllByStudentIdAndAcademicYearAndAcademicSemester(studentId, academicYear, academicSemester);
            List<StudentModuleSelectionDTO> studentModuleSelectionDTOS = studentModuleSelectionMapper.toDto(studentModuleSelections);
            return studentModuleSelectionDTOS;
        }


        // submit grade
        public void updateGradeBySelectionIdAndGrade(Long selectionId, String grade) {
            log.debug("request to update id: {} StudentModuleSelections with grade {}", selectionId, grade);
            Double attemptedHour;
            ModuleGrade moduleGrade = getModuleGrade(grade);
            attemptedHour = getAttemptedHours(selectionId);
            updateQCSByGrade(selectionId, moduleGrade, attemptedHour);
        }

        public void updateQCSByGrade(Long selectionId, ModuleGrade moduleGrade, Double attemptedHour) {
            log.debug("Request to update QPV by grade: {}, attemptedHour: {}", moduleGrade.getName(), attemptedHour);
            double qcs;

            qcs = moduleGrade.getQpv() * attemptedHour;
            updateQCS(selectionId, moduleGrade, qcs, attemptedHour);
        }

        public ModuleGrade getModuleGrade(String grade) {
            log.debug("request to check grade {}", grade);
            ModuleGrade moduleGrade = moduleGradeService.getModuleGradeByName(grade);
            if(moduleGrade != null) {
                log.debug("Module Grade found !");
                return moduleGrade;
            }
            return null;
        }


        // submit mark
        public void updateMarkBySelectionIdAndMark(Long selectionId, Double mark) {
            log.debug("Request to update id: {} StudentModuleSelections with mark {}", selectionId, mark);
            Double attemptedHour;
            studentModuleSelectionRepository.updateMarksById(selectionId, mark);
            attemptedHour = getAttemptedHours(selectionId);
            updateQCSByMark(selectionId, mark, attemptedHour);
        }

        public void checkIfSemesterIsEnd(Long selectionId) {
            log.debug("Request to get Semester No for selection Id: {}", selectionId);

            Boolean haveAllMarks = true;

            // get a tuple by selectionId for getting studentId and semesterNo
            Optional<StudentModuleSelectionDTO> studentModuleSelectionDTO = findOne(selectionId);

            log.debug("Request to get result list for student {} with qcs : {} at SemesterNo {}", studentModuleSelectionDTO.get().getStudentId(), studentModuleSelectionDTO.get().getQcs(), studentModuleSelectionDTO.get().getSemesterNo());
            List<StudentModuleSelection> resultsList = studentModuleSelectionRepository.findAllByStudentIdAndSemesterNo(studentModuleSelectionDTO.get().getStudentId(), studentModuleSelectionDTO.get().getSemesterNo());
            // check if a student has all the marks for a semester
            for(StudentModuleSelection studentModuleSelection : resultsList) {
                if(studentModuleSelection.getQcs() == null) {
                    haveAllMarks = false;
                    break;
                }
            }
            // (if it is the end of the semester)
            if(haveAllMarks) {
                // send all the results that less or equal to this academic semester
                log.debug("It is the end of semester {}, Request to get all the results that that less or equal to the academic semester {} for student {}", studentModuleSelectionDTO.get().getAcademicSemester(), studentModuleSelectionDTO.get().getAcademicSemester(), studentModuleSelectionDTO.get().getStudentId());
                List<StudentModuleSelection> allResultsList = studentModuleSelectionRepository.findAllByStudentIdAndAcademicSemester(studentModuleSelectionDTO.get().getStudentId(), studentModuleSelectionDTO.get().getAcademicSemester());
                List<StudentModuleSelectionDTO> allResultsDTO = studentModuleSelectionMapper.toDto(allResultsList);
                log.debug("Request to calculate QCA in Student micro-service with a list of {} results", allResultsList.size());
                // todo send to student micro-service for calculating the QCA with resultList, academicYear and academicSemester
                 studentFeignClient.calculateQCA(resultsList.get(resultsList.size() - 1).getAcademicYear(), resultsList.get(resultsList.size() - 1).getAcademicSemester(), allResultsDTO);


            }
        }

        // get results by acdemic year (2014) and yearNo (1 2 3 4...)
        public List<StudentModuleSelectionDTO> findAllStudentSelectionsByYear(Long studentId, Integer academicYear, Integer yearNo) {
            log.debug("Request to get Year selections for student: {} at academicYear: {}, yearNo: {}",
                studentId, academicYear, yearNo);
            List<StudentModuleSelection> studentModuleSelections=  studentModuleSelectionRepository.findAllByStudentIdAndAcademicYearAndYearNo(studentId, academicYear, yearNo);
            List<StudentModuleSelectionDTO> returnList =  studentModuleSelectionMapper.toDto(studentModuleSelections);
            return returnList;
        }

        public void clearAllMarks() {
            log.debug("Request to clear all the marks for demo");
            studentModuleSelectionRepository.updateAllByCreditHourAndMarksAndQcsAAndsAndStudentModuleGradeType();
    }

        private Double getAttemptedHours(Long selectionId) {
        log.debug("Request to get credit from a module in the selection {}", selectionId);
        Optional<StudentModuleSelection> studentModuleSelection = studentModuleSelectionRepository.findById(selectionId);
        return studentModuleSelection.get().getModule().getCredit() * getFactor(studentModuleSelection);
    }

        private Double getFactor(Optional<StudentModuleSelection> studentModuleSelection) {
        log.debug("Request to get factor from academicYear: {}, academicSemester: {}, yearNumber: {}, SemesterNumber: {}",
            studentModuleSelection.get().getAcademicYear(),
            studentModuleSelection.get().getAcademicSemester(),
            studentModuleSelection.get().getYearNo(),
            studentModuleSelection.get().getSemesterNo());

        List<ProgrammePropDTO> programmePropResponse = programmeFeignClient.getProgrammeProps("SEMESTER",
            studentModuleSelection.get().getAcademicYear(),
            studentModuleSelection.get().getYearNo(),
            studentModuleSelection.get().getSemesterNo(),
            "factor");



        return Double.parseDouble(programmePropResponse.get(0).getValue());

    }

        private void updateQCSByMark(Long selectionId, Double mark, Double attemptedHour) {
        log.debug("Request to update QPV and GradeName by Mark: {}, attemptedHour: {}", mark, attemptedHour);

        // get QPV and GradeName
        Double qcs;
        ModuleGrade moduleGradeResult = null;

        List<ModuleGrade> moduleGradeList = moduleGradeService.getAllModuleGradewithQcaAffected();
        log.debug("moduleGradeList result: {}", moduleGradeList.size());

        for(ModuleGrade moduleGrade : moduleGradeList) {
            if(mark >= moduleGrade.getLowMarks()) {
                moduleGradeResult = moduleGrade;
                break;
            }
        }

        qcs = moduleGradeResult.getQpv() * attemptedHour;
        updateQCS(selectionId, moduleGradeResult, qcs, attemptedHour);
    }

    public void updateQCS(Long selectionId, ModuleGrade moduleGrade, Double qcs, Double attemptedHour) {
        DecimalFormat df = new DecimalFormat("#.##");
        qcs = Double.parseDouble(df.format(qcs));
        log.debug("Request to update student result with selectionId: {}, gradeName: {}, QCS: {}, attemptedHour: {}", selectionId, moduleGrade.getName(), qcs, attemptedHour);
        studentModuleSelectionRepository.updateById(selectionId, moduleGrade, qcs, attemptedHour);
    }
}
