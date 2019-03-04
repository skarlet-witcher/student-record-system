package cc.orangejuice.srs.student.service;

import cc.orangejuice.srs.student.client.ProgrammeFeignClient;
import cc.orangejuice.srs.student.client.StudentModuleSelectionsFeignClient;
import cc.orangejuice.srs.student.client.dto.ProgrammePropDTO;
import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.Student;
import cc.orangejuice.srs.student.domain.StudentProgression;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.domain.enumeration.ProgressType;
import cc.orangejuice.srs.student.repository.StudentProgressionRepository;
import cc.orangejuice.srs.student.repository.StudentRepository;
import cc.orangejuice.srs.student.service.dto.StudentDTO;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;
import cc.orangejuice.srs.student.service.mapper.StudentMapper;
import cc.orangejuice.srs.student.service.mapper.StudentProgressionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.activation.ActivationDataFlavor;
import javax.swing.text.html.Option;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Service Implementation for managing StudentProgression.
 */
@Service
@Transactional
public class StudentProgressionService {

    private final Logger log = LoggerFactory.getLogger(StudentProgressionService.class);

    private final StudentProgressionRepository studentProgressionRepository;

    private final StudentProgressionMapper studentProgressionMapper;

    private final StudentModuleSelectionsFeignClient studentModuleSelectionsFeignClient;

    private final ProgrammeFeignClient programmeFeignClient;

    private final StudentService studentService;

    private final StudentMapper studentMapper;

    @Autowired
    private StudentRepository studentRepository;

    public StudentProgressionService(StudentProgressionRepository studentProgressionRepository,
                                     StudentProgressionMapper studentProgressionMapper,
                                     StudentModuleSelectionsFeignClient studentModuleSelectionsFeignClient,
                                     ProgrammeFeignClient programmeFeignClient,
                                     StudentService studentService,
                                     StudentMapper studentMapper) {
        this.studentProgressionRepository = studentProgressionRepository;
        this.studentProgressionMapper = studentProgressionMapper;
        this.studentModuleSelectionsFeignClient = studentModuleSelectionsFeignClient;
        this.programmeFeignClient = programmeFeignClient;
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    /**
     * Save a studentProgression.
     *
     * @param studentProgressionDTO the entity to save
     * @return the persisted entity
     */
    public StudentProgressionDTO save(StudentProgressionDTO studentProgressionDTO) {
        log.debug("Request to save StudentProgression : {}", studentProgressionDTO);
        StudentProgression studentProgression = studentProgressionMapper.toEntity(studentProgressionDTO);
        studentProgression = studentProgressionRepository.save(studentProgression);
        return studentProgressionMapper.toDto(studentProgression);
    }

    /**
     * Get all the studentProgressions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StudentProgressionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentProgressions");
        return studentProgressionRepository.findAll(pageable)
                .map(studentProgressionMapper::toDto);
    }


    /**
     * Get one studentProgression by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StudentProgressionDTO> findOne(Long id) {
        log.debug("Request to get StudentProgression : {}", id);
        return studentProgressionRepository.findById(id)
                .map(studentProgressionMapper::toDto);
    }

    /**
     * Delete the studentProgression by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentProgression : {}", id);
        studentProgressionRepository.deleteById(id);
    }


    public static void swapWorstModule(List<StudentModuleSelectionDTO> gradeOfThisStduent, int numberOfPossibleSwap) {
        for (int i = 0; i < numberOfPossibleSwap; i++) {
            gradeOfThisStduent.get(i).setQcs(12.0);
        }
    }


    private static Integer getNQH() {
        return 0;
    }


    public void calculateQCA(List<StudentModuleSelectionDTO> resultsList, Integer academicYear, Integer academicSemester) {
        log.debug("Request to calculate QCA for student: {}", resultsList.get(resultsList.size() - 1).getStudentId());


        // calculate semester QCA
        if(isDuplicateSemesterQCA(resultsList, academicYear, academicSemester)) return;
        Double semesterQCA = calculateSemesterQCA(resultsList, academicYear, academicSemester);
        insertSemesterQCAForStudent(semesterQCA, academicYear, academicSemester, resultsList.get(resultsList.size() - 1).getStudentId(), null);

        // if it is the end of the part
        Integer partNo = isEndOfPart(resultsList, academicYear);
        if (partNo > 0) {
            // if yes, calculate cumulative QCA and generate progression decision
            if(isDuplicateCumulativeQCA(resultsList, academicYear, partNo)) return;
            Double cumulativeQCA = calculateCumulativeQCA(resultsList);
            log.debug("ready to make progression decision with cumulative QCA: {} and academicYear: {}", cumulativeQCA, academicYear);
            ProgressDecision progressDecisionEnum = makeProgressionDecision(cumulativeQCA, resultsList);
            insertCumulativeQCAForStudent(cumulativeQCA, partNo, academicYear, academicSemester, resultsList.get(resultsList.size() - 1).getStudentId(), progressDecisionEnum);
        }

        // leave graduation for now


    }

    // how to distinguish part 1 and part 2 ?
    private Integer isEndOfPart(List<StudentModuleSelectionDTO> resultsList, Integer academicYear) {
        log.debug("Request to check if the result list is at the end of the part for academic Year: {}", academicYear);
        List<ProgrammePropDTO> partList = programmeFeignClient.getProgrammeProps("YEAR", academicYear, null, null, "part");
        Integer isEndOfPart = 0;

        for (int i = 0; i < partList.size(); i++) {
            // if the results end in the end of semester
            if (resultsList.get(resultsList.size() - 1).getYearNo() == partList.get(i).getForYearNo() &&
                    resultsList.get(resultsList.size() - 1).getSemesterNo() == 2) {
                // if this is the end of the part (if it has multiple parts)
                if (partList.get(i).getValue() != partList.get(i + 1).getValue()) {
                    isEndOfPart = Integer.parseInt(partList.get(i).getValue());
                    log.debug("It is end of part {}. And ready to calculate cumulative QCA", partList.get(i).getValue());
                    break;
                }
            }
        }
        return isEndOfPart;

    }

    private Double calculateSemesterQCA(List<StudentModuleSelectionDTO> resultsList, Integer academicYear, Integer academicSemester) {
        log.debug("Request to calculate semester QCA for student: {} in academic semester: {}", resultsList.get(resultsList.size() - 1).getStudentId(), academicSemester);

        Double semesterQCS = 0.00;
        Double attemptedHours = 0.00;
        for (StudentModuleSelectionDTO oneResult : resultsList) {
            if (oneResult.getAcademicYear().equals(academicYear) && oneResult.getAcademicSemester().equals(academicSemester)) {
                semesterQCS += oneResult.getQcs();
                attemptedHours += oneResult.getCreditHour() - getNQH();
            }
        }
        log.debug("semesterQCS is {} and attemptedHours is {}", semesterQCS, attemptedHours);
        log.debug("THe semester QCA for student {} at academicYear {} and academicSemester {} is {}", resultsList.get(0).getStudentId(), academicYear, academicSemester, semesterQCS / attemptedHours);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(semesterQCS / attemptedHours));
    }

    private void insertSemesterQCAForStudent(Double semesterQCA, Integer academicYear, Integer academicSemester, Long studentId, ProgressDecision progressDecisionEnum) {
        log.debug("request to insert Semester QCA for student: {} with QCA: {} in academic Year: {} and academic Semester: ", studentId, academicYear, academicSemester);

        StudentProgressionDTO studentProgressionDTO = new StudentProgressionDTO();
        studentProgressionDTO.setForAcademicYear(academicYear);
        studentProgressionDTO.setForAcademicSemester(academicSemester);
        studentProgressionDTO.setQca(semesterQCA);
        studentProgressionDTO.setStudentId(studentId);
        studentProgressionDTO.setProgressType(ProgressType.SEMESTER);
        if (progressDecisionEnum != null) studentProgressionDTO.setProgressDecision(progressDecisionEnum);
        log.debug("request to save student semester qca to student progression table. studentId: {}, academicYear: {}, academicSemester: {}, semesterQCA: {}",
                studentId, academicYear, academicSemester, semesterQCA);
        save(studentProgressionDTO);
    }

    private void insertCumulativeQCAForStudent(Double cumulativeQCA, Integer partNo, Integer academicYear, Integer academicSemester, Long studentId, ProgressDecision progressDecisionEnum) {
        log.debug("request to insert Cumulative QCA for student: {} with QCA: {} in academic Year: {} and academic Semester: ", studentId, academicYear, academicSemester);

        StudentProgressionDTO studentProgressionDTO = new StudentProgressionDTO();
        studentProgressionDTO.setForPartNo(partNo);
        studentProgressionDTO.setForAcademicYear(academicYear);
        studentProgressionDTO.setForAcademicSemester(academicSemester);
        studentProgressionDTO.setQca(cumulativeQCA);
        studentProgressionDTO.setStudentId(studentId);
        studentProgressionDTO.setProgressType(ProgressType.YEAR);
        if (progressDecisionEnum != null) studentProgressionDTO.setProgressDecision(progressDecisionEnum);
        log.debug("request to save student cumulative qca to student progression table. studentId: {}, academicYear: {}, academicSemester: {}, semesterQCA: {}",
            studentId, academicYear, academicSemester, cumulativeQCA);
        save(studentProgressionDTO);
    }

    private Double calculateCumulativeQCA(List<StudentModuleSelectionDTO> resultsList) {
        log.debug("request to calculate the cumulative QCA for student: {}", resultsList.get(0).getStudentId());
        Double qcs = 0.00;
        Double attemptedHour = 0.00;
        for (StudentModuleSelectionDTO studentModuleSelectionDTO : resultsList) {
            qcs += studentModuleSelectionDTO.getQcs();
            attemptedHour += studentModuleSelectionDTO.getCreditHour() - getNQH();
        }
        log.debug("The cumulative QCA is {}", qcs / attemptedHour);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(qcs / attemptedHour));
    }

    private Boolean isDuplicateSemesterQCA(List<StudentModuleSelectionDTO> resultsList, Integer academicYear, Integer academicSemester) {
        // check data existence in Student Progression table
        // todo variations : how to update qca if one of results is edited?
        log.debug("Request to check student {} results existence in academicYear : {} and academicSemester: {}",
            resultsList.get(resultsList.size() - 1).getStudentId(), academicYear, academicSemester);

        Optional<StudentDTO> student = studentService.findOne(resultsList.get(resultsList.size() - 1).getStudentId());
        List<StudentProgression> studentProgressionDTOS = studentProgressionRepository.findAllByStudent(studentMapper.toEntity(student.get()));

        for(StudentProgression studentProgression: studentProgressionDTOS) {
            if (studentProgression.getForAcademicYear() == academicSemester && studentProgression.getForAcademicYear() == academicYear) {
                // the result will not be inserted if the result has already existed in the db
                log.debug("student {} semester result exists in academicYear : {} and academicSemester: {}. can not insert new record and rollback.",
                    resultsList.get(resultsList.size() - 1).getStudentId(), academicYear, academicSemester);
                return true;
            }
        }
        return false;
    }

    private Boolean isDuplicateCumulativeQCA(List<StudentModuleSelectionDTO> resultsList, Integer academicYear, Integer partNo) {
        // check data existence in Student Progression table
        // todo variations : how to update qca if one of results is edited?
        log.debug("Request to check student {} results existence in academicYear : {} and partNo: {}",
            resultsList.get(resultsList.size() - 1).getStudentId(), academicYear, partNo);

        Optional<StudentDTO> student = studentService.findOne(resultsList.get(resultsList.size() - 1).getStudentId());
        List<StudentProgression> studentProgressionDTOS = studentProgressionRepository.findAllByStudent(studentMapper.toEntity(student.get()));

        for(StudentProgression studentProgression: studentProgressionDTOS) {
            if (studentProgression.getForPartNo() == partNo && studentProgression.getForAcademicYear() == academicYear) {
                // the result will not be inserted if the result has already existed in the db
                log.debug("student {} cumulative QCA results exist in academicYear : {} and partNo: {}. can not insert new record and rollback.",
                    resultsList.get(resultsList.size() - 1).getStudentId(),academicYear, partNo);
                return true;
            }
        }
        return false;
    }

    /**
     * Make the decision based on original_QCA and listGradeOfThisStudent
     * This function does not check the condition whether the student_progression is in Decision Point or not
     * This function only check if
     * QCA > 2.0 : PASS
     * QCA < 2.0: then swap 4 worst grades, then calculating QCA again based on listGradeOfThisStudent, then output decison
     *
     * @param originalCumulativeQca
     * @param listGradeOfThisStudent: to take 4 worst modules to swap
     * @return PASS, FAIL_CAN_REPEAT, FAIL_NO_REPEAT
     */
    private ProgressDecision makeProgressionDecision(double originalCumulativeQca, List<StudentModuleSelectionDTO> listGradeOfThisStudent) {
        log.debug("Begin making first decision of transiting state from NO_STATE to PASS/FAIL_CAN_REPEAT/FAIL_NO_REPEAT");
        if (originalCumulativeQca > 2.0)
            return ProgressDecision.PASS;
        else {
            //Sort to get 4 worst grades
            Collections.sort(listGradeOfThisStudent, (o1, o2) -> {
                if (o1.getQcs() > o2.getQcs())
                    return 1;
                else return -1;
            });

            //Check if he took 1 semester or 2 semesters because it will affect the number of swap grades
            boolean isLearnedSem1 = false;
            boolean isLearnedSem2 = false;

            for (StudentModuleSelectionDTO gradeRecord : listGradeOfThisStudent) {
                if (isLearnedSem1 == false) {
                    if (gradeRecord.getYearNo() == 1 && gradeRecord.getSemesterNo() == 1)
                        isLearnedSem1 = true;
                }
                if (isLearnedSem2 == false) {
                    if (gradeRecord.getYearNo() == 1 && gradeRecord.getSemesterNo() == 2)
                        isLearnedSem2 = true;
                }
            }
            if (isLearnedSem1 == true && isLearnedSem2 == true) {
                swapWorstModule(listGradeOfThisStudent, 4);
            } else {
                swapWorstModule(listGradeOfThisStudent, 2);
            }

            //Calculate QCA after swap
            double QCA_AfterSwap = calculateCumulativeQCA(listGradeOfThisStudent);
            log.debug("QCA_AfterSwap: {}", QCA_AfterSwap);

            if (QCA_AfterSwap >= 2.0) {
                return ProgressDecision.FAIL_CAN_REPEAT;
            } else {
                return ProgressDecision.FAIL_NO_REPEAT;
            }
        }
    }
}
