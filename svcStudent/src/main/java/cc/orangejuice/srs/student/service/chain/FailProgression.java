package cc.orangejuice.srs.student.service.chain;

import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

public class FailProgression extends AbstractProgression {
    @Override
    public ProgressDecision progressionChecker(double residualQCA) {
        return makeProgressionDecision();
    }

    @Override
    public ProgressDecision makeProgressionDecision() {
        return ProgressDecision.FAIL_NO_REPEAT;
    }
}
