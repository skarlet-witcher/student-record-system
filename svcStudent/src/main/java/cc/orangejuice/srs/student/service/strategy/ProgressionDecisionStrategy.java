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

    public ProgressionDecisionStrategy(List<StudentModuleSelectionDTO> listGradeOfThisStudent, StudentProgressionService studentProgressionService, Logger log) {
        this.listGradeOfThisStudent = listGradeOfThisStudent;
        this.studentProgressionService = studentProgressionService;
        this.log = log;
    }

    public abstract ProgressDecision action();



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
