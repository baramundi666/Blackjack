package model.strategy;

import model.basic.Hand;
import model.elementary.Decision;
import model.pattern.Pattern;

public abstract class AbstractStrategy {

    protected Pattern pattern;

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public abstract Decision getDecision(int deckSize, CardCounter counter, Hand dealerHand, Hand playerHand);

}
