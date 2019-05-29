package cc.orangejuice.srs.student.service.strategy;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class RepeatStrategy extends ProgressionDecisionStrategy {

    public RepeatStrategy(List<StudentModuleSelectionDTO> listGradeOfThisStudent, StudentProgressionService studentProgressionService, Logger log) {
        super(listGradeOfThisStudent, studentProgressionService, log);
    }

    @Override
    public ProgressDecision action() {
        //Sort to get 4 worst grades
        sortWorstGrades();
        //Check if he took 1 semester or 2 semesters because it will affect the number of swap grades
        swapGrades();

        if (calculateResidualQCA() >= 2.0) {
            return ProgressDecision.FAIL_CAN_REPEAT;
        } else {
            return ProgressDecision.FAIL_NO_REPEAT;
        }
    }

    private void sortWorstGrades() {

        Collections.sort(this.getListGradeOfThisStudent(), (o1, o2) -> {
            if (o1.getQcs() > o2.getQcs())
                return 1;
            else return -1;
        });
        /*
        for (int i = 0; i < this.getListGradeOfThisStudent().size(); i++)
        {
            for (int j = 0; j < this.getListGradeOfThisStudent().size() - 1 - i; j++)
            {
                if (this.getListGradeOfThisStudent().get(j).getQcs() > this.getListGradeOfThisStudent().get(j + 1).getQcs())
                {
                    StudentModuleSelectionDTO studentModuleSelectionDTO;
                    studentModuleSelectionDTO = this.getListGradeOfThisStudent().get(j);
                    this.getListGradeOfThisStudent().set(j, this.getListGradeOfThisStudent().get(j + 1));
                    this.getListGradeOfThisStudent().set(j + 1, studentModuleSelectionDTO);
                }
            }
        }
        */
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

    private void swapWorstModule(List<StudentModuleSelectionDTO> gradeOfThisStudent, int numberOfPossibleSwap) {
        for (int i = 0; i < numberOfPossibleSwap; i++) {
            gradeOfThisStudent.get(i).setQcs(12.0); // c3 with the qcs of 12.0
        }
    }

}
