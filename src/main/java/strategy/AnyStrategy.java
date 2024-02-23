package strategy;

import model.Hand;
import model.basic.Decision;
import model.basic.Value;

import java.util.Objects;
import java.util.concurrent.DelayQueue;

public class AnyStrategy extends AbstractStrategy{
    public AnyStrategy(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Decision getDecision(CardCounter counter, Hand dealerHand, Hand playerHand) {
        //System.out.println(counter.getCurrentCount() + " " + counter.getRealCount());
        Decision decision;
        var playerCards = playerHand.getCards();
        var dealerCards = dealerHand.getCards();
        if(playerCards.size()==2 && playerCards.get(0).getValue()==playerCards.get(1).getValue()) {
            //System.out.println("pair" + playerHand.getPoints());
            decision = pattern.pair().get(
                            new Pair<>(playerCards.get(0).getValue(),
                                    dealerCards.get(0).getValue()))
                    .getInstruction(counter.getRealCount());
        }
        else if(playerHand.getAceCount()==1 && playerHand.getPoints()<22) {
            //System.out.println("ace" + playerHand.getPoints());
            decision = pattern.ace().get(
                            new Pair<>(playerHand.getPoints()-11, dealerCards.get(0).getValue()))
                    .getInstruction(counter.getRealCount());
            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                if(playerHand.getPoints()-11<7) decision = Decision.HIT;
                else decision = Decision.STAND;
            }
        }
        else {
            //System.out.println("normal" + playerHand.getPoints());
            decision = pattern.normal().get(
                            new Pair<>(playerHand.getPoints(), dealerCards.get(0).getValue()))
                    .getInstruction(counter.getRealCount());
            // not possible to double
            if(playerHand.getCards().size()>2) {
                if(decision==Decision.DOUBLE) decision = Decision.HIT;
                else if(decision==Decision.SURRENDER) {
                    if(playerHand.getPoints()==17) decision = Decision.STAND;
                    else decision = Decision.HIT;
                }
            }
        }

        return decision;
    }
}
