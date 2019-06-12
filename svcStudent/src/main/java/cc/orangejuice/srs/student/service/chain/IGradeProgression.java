package cc.orangejuice.srs.student.service.chain;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.service.StudentProgressionService;

import java.util.Arrays;
import java.util.List;

public class IGradeProgression extends AbstractProgression {

    private List<StudentModuleSelectionDTO> listGradeOfThisStudent;
    private StudentProgressionService studentProgressionService;

    public IGradeProgression(List<StudentModuleSelectionDTO> listGradeOfThisStudent, StudentProgressionService studentProgressionService) {
        this.listGradeOfThisStudent = listGradeOfThisStudent;
        this.studentProgressionService = studentProgressionService;
    }


    @Override
    public ProgressDecision progressionChecker(double originalQCA) {
        if(originalQCA > 2.0 && checkIGrade()) {
            return makeProgressionDecision();
        } else {
            return nextProgression.progressionChecker(originalQCA);
        }
    }

    @Override
    public ProgressDecision makeProgressionDecision() {
        return ProgressDecision.REPEAT_I_GRADE;
    }

    private Boolean checkIGrade() {
        Boolean result = false;
        for(StudentModuleSelectionDTO studentModuleSelectionDTO : listGradeOfThisStudent) {
            if(Arrays.binarySearch(this.studentProgressionService.getModuleGradeList().toArray(), studentModuleSelectionDTO.getStudentModuleGradeTypeName()) < 0) {
                System.out.println("IGrade found");
                result = true;
                break;
            }
        }
        return result;
    }
}
