package cc.orangejuice.srs.student.service.strategy;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import org.slf4j.Logger;

import java.util.List;

public class PassStrategy extends ProgressionDecisionStrategy{

    public PassStrategy(List<StudentModuleSelectionDTO> listGradeOfThisStudent, StudentProgressionService studentProgressionService, Logger log) {
        super(listGradeOfThisStudent, studentProgressionService, log);
    }

    @Override
    public ProgressDecision action() {
        return ProgressDecision.PASS;
    }
}
