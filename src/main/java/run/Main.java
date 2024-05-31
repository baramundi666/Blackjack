package run;

import model.basic.Card;
import model.basic.Deck;
import model.elementary.Suit;
import model.elementary.Value;
import model.round.ConsoleRound;
import model.pattern.PatternReader;
import model.strategy.AnyStrategy;
import run.simulation.Simulation;
import run.simulation.TestSimulation;
import run.statistics.PatternDatabase;

import java.io.IOException;
import java.util.List;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        //runConsole();
        //runSimulation();
        //runTest();
        runUI();
    }

    private static void runUI() {
        Application.launch(TableApp.class);
    }

    private static void runConsole() {
        var deck = new Deck(6);
        var round = new ConsoleRound(2, deck);
        try {
            round.play();
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runSimulation() {
        int deckSize = 6;
        int simulationLength = (int) Math.pow(10,7);
        var patternReader = new PatternReader();
//        var newStrategy = new AnyStrategy(patternReader.readPattern(
//                PatternDatabase.basicStrategyWithCardCounting, PatternDatabase.standardInsurance));
        var newStrategy = new AnyStrategy(patternReader.readPattern(
                PatternDatabase.basicStrategyWithCardCounting, PatternDatabase.standardInsurance));
        var simulation = new Simulation(newStrategy, deckSize, simulationLength);
        simulation.start();
    }

    private static void runTest() {
        var two = new Card(Value.TWO, Suit.CLUB);
        var three = new Card(Value.THREE, Suit.CLUB);
        var four = new Card(Value.FOUR, Suit.CLUB);
        var five = new Card(Value.FIVE, Suit.CLUB);
        var six = new Card(Value.SIX, Suit.CLUB);
        var seven = new Card(Value.SEVEN, Suit.CLUB);
        var eight = new Card(Value.EIGHT, Suit.CLUB);
        var nine = new Card(Value.NINE, Suit.CLUB);
        var ten = new Card(Value.TEN, Suit.CLUB);
        var ace = new Card(Value.ACE, Suit.CLUB);

        var playerCards = List.of(eight, eight);
        var dealerCard = ten;

        var patternReader = new PatternReader();
        var strategy = new AnyStrategy(patternReader.readPattern(
                PatternDatabase.newPattern, PatternDatabase.standardInsurance));
        int deckSize = 6;
        var testSimulation = new TestSimulation(strategy, deckSize,
                1000000, playerCards, dealerCard);
        testSimulation.start();
    }
}
