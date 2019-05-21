package cc.orangejuice.srs.student.service.strategy;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

import java.util.Collections;

public class RepeatStrategy extends ProgressionDecisionStrategy {

    @Override
    public ProgressDecision action() {
        //Sort to get 4 worst grades
        sortGrades();

        //Check if he took 1 semester or 2 semesters because it will affect the number of swap grades
        swapGrades();


        if (calculateResidualQCA() >= 2.0) {
            return ProgressDecision.FAIL_CAN_REPEAT;
        } else {
            return ProgressDecision.FAIL_NO_REPEAT;
        }
    }

    private void sortGrades() {
        Collections.sort(this.getListGradeOfThisStudent(), (o1, o2) -> {
            if (o1.getQcs() > o2.getQcs())
                return 1;
            else return -1;
        });
    }

    private void swapGrades() {
        //Check if he took 1 semester or 2 semesters because it will affect the number of swap grades
        boolean isLearnedSem1 = false;
        boolean isLearnedSem2 = false;


        // semester check (1 or 2 or both)
        for (StudentModuleSelectionDTO gradeRecord : this.getListGradeOfThisStudent()) {
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
            swapWorstModule(this.getListGradeOfThisStudent(), 4);
        } else {
            swapWorstModule(this.getListGradeOfThisStudent(), 2);
        }
    }

    private double calculateResidualQCA() {
        //Calculate QCA after swap
        double QCA_AfterSwap = this.getStudentProgressionService().calculateCumulativeQCA(this.getListGradeOfThisStudent());
        this.getLog().debug("QCA_AfterSwap: {}", QCA_AfterSwap);
        return QCA_AfterSwap;
    }

}
