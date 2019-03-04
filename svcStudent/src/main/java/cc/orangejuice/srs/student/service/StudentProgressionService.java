package cc.orangejuice.srs.student.service;

import cc.orangejuice.srs.student.client.StudentModuleSelectionsFeignClient;
import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.StudentProgression;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.repository.StudentProgressionRepository;
import cc.orangejuice.srs.student.repository.StudentRepository;
import cc.orangejuice.srs.student.service.dto.StudentDTO;
import cc.orangejuice.srs.student.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;
import cc.orangejuice.srs.student.service.mapper.StudentProgressionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.swing.text.html.Option;
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

    private final StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    public StudentProgressionService(StudentProgressionRepository studentProgressionRepository,
                                     StudentProgressionMapper studentProgressionMapper,
                                     StudentModuleSelectionsFeignClient studentModuleSelectionsFeignClient,
                                     StudentService studentService) {
        this.studentProgressionRepository = studentProgressionRepository;
        this.studentProgressionMapper = studentProgressionMapper;
        this.studentModuleSelectionsFeignClient = studentModuleSelectionsFeignClient;
        this.studentService = studentService;
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


    public void firstDecision(Collection<StudentModuleSelectionDTO> gradeList) {
        log.debug("Begin making first decision of transiting state from NO_STATE to PASS/FAIL_CAN_REPEAT/FAIL_NO_REPEAT");

        //Get list of QCA
        List<StudentProgression> studentProgressionRepositoryAll = studentProgressionRepository.findAll();
        List<StudentProgressionDTO> studentProgressionDTOS = studentProgressionMapper.toDto(studentProgressionRepositoryAll);

        //Check if QCA in the list is in the first part, and >2.0 or <2.0
        for (StudentProgressionDTO studentProgressionDTO : studentProgressionDTOS) {
            //If QCA in the first part
            if (studentProgressionDTO.getForPartNo() == 1 && studentProgressionDTO.getForAcademicSemester() == 2) {
                //if QCA >2.0
                if (studentProgressionDTO.getQca() >= 2) {
                    studentProgressionDTO.setProgressDecision(ProgressDecision.PASS);
                    StudentProgression modifiedStudentProgression = studentProgressionMapper.toEntity(studentProgressionDTO);
                    studentProgressionRepository.save(modifiedStudentProgression);
                }

                //Else QCA <2.0 , Check if his QCA is FAIL_NO_REPEAT or FAIL_CAN_REPEAT after Swap
                else {
                    //get all grade of this student in year 1 and store in a list
                    List<StudentModuleSelectionDTO> gradeOfThisStduent = new ArrayList<>();
                    boolean isLearning2Semester = false;

                    for (StudentModuleSelectionDTO gradeRecord : gradeList) {
                        if (gradeRecord.getStudentId().equals(studentProgressionDTO.getStudentId())) {
                            if (gradeRecord.getYearNo() == 1)
                                gradeOfThisStduent.add(gradeRecord);
                            if (isLearning2Semester == false) {
                                if (gradeRecord.getSemesterNo() == 2) {
                                    isLearning2Semester = true;
                                }
                            }
                        }
                    }

                    //Sort to get the worst grades
                    Collections.sort(gradeOfThisStduent, (o1, o2) -> {
                        if (o1.getQcs() > o2.getQcs())
                            return 1;
                        else return -1;
                    });

                    double QCAbeforeSwap = calculateQCAafterSwap(gradeOfThisStduent);
                    System.out.println("QCA before: " +QCAbeforeSwap + studentProgressionDTO.getProgressDecision());

                    //If he only learns in 1 semester
                    if (isLearning2Semester == false) {
                        //find 2 worst grades and swap
                        swapWorstModule(gradeOfThisStduent, 2);
                    }
                    //If he learns 2 semesters
                    else {
                        //find 4 worst grades and swap
                        swapWorstModule(gradeOfThisStduent, 4);
                    }
                    //calculate QCA again
                    double QCAafterSwap = calculateQCAafterSwap(gradeOfThisStduent);

                    //if (swap > 2.0) or <2.0
                    if(QCAafterSwap >= 2.0){
                        studentProgressionDTO.setProgressDecision(ProgressDecision.FAIL_CAN_REPEAT);
                        StudentProgression modifiedStudentProgression = studentProgressionMapper.toEntity(studentProgressionDTO);
                        studentProgressionRepository.save(modifiedStudentProgression);
                    }
                    else {
                        studentProgressionDTO.setProgressDecision(ProgressDecision.FAIL_NO_REPEAT);
                        StudentProgression modifiedStudentProgression = studentProgressionMapper.toEntity(studentProgressionDTO);
                        studentProgressionRepository.save(modifiedStudentProgression);

                    }

                    //Decision CAN_REPEAT or NO_REPEAT
                    System.out.println("QCA after: " +QCAafterSwap + studentProgressionDTO.getProgressDecision());

                }

            }
        }

    }


    public static void swapWorstModule(List<StudentModuleSelectionDTO> gradeOfThisStduent, int numberOfPossibleSwap) {
        for (int i = 0; i < numberOfPossibleSwap; i++) {
            gradeOfThisStduent.get(i).setQcs(12.0);
        }
    }


    private static Integer getNQH() {
        return 0;
    }

    public static double calculateQCAafterSwap(List<StudentModuleSelectionDTO> gradeOfThisStudent) {
        double QCA = 0.0;
        Double totalAttemptHours = 0.0;
        //Accumulate QCA and attempthours
        for (StudentModuleSelectionDTO grade : gradeOfThisStudent) {
            QCA = QCA + grade.getQcs();
                totalAttemptHours += grade.getCreditHour() - getNQH();

//            int eachAttemptHour = 0;
//            int credit = moduleRepository.findById(grade.getModuleId()).getCredit;
//            int factor = programmeproRepositoty.findByEnrollYearandSemesterNoAndKey;
//            eachAttemptHour = credit* factor;
//            totalAttemptHours = totalAttemptHours + eachAttemptHour;

        }

        QCA = QCA / totalAttemptHours;
        return QCA;
    }

    //(Function not finish) Long will change it later.
    //Xiangkai is responsible for producing the Student_progression record.
    // And he will call this function to fill the data into the "Progress Decision" column
    // he will need to check the condition (studentProgressionDTO.getForPartNo() == 1 && studentProgressionDTO.getForAcademicSemester() == 2)

    /**
     * Make the decision based on original_QCA and listGradeOfThisStudent
     * This function does not check the condition whether the student_progression is in Decision Point or not
     * This function only check if
     *    QCA > 2.0 : PASS
     *    QCA < 2.0: then swap 4 worst grades, then calculating QCA again based on listGradeOfThisStudent, then output decison
     * @param originalCumulativeQca
     * @param listGradeOfThisStudent: to take 4 worst modules to swap
     * @return PASS, FAIL_CAN_REPEAT, FAIL_NO_REPEAT
     */
    private Enum<ProgressDecision> makeProgressionDecision(double originalCumulativeQca, List<StudentModuleSelectionDTO> listGradeOfThisStudent) {
        if(originalCumulativeQca > 2.0)
            return ProgressDecision.PASS;
        else return ProgressDecision.FAIL_CAN_REPEAT;
    }

    public void calculateQCA(StudentModuleSelectionDTO[] resultsList, Integer academicYear, Integer academicSemester) {
        log.debug("Request to calculate QCA for student: {}", resultsList[resultsList.length - 1].getStudentId());

        // todo check if there is a tuple in the Student progression table for this student
        log.debug("Request to check student {} results existence in academicYear : {} and academicSemester: {}",
            resultsList[resultsList.length - 1].getStudentId(),resultsList[resultsList.length - 1].getAcademicYear(), resultsList[resultsList.length - 1].getAcademicSemester());
        Optional<StudentDTO> student = studentService.findOne(resultsList[resultsList.length - 1].getStudentId());
        if(studentProgressionRepository.findOneByStudent(student.get()._toConvertStudent()) != null) {
            log.debug("student {} results exist in academicYear : {} and academicSemester: {}",
                resultsList[resultsList.length - 1].getStudentId(), resultsList[resultsList.length - 1].getAcademicYear(), resultsList[resultsList.length - 1].getAcademicSemester());
            return;
        }

        // todo calculate semester QCA
        Double semesterQCA = calculateSemesterQCA(resultsList, academicYear, academicSemester);
        insertSemesterQCAForStudent(semesterQCA, academicYear, academicSemester, resultsList[resultsList.length - 1].getStudentId());

        // todo if it is the end of the part


        // todo if yes, calculate cumulative QCA and generate progression decision
        // todo how to distinguish part 1 and part 2 ?
        // todo no need to think about graduation for now


    }

    private Double calculateSemesterQCA(StudentModuleSelectionDTO[] resultsList, Integer academicYear, Integer academicSemester) {
        log.debug("Request to calculate semester QCA for student: {} in academic semester: {}", resultsList[resultsList.length - 1].getStudentId(), academicSemester);
        Double semesterQCS = 0.00;
        Double attemptedHours = 0.00;
        for(StudentModuleSelectionDTO oneResult : resultsList) {
           if(oneResult.getAcademicYear() == academicYear && oneResult.getAcademicSemester() == academicSemester) {
               semesterQCS += oneResult.getQcs();
               attemptedHours += oneResult.getCreditHour() - getNQH();
           }
        }
        return semesterQCS/attemptedHours;
    }

    private void insertSemesterQCAForStudent(Double semesterQCA, Integer academicYear, Integer academicSemester, Long studentId) {
        log.debug("request to get student: {}", studentId);

        StudentProgressionDTO studentProgressionDTO = new StudentProgressionDTO();
        studentProgressionDTO.setForAcademicYear(academicYear);
        studentProgressionDTO.setForAcademicSemester(academicSemester);
        studentProgressionDTO.setQca(semesterQCA);
        studentProgressionDTO.setStudentId(studentId);
        log.debug("request to save student semester qca to student progression table. studentId: {}, academicYear: {}, academicSemester: {}, semesterQCA: {}",
            studentId, academicYear, academicSemester, semesterQCA);
        save(studentProgressionDTO);
    }
}
