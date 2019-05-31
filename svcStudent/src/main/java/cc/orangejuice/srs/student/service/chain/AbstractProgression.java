package cc.orangejuice.srs.student.service.chain;


import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

public abstract class AbstractProgression {

    protected AbstractProgression nextProgression;

    public void setNextProgression(AbstractProgression nextProgression) {
        this.nextProgression = nextProgression;
    }

    public abstract ProgressDecision progressionChecker(double originalQCA);

    public abstract ProgressDecision makeProgressionDecision();
}
