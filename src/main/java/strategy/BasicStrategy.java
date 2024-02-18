package strategy;

import model.Hand;
import model.basic.Decision;
import model.basic.Value;

import java.util.HashMap;
import java.util.List;

public class BasicStrategy extends AbstractStrategy {
    // Ignores CardCounter

    @Override
    public Decision getDecision(CardCounter counter, Hand dealerHand, Hand playerHand) {
        Decision decision;
        var playerCards = playerHand.getCards();
        var dealerCards = dealerHand.getCards();
        if(playerCards.size()==2 && playerCards.get(0).getValue()==playerCards.get(1).getValue()) {
            //System.out.println("pair" + playerHand.getPoints());
            decision = pattern.pair().get(
                    new Pair<>(playerCards.get(0).getValue(), dealerCards.get(0).getValue()))
                    .a();
        }
        else if(playerHand.getAceCount()==1 && playerHand.getPoints()<22) {
            //System.out.println("ace" + playerHand.getPoints());
            decision = pattern.ace().get(
                    new Pair<>(playerHand.getPoints()-11, dealerCards.get(0).getValue()))
                    .a();
            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                if(playerHand.getPoints()-11<7) decision = Decision.HIT;
                else decision = Decision.STAND;
            }
        }
        else {
            //System.out.println("normal" + playerHand.getPoints());
            decision = pattern.normal().get(
                    new Pair<>(playerHand.getPoints(), dealerCards.get(0).getValue()))
                    .a();
            // not possible to double
            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                decision = Decision.HIT;
            }
        }
        //if(decision==Decision.NONE) System.out.println("error: decision = NONE");
        return decision;
    }

    public void InitializeStrategy() {
        var normal = new HashMap<Pair<Integer, Value>, Pair<Decision,Integer>>();

        for(int i=5;i<17;i++) {
            for(Value j : Value.values()) {
                normal.put(new Pair<>(i, j), new Pair<>(Decision.HIT, 0));
            }
        }
        normal.put(new Pair<>(9, Value.THREE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(9, Value.FOUR), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(9, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(9, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.TWO), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.THREE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.FOUR), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.SEVEN), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.EIGHT), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(10, Value.NINE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.TWO), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.THREE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.FOUR), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.SEVEN), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.EIGHT), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.NINE), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.TEN), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.JACK), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.QUEEN), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(11, Value.KING), new Pair<>(Decision.DOUBLE, 0));
        normal.put(new Pair<>(12, Value.FOUR), new Pair<>(Decision.STAND, 0));
        normal.put(new Pair<>(12, Value.FIVE), new Pair<>(Decision.STAND, 0));
        normal.put(new Pair<>(12, Value.SIX), new Pair<>(Decision.STAND, 0));
        for(int i=13;i<17;i++) {
            normal.put(new Pair<>(i, Value.TWO), new Pair<>(Decision.STAND, 0));
            normal.put(new Pair<>(i, Value.THREE), new Pair<>(Decision.STAND, 0));
            normal.put(new Pair<>(i, Value.FOUR), new Pair<>(Decision.STAND, 0));
            normal.put(new Pair<>(i, Value.FIVE), new Pair<>(Decision.STAND, 0));
            normal.put(new Pair<>(i, Value.SIX), new Pair<>(Decision.STAND, 0));
        }
        for(int i=17;i<22;i++) {
            for(Value j : Value.values()) {
                normal.put(new Pair<>(i, j), new Pair<>(Decision.STAND, 0));
            }
        }


        var ace = new HashMap<Pair<Integer, Value>, Pair<Decision,Integer>>();

        for(int i=2;i<8;i++) {
            for(Value j : Value.values()) {
                ace.put(new Pair<>(i, j), new Pair<>(Decision.HIT, 0));
            }
        }
        for(int i=2;i<4;i++) {
            ace.put(new Pair<>(i, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
            ace.put(new Pair<>(i, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        }
        for(int i=4;i<6;i++) {
            ace.put(new Pair<>(i, Value.FOUR), new Pair<>(Decision.DOUBLE, 0));
            ace.put(new Pair<>(i, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
            ace.put(new Pair<>(i, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        }
        for(int i=6;i<8;i++) {
            ace.put(new Pair<>(i, Value.THREE), new Pair<>(Decision.DOUBLE, 0));
            ace.put(new Pair<>(i, Value.FOUR), new Pair<>(Decision.DOUBLE, 0));
            ace.put(new Pair<>(i, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
            ace.put(new Pair<>(i, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        }
        //fixed mistake here
        ace.put(new Pair<>(7, Value.TWO), new Pair<>(Decision.STAND, 0));
        ace.put(new Pair<>(7, Value.SEVEN), new Pair<>(Decision.STAND, 0));
        ace.put(new Pair<>(7, Value.EIGHT), new Pair<>(Decision.STAND, 0));
        for(int i=8;i<11;i++) {
            for(Value j : Value.values()) {
                ace.put(new Pair<>(i, j), new Pair<>(Decision.STAND, 0));
            }
        }


        var pair = new HashMap<Pair<Value, Value>, Pair<Decision,Integer>>();

        for(Value i : Value.values()) {
            for(Value j : Value.values()) {
                pair.put(new Pair<>(i, j), new Pair<>(Decision.HIT, 0));
            }
        }
        pair.put(new Pair<>(Value.ACE, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.SEVEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.EIGHT), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.NINE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.TEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.JACK), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.QUEEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.ACE, Value.KING), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.TWO, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.TWO, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.TWO, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.TWO, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.TWO, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.TWO, Value.SEVEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.THREE, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.THREE, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.THREE, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.THREE, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.THREE, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.THREE, Value.SEVEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.FOUR, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.FOUR, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.FIVE, Value.TWO), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.THREE), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.FOUR), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.FIVE), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.SIX), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.SEVEN), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.EIGHT), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.FIVE, Value.NINE), new Pair<>(Decision.DOUBLE, 0));
        pair.put(new Pair<>(Value.SIX, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SIX, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SIX, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SIX, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SIX, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SEVEN, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SEVEN, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SEVEN, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SEVEN, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.SEVEN, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        //fixed mistake here
        pair.put(new Pair<>(Value.SEVEN, Value.SEVEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.SEVEN), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.EIGHT), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.EIGHT, Value.NINE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.TWO), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.THREE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.FOUR), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.FIVE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.SIX), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.SEVEN), new Pair<>(Decision.STAND, 0));
        pair.put(new Pair<>(Value.NINE, Value.EIGHT), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.NINE), new Pair<>(Decision.SPLIT, 0));
        pair.put(new Pair<>(Value.NINE, Value.TEN), new Pair<>(Decision.STAND, 0));
        pair.put(new Pair<>(Value.NINE, Value.JACK), new Pair<>(Decision.STAND, 0));
        pair.put(new Pair<>(Value.NINE, Value.QUEEN), new Pair<>(Decision.STAND, 0));
        pair.put(new Pair<>(Value.NINE, Value.KING), new Pair<>(Decision.STAND, 0));
        pair.put(new Pair<>(Value.NINE, Value.ACE), new Pair<>(Decision.STAND, 0));
        for(Value i : List.of(Value.TEN, Value.JACK, Value.QUEEN, Value.KING)) {
            pair.put(new Pair<>(i, Value.TWO), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.THREE), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.FOUR), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.FIVE), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.SIX), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.SEVEN), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.EIGHT), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.NINE), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.TEN), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.JACK), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.QUEEN), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.KING), new Pair<>(Decision.STAND, 0));
            pair.put(new Pair<>(i, Value.ACE), new Pair<>(Decision.STAND, 0));
        }

        this.pattern = new Pattern(normal, ace, pair);
    }
}
