package cc.orangejuice.srs.module.service;


import cc.orangejuice.srs.module.client.ProgrammeFeignClient;
import cc.orangejuice.srs.module.domain.Module;
import cc.orangejuice.srs.module.domain.ModuleGrade;
import cc.orangejuice.srs.module.domain.StudentModuleSelection;
import cc.orangejuice.srs.module.repository.StudentModuleSelectionRepository;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.module.service.mapper.StudentModuleSelectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final ModuleGradeService moduleGradeService;


    public StudentModuleSelectionService(StudentModuleSelectionRepository studentModuleSelectionRepository,
                                         StudentModuleSelectionMapper studentModuleSelectionMapper,
                                         ProgrammeFeignClient programmeFeignClient,
                                         ModuleGradeService moduleGradeService) {
        this.studentModuleSelectionRepository = studentModuleSelectionRepository;
        this.studentModuleSelectionMapper = studentModuleSelectionMapper;
        this.programmeFeignClient = programmeFeignClient;
        this.moduleGradeService = moduleGradeService;
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


    public void updateMarkBySelectionIdAndMark(Long selectionId, Double mark) {
        log.debug("Request to update id: {} StudentModuleSelections with mark {}", selectionId, mark);
        Double creditHour;
        studentModuleSelectionRepository.updateMarksById(selectionId, mark);
        creditHour = getCreditHour(selectionId);
        updateQCS(selectionId, mark, creditHour);
    }


    // todo sub-goal get credit
    private Double getCreditHour(Long selectionId) {
        log.debug("Request to get credit from a module in the selection {}", selectionId);
        Optional<StudentModuleSelection> studentModuleSelection = studentModuleSelectionRepository.findById(selectionId);
        return studentModuleSelection.get().getModule().getCredit() * getFactor(studentModuleSelection);
    }

    // todo sub-goal getFactor
    private Double getFactor(Optional<StudentModuleSelection> studentModuleSelection) {
        log.debug("Request to get factor from academicYear: {}, academicSemester: {}, yearNumber: {}, SemesterNumber: {}",
            studentModuleSelection.get().getAcademicYear(),
            studentModuleSelection.get().getAcademicSemester(),
            studentModuleSelection.get().getYearNo(),
            studentModuleSelection.get().getSemesterNo());

        ResponseEntity programmeProp = programmeFeignClient.getProgrammeProp("SEMESTER",
            studentModuleSelection.get().getAcademicYear(),
            studentModuleSelection.get().getYearNo(),
            studentModuleSelection.get().getSemesterNo(),
            "factor");
        // todo how to get the factor
        log.debug("get factor : {} ",Double.parseDouble(programmeProp.getBody().toString()));
        return Double.parseDouble(programmeProp.getBody().toString());

    }

    // todo update QCS
    private void updateQCS(Long selectionId, Double mark, Double creditHour) {
        log.debug("Request to get QPV and GradeName by Mark: {}", mark);

        // get QPV and GradeName
        Double qcs;
        Double qpv= 0.00;
        String gradeName="";

        List<ModuleGrade> moduleGradeList = moduleGradeService.getAllModuleGradewithQcaAffected();

        for(ModuleGrade moduleGrade : moduleGradeList) {
            if(mark >= moduleGrade.getLowMarks()) {
                qpv = moduleGrade.getQpv();
                gradeName = moduleGrade.getName();
                return;
            }
        }
        qcs = qpv * creditHour;
        studentModuleSelectionRepository.updateByIdAndStudentModuleGradeTypeAndQcsAndCreditHour(selectionId, gradeName, qcs, creditHour);
    }


    public Optional<StudentModuleSelectionDTO> findOneByYearNoAndSemesterNoAndModule(Integer academic_year, Integer academic_semester, Integer yearNo, Integer semesterNo) {
        log.debug("Request to get studentModuleSelection for the student in academicYear : {}, academicSemester: {}, yearNo: {}, semesterNo: {} and module: {} ",
            academic_year, academic_semester, yearNo, semesterNo);
        return studentModuleSelectionRepository.findOneByAcademicYearAndAcademicSemesterAndYearNoAndSemesterNo(academic_year, academic_semester, yearNo, semesterNo)
            .map(studentModuleSelectionMapper::toDto);

    }

    // todo calculate semester QCA
    public Double getSemesterQCA(Long studentId, Integer academicYear, Integer yearNo, Integer semesterNo) {
        log.debug("Request to get Semester QCA for student: {} at academicYear: {}, yearNo: {} and semesterNo {}",
            studentId, academicYear, yearNo, semesterNo);
        Double semesterQca;
        Double cumulatedQCS = 0.00;
        Double cumulatedHours = 0.00;
        for(StudentModuleSelection studentModuleSelection: findAllStudentSelectionsBySemester(studentId, academicYear, yearNo, semesterNo)) {
            cumulatedQCS += studentModuleSelection.getQcs();
            cumulatedHours += studentModuleSelection.getCreditHour() - getNQH();
        }
        semesterQca = cumulatedQCS/cumulatedHours;
        return semesterQca;
    }

    private List<StudentModuleSelection> findAllStudentSelectionsBySemester(Long studentId, Integer academicYear, Integer yearNo, Integer semesterNo) {
        log.debug("Request to get Semester selections for student: {} at academicYear: {}, yearNo: {} and semesterNo {}",
            studentId, academicYear, yearNo, semesterNo);
        return studentModuleSelectionRepository.findAllByStudentIdAndAcademicYearAndYearNoAndSemesterNo(studentId, academicYear, yearNo, semesterNo);
    }

    private Double getNQH() {
        // to be improved later
        return 0.00;
    }

    // todo calculate cumulative QCA
    public Double getCumulativeQCA(Long studentId, Integer academicYear, Integer yearNo) {
        log.debug("REST request to get Cumulative QCA for student: {} at academicYear: {}, yearNo: {}",
            studentId, academicYear, yearNo);
        Double cumulativeQca;
        Double cumulatedQCS = 0.00;
        Double cumulatedHours = 0.00;
        for(StudentModuleSelection studentModuleSelection: findAllStudentSelectionsByYear(studentId, academicYear, yearNo)) {
            cumulatedQCS += studentModuleSelection.getQcs();
            cumulatedHours += studentModuleSelection.getCreditHour() - getNQH();
        }
        cumulativeQca = cumulatedQCS/cumulatedHours;
        return cumulativeQca;

    }

    private List<StudentModuleSelection> findAllStudentSelectionsByYear(Long studentId, Integer academicYear, Integer yearNo) {
        log.debug("Request to get Year selections for student: {} at academicYear: {}, yearNo: {}",
            studentId, academicYear, yearNo);
        return studentModuleSelectionRepository.findAllByStudentIdAndAcademicYearAndYearNo(studentId, academicYear, yearNo);
    }
}
