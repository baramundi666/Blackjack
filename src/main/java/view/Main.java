package view;

import model.basic.Decision;
import model.basic.Value;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        var deck = new Deck(6);
//        var round = new Round(3, deck);
//        try {
//            round.play();
//        } catch (IOException | IllegalStateException e) {
//            throw new RuntimeException(e);
//        }

        var basicStrategy = initializeBasicStrategy();
        var simulation = new Simulation(basicStrategy, 6, 100000);
        simulation.start();
    }

    private static Strategy initializeBasicStrategy() {
        var normal = new HashMap<Pair<Integer, Value>, Decision>();

        for(int i=5;i<17;i++) {
            for(Value j : Value.values()) {
                normal.put(new Pair<>(i, j), Decision.HIT);
            }
        }
        normal.put(new Pair<>(9, Value.THREE), Decision.DOUBLE);
        normal.put(new Pair<>(9, Value.FOUR), Decision.DOUBLE);
        normal.put(new Pair<>(9, Value.FIVE), Decision.DOUBLE);
        normal.put(new Pair<>(9, Value.SIX), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.TWO), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.THREE), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.FOUR), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.FIVE), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.SIX), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.SEVEN), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.EIGHT), Decision.DOUBLE);
        normal.put(new Pair<>(10, Value.NINE), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.TWO), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.THREE), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.FOUR), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.FIVE), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.SIX), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.SEVEN), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.EIGHT), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.NINE), Decision.DOUBLE);
        normal.put(new Pair<>(11, Value.TEN), Decision.DOUBLE);
        normal.put(new Pair<>(12, Value.FOUR), Decision.STAND);
        normal.put(new Pair<>(12, Value.FIVE), Decision.STAND);
        normal.put(new Pair<>(12, Value.SIX), Decision.STAND);
        for(int i=13;i<17;i++) {
            normal.put(new Pair<>(i, Value.TWO), Decision.STAND);
            normal.put(new Pair<>(i, Value.THREE), Decision.STAND);
            normal.put(new Pair<>(i, Value.FOUR), Decision.STAND);
            normal.put(new Pair<>(i, Value.FIVE), Decision.STAND);
            normal.put(new Pair<>(i, Value.SIX), Decision.STAND);
        }
        for(int i=17;i<22;i++) {
            for(Value j : Value.values()) {
                normal.put(new Pair<>(i, j), Decision.STAND);
            }
        }


        var ace = new HashMap<Pair<Integer, Value>, Decision>();

        for(int i=2;i<8;i++) {
            for(Value j : Value.values()) {
                ace.put(new Pair<>(i, j), Decision.HIT);
            }
        }
        for(int i=2;i<4;i++) {
            ace.put(new Pair<>(i, Value.FIVE), Decision.DOUBLE);
            ace.put(new Pair<>(i, Value.SIX), Decision.DOUBLE);
        }
        for(int i=4;i<6;i++) {
            ace.put(new Pair<>(i, Value.FOUR), Decision.DOUBLE);
            ace.put(new Pair<>(i, Value.FIVE), Decision.DOUBLE);
            ace.put(new Pair<>(i, Value.SIX), Decision.DOUBLE);
        }
        ace.put(new Pair<>(6, Value.TWO), Decision.STAND);
        ace.put(new Pair<>(6, Value.THREE), Decision.DOUBLE);
        ace.put(new Pair<>(6, Value.FOUR), Decision.DOUBLE);
        ace.put(new Pair<>(6, Value.FIVE), Decision.DOUBLE);
        ace.put(new Pair<>(7, Value.SIX), Decision.DOUBLE);
        ace.put(new Pair<>(7, Value.SEVEN), Decision.STAND);
        ace.put(new Pair<>(7, Value.EIGHT), Decision.STAND);
        for(int i=8;i<11;i++) {
            for(Value j : Value.values()) {
                ace.put(new Pair<>(i, j), Decision.STAND);
            }
        }


        var pair = new HashMap<Pair<Value, Value>, Decision>();

        for(Value i : Value.values()) {
            for(Value j : Value.values()) {
                pair.put(new Pair<>(i, j), Decision.HIT);
            }
        }
        pair.put(new Pair<>(Value.ACE, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.SEVEN), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.EIGHT), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.NINE), Decision.SPLIT);
        pair.put(new Pair<>(Value.ACE, Value.TEN), Decision.SPLIT);
        pair.put(new Pair<>(Value.TWO, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.TWO, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.TWO, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.TWO, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.TWO, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.TWO, Value.SEVEN), Decision.SPLIT);
        pair.put(new Pair<>(Value.THREE, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.THREE, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.THREE, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.THREE, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.THREE, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.THREE, Value.SEVEN), Decision.SPLIT);
        pair.put(new Pair<>(Value.FOUR, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.FOUR, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.FIVE, Value.TWO), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.THREE), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.FOUR), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.FIVE), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.SIX), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.SEVEN), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.EIGHT), Decision.DOUBLE);
        pair.put(new Pair<>(Value.FIVE, Value.NINE), Decision.DOUBLE);
        pair.put(new Pair<>(Value.SIX, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.SIX, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.SIX, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.SIX, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.SIX, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.SEVEN, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.SEVEN, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.SEVEN, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.SEVEN, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.SEVEN, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.SEVEN), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.EIGHT), Decision.SPLIT);
        pair.put(new Pair<>(Value.EIGHT, Value.NINE), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.TWO), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.THREE), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.FOUR), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.FIVE), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.SIX), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.SEVEN), Decision.STAND);
        pair.put(new Pair<>(Value.NINE, Value.EIGHT), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.NINE), Decision.SPLIT);
        pair.put(new Pair<>(Value.NINE, Value.TEN), Decision.STAND);
        pair.put(new Pair<>(Value.NINE, Value.JACK), Decision.STAND);
        pair.put(new Pair<>(Value.NINE, Value.QUEEN), Decision.STAND);
        pair.put(new Pair<>(Value.NINE, Value.KING), Decision.STAND);
        pair.put(new Pair<>(Value.NINE, Value.ACE), Decision.STAND);
        for(Value i : List.of(Value.TEN, Value.JACK, Value.QUEEN, Value.KING)) {
            pair.put(new Pair<>(i, Value.TWO), Decision.STAND);
            pair.put(new Pair<>(i, Value.THREE), Decision.STAND);
            pair.put(new Pair<>(i, Value.FOUR), Decision.STAND);
            pair.put(new Pair<>(i, Value.FIVE), Decision.STAND);
            pair.put(new Pair<>(i, Value.SIX), Decision.STAND);
            pair.put(new Pair<>(i, Value.SEVEN), Decision.STAND);
            pair.put(new Pair<>(i, Value.EIGHT), Decision.STAND);
            pair.put(new Pair<>(i, Value.NINE), Decision.STAND);
            pair.put(new Pair<>(i, Value.TEN), Decision.STAND);
            pair.put(new Pair<>(i, Value.JACK), Decision.STAND);
            pair.put(new Pair<>(i, Value.QUEEN), Decision.STAND);
            pair.put(new Pair<>(i, Value.KING), Decision.STAND);
            pair.put(new Pair<>(i, Value.ACE), Decision.STAND);
        }


        var pattern = new Pattern(normal, ace, pair);

        return new Strategy(pattern);
    }
}
