package view;

import model.Hand;
import model.basic.Decision;
import model.basic.Value;

public class Strategy {

    private final Pattern pattern;

    public Strategy(Pattern pattern) {
        this.pattern = pattern;
    }

    public Decision getDecision(Hand dealerHand, Hand playerHand) {
        Decision decision;
        var playerCards = playerHand.getCards();
        var dealerCards = dealerHand.getCards();
        if(playerCards.size()==2 && playerCards.get(0).getValue()==playerCards.get(1).getValue()) {
            System.out.println("pair" + playerHand.getPoints());
            decision = pattern.pair().get(
                    new Pair<>(playerCards.get(0).getValue(), dealerCards.get(0).getValue()));
        }
        else if(playerHand.getAceCount()==1 && playerHand.getPoints()<11) {
            System.out.println("ace" + playerHand.getPoints());
            decision = pattern.ace().get(
                    new Pair<>(playerHand.getPoints()-11, dealerCards.get(0).getValue()));
        }
        else {
            System.out.println("normal" + playerHand.getPoints());
            decision = pattern.normal().get(
                    new Pair<>(playerHand.getPoints(), dealerCards.get(0).getValue()));
        }
        if(decision==Decision.NONE) System.out.println("error: decision = NONE");
        return decision;
    }
}
