package cc.orangejuice.srs.student.service.strategy;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class RepeatStrategy {

    private List<StudentModuleSelectionDTO> listGradeOfThisStudent;
    private StudentProgressionService studentProgressionService;

    private Logger log;

    public ProgressDecision action() {
        //Sort to get 4 worst grades
        Collections.sort(listGradeOfThisStudent, (o1, o2) -> {
            if (o1.getQcs() > o2.getQcs())
                return 1;
            else return -1;
        });

        //Check if he took 1 semester or 2 semesters because it will affect the number of swap grades
        boolean isLearnedSem1 = false;
        boolean isLearnedSem2 = false;


        // semester check (1 or 2 or both)
        for (StudentModuleSelectionDTO gradeRecord : listGradeOfThisStudent) {
            if (isLearnedSem1 == false) {
                if (gradeRecord.getYearNo() == 1 && gradeRecord.getSemesterNo() == 1)
                    isLearnedSem1 = true;
            }
            if (isLearnedSem2 == false) {
                if (gradeRecord.getYearNo() == 1 && gradeRecord.getSemesterNo() == 2)
                    isLearnedSem2 = true;
            }
            if(isLearnedSem1 == true && isLearnedSem2 == true) break;
        }

        // swap grades 2 or 4
        if (isLearnedSem1 == true && isLearnedSem2 == true) {
            swapWorstModule(listGradeOfThisStudent, 4);
        } else {
            swapWorstModule(listGradeOfThisStudent, 2);
        }

        //Calculate QCA after swap
        double QCA_AfterSwap = studentProgressionService.calculateCumulativeQCA(listGradeOfThisStudent);
        log.debug("QCA_AfterSwap: {}", QCA_AfterSwap);

        if (QCA_AfterSwap >= 2.0) {
            return ProgressDecision.FAIL_CAN_REPEAT;
        } else {
            return ProgressDecision.FAIL_NO_REPEAT;
        }
    }

    private void swapWorstModule(List<StudentModuleSelectionDTO> gradeOfThisStudent, int numberOfPossibleSwap) {
        for (int i = 0; i < numberOfPossibleSwap; i++) {
            gradeOfThisStudent.get(i).setQcs(12.0); // c3 with the qcs of 12.0
        }
    }
}
