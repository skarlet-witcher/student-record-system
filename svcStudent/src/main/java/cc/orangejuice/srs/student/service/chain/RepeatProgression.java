package cc.orangejuice.srs.student.service.chain;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RepeatProgression extends AbstractProgression {

    private List<StudentModuleSelectionDTO> listGradeOfThisStudent;
    private StudentProgressionService studentProgressionService;

    private Logger log;

    public RepeatProgression(List<StudentModuleSelectionDTO> listGradeOfThisStudent, StudentProgressionService studentProgressionService, Logger log) {
        this.listGradeOfThisStudent = listGradeOfThisStudent;
        this.studentProgressionService = studentProgressionService;
        this.log = log;
    }

    @Override
    public ProgressDecision progressionChecker(double originalQCA) {
        Double residualQCA;
        //Sort to get 4 worst grades
        sortWorstGrades();
        //Check if he took 1 semester or 2 semesters because it will affect the number of swap grades
        swapGrades();

        residualQCA = calculateResidualQCA();

        if (residualQCA >= 2.0) {
            return makeProgressionDecision();
        } else {
            return this.nextProgression.progressionChecker(residualQCA);
        }
    }

    @Override
    public ProgressDecision makeProgressionDecision() {
        return ProgressDecision.FAIL_CAN_REPEAT;
    }

    private void sortWorstGrades() {

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

    private void swapWorstModule(List<StudentModuleSelectionDTO> gradeOfThisStudent, int numberOfPossibleSwap) {
        for (int i = 0; i < numberOfPossibleSwap; i++) {
            if (Arrays.binarySearch(studentProgressionService.getModuleGradeList().toArray(),
                gradeOfThisStudent.get(i).getStudentModuleGradeTypeName()) >= 0) {

                gradeOfThisStudent.get(i).setQcs(12.0); // c3 with the qcs of 12.0
            }

        }
    }

    private double calculateResidualQCA() {
        //Calculate QCA after swap
        double QCA_AfterSwap = this.getStudentProgressionService().calculateCumulativeQCA(this.getListGradeOfThisStudent());
        this.getLog().debug("QCA_AfterSwap: {}", QCA_AfterSwap);
        return QCA_AfterSwap;
    }

    public List<StudentModuleSelectionDTO> getListGradeOfThisStudent() {
        return listGradeOfThisStudent;
    }

    public StudentProgressionService getStudentProgressionService() {
        return studentProgressionService;
    }

    public Logger getLog() {
        return log;
    }
}
