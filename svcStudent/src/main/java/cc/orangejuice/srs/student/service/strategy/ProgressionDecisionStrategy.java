package cc.orangejuice.srs.student.service.strategy;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import org.slf4j.Logger;

import java.util.List;

public abstract class ProgressionDecisionStrategy {

    private List<StudentModuleSelectionDTO> listGradeOfThisStudent;
    private StudentProgressionService studentProgressionService;

    private Logger log;

    public abstract ProgressDecision action();

    protected void swapWorstModule(List<StudentModuleSelectionDTO> gradeOfThisStudent, int numberOfPossibleSwap) {
        for (int i = 0; i < numberOfPossibleSwap; i++) {
            gradeOfThisStudent.get(i).setQcs(12.0); // c3 with the qcs of 12.0
        }
    }

    public List<StudentModuleSelectionDTO> getListGradeOfThisStudent() {
        return listGradeOfThisStudent;
    }

    public void setListGradeOfThisStudent(List<StudentModuleSelectionDTO> listGradeOfThisStudent) {
        this.listGradeOfThisStudent = listGradeOfThisStudent;
    }

    public StudentProgressionService getStudentProgressionService() {
        return studentProgressionService;
    }

    public void setStudentProgressionService(StudentProgressionService studentProgressionService) {
        this.studentProgressionService = studentProgressionService;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }
}
