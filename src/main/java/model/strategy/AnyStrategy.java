package model.strategy;

import model.basic.Hand;
import model.elementary.Decision;
import model.elementary.Pair;
import model.elementary.Value;
import model.pattern.Pattern;

public class AnyStrategy extends AbstractStrategy{
    public AnyStrategy(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Decision getDecision(int deckSize, CardCounter counter, Hand dealerHand, Hand playerHand) {
        //System.out.println(counter.getCurrentCount() + " " + counter.getRealCount());
        Decision decision;
        var playerCards = playerHand.getCards();
        var dealerCards = dealerHand.getCards();
        var realCount = counter.getRealCount();

        // Decide whether to buy insurance:
        // dealer has an ace, it's worth it, it's player's first decision
        if(dealerCards.get(0).getValue() == Value.ACE &&
                realCount>=pattern.insurance().get(deckSize) &&
                playerHand.getPlayer().getHands().size()==1 && playerCards.size()==2) {
            //System.out.println("insurance has been bought");
            playerHand.setHandInsured(true);
            var player = playerHand.getPlayer();
            player.setBalance(player.getBalance() - playerHand.getBet()*0.5);
        }

        // Decide on player's decision
        if(playerCards.size()==2 && playerCards.get(0).getValue()==playerCards.get(1).getValue()) {
            //System.out.println("pair" + playerHand.getPoints());
            decision = pattern.pair().get(
                            new Pair<>(playerCards.get(0).getValue(),
                                    dealerCards.get(0).getValue()))
                    .getInstruction(realCount);
        }
        else if(playerHand.getAceCount()==1 && playerHand.getPoints()<22) {
            //System.out.println("ace" + playerHand.getPoints());
            decision = pattern.ace().get(
                            new Pair<>(playerHand.getPoints()-11, dealerCards.get(0).getValue()))
                    .getInstruction(realCount);
            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                if(playerHand.getPoints()-11<7) decision = Decision.HIT;
                else decision = Decision.STAND;
            }
        }
        else {
            //System.out.println("normal" + playerHand.getPoints());
            decision = pattern.normal().get(
                            new Pair<>(playerHand.getPoints(), dealerCards.get(0).getValue()))
                    .getInstruction(realCount);
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
