package model.strategy;

import model.basic.Card;
import model.basic.Hand;
import model.elementary.Decision;
import model.elementary.Pair;
import model.elementary.Value;
import model.pattern.Pattern;

import java.util.Objects;

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

        if(!Objects.isNull(pattern.insurance())) {
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
        }


        // Decide on player's decision
        if(playerCards.size()==2 && playerCards.get(0).getValue()==playerCards.get(1).getValue()) {
            //System.out.println("pair");
            decision = pattern.pair().get(
                            new Pair<>(playerCards.get(0).getValue(),
                                    dealerCards.get(0).getValue()))
                    .getInstruction(realCount);
            if(decision==Decision.NONE) {
                throw new RuntimeException("Decision is NONE");
            }
        }
        else if(softHand(playerHand)) {
            //System.out.println("ace");
            decision = pattern.ace().get(
                            new Pair<>(playerHand.getPoints()-11, dealerCards.get(0).getValue()))
                    .getInstruction(realCount);
            if(decision==Decision.NONE) {
                throw new RuntimeException("Decision is NONE");
            }
            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                if(playerHand.getPoints()-11<7) decision = Decision.HIT;
                else decision = Decision.STAND;
            }
        }
        else {
            //System.out.println("normal");
            decision = pattern.normal().get(
                            new Pair<>(playerHand.getPoints(), dealerCards.get(0).getValue()))
                    .getInstruction(realCount);
            if(decision==Decision.NONE) {
                throw new RuntimeException("Decision is NONE");
            }
        }

        // not possible to double/surrender after two initial cards
        if(playerHand.getCards().size()>2) {
            if(decision==Decision.DOUBLE) decision = Decision.HIT;
            else if(decision==Decision.SURRENDER) {
                if(playerHand.getPoints()>=17) decision = Decision.STAND;
                else decision = Decision.HIT;
            }
        }

        // not possible to surrender on dealer's ace
        if(decision==Decision.SURRENDER &&
                dealerHand.getCards().get(0).getValue() == Value.ACE) {
            if(playerHand.getPoints()>=17) decision = Decision.STAND;
            else decision = Decision.HIT;
        }

        return decision;
    }

    private boolean softHand(Hand hand) {
        if(hand.getAceCount()==0) return false;
        int pointsWithoutAces = 0;
        for(Card card : hand.getCards()) {
            if(card.getValue() != Value.ACE) pointsWithoutAces+=card.getValue().getPoints();
        }
        return hand.getPoints() - pointsWithoutAces >= 11;
    }
}
