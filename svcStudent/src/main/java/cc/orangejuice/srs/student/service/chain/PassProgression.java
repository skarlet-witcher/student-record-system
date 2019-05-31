package cc.orangejuice.srs.student.service.chain;

import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

public class PassProgression extends AbstractProgression {

    @Override
    public ProgressDecision progressionChecker(double originalQCA) {
        if(originalQCA > 2.0) {
            return makeProgressionDecision();
        } else {
            return nextProgression.progressionChecker(originalQCA);
        }
    }

    @Override
    public ProgressDecision makeProgressionDecision() {
        return ProgressDecision.PASS;
    }
}
