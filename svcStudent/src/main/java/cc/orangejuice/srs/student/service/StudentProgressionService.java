package cc.orangejuice.srs.student.service;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.StudentProgression;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.repository.StudentProgressionRepository;
import cc.orangejuice.srs.student.repository.StudentRepository;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;
import cc.orangejuice.srs.student.service.mapper.StudentProgressionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private StudentRepository studentRepository;

    public StudentProgressionService(StudentProgressionRepository studentProgressionRepository, StudentProgressionMapper studentProgressionMapper) {
        this.studentProgressionRepository = studentProgressionRepository;
        this.studentProgressionMapper = studentProgressionMapper;
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
        List<StudentProgressionDTO> studentProgressionDTO = studentProgressionMapper.toDto(studentProgressionRepositoryAll);

        //Check if QCA in the list is in the first part, and >2.0 or <2.0
        for (StudentProgressionDTO studentProgression : studentProgressionDTO) {
            //If QCA in the first part
            if (studentProgression.getForPartNo() == 1 && studentProgression.getForAcademicSemester() == 2) {
                //if QCA >2.0
                if (studentProgression.getQca() >= 2) {
                    studentProgression.setProgressDecision(ProgressDecision.PASS);
                    StudentProgression modifiedStudentProgression = studentProgressionMapper.toEntity(studentProgression);
                    studentProgressionRepository.save(modifiedStudentProgression);
                }

                //Else QCA <2.0 , Check if his QCA is FAIL_NO_REPEAT or FAIL_CAN_REPEAT after Swap
                else {
                    //get all grade of this student in year 1 and store in a list
                    List<StudentModuleSelectionDTO> gradeOfThisStduent = new ArrayList<>();
                    boolean isLearning2Semester = false;

                    for (StudentModuleSelectionDTO gradeRecord : gradeList) {
                        if (gradeRecord.getStudentId().equals(studentProgression.getStudentId())) {
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
                    System.out.println("QCA before: " +QCAbeforeSwap + studentProgression.getProgressDecision());

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
                        studentProgression.setProgressDecision(ProgressDecision.FAIL_CAN_REPEAT);
                        StudentProgression modifiedStudentProgression = studentProgressionMapper.toEntity(studentProgression);
                        studentProgressionRepository.save(modifiedStudentProgression);
                    }
                    else {
                        studentProgression.setProgressDecision(ProgressDecision.FAIL_NO_REPEAT);
                        StudentProgression modifiedStudentProgression = studentProgressionMapper.toEntity(studentProgression);
                        studentProgressionRepository.save(modifiedStudentProgression);

                    }

                    //Decision CAN_REPEAT or NO_REPEAT
                    System.out.println("QCA after: " +QCAafterSwap + studentProgression.getProgressDecision());

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

    public static double calculateQCAafterSwap(List<StudentModuleSelectionDTO> gradeOfThisStuduent) {
        double QCA = 0.0;
        Double totalAttemptHours = 0.0;
        //Accumulate QCA and attempthours
        for (StudentModuleSelectionDTO grade : gradeOfThisStuduent) {

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


}
