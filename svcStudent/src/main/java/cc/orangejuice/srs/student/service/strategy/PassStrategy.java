package cc.orangejuice.srs.student.service.strategy;

import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

public class PassStrategy {
    public ProgressDecision action() {
        return ProgressDecision.PASS;
    }
}
