package strategy;

import model.Hand;
import model.basic.Decision;

public abstract class AbstractStrategy {

    protected Pattern pattern;

    public abstract Decision getDecision(CardCounter counter, Hand dealerHand, Hand playerHand);

}
