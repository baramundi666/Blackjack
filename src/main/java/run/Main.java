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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import run.statistics.Tracker;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Main {
    public static void main(String[] args) {
        //runConsole();
        runSimulation();
        //runTest();
        //runUI();
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
        // Concurrency
        int n = 4;
        List<Future<?>> futures = new ArrayList<Future<?>>();
        ExecutorService executor = newFixedThreadPool(n);

        // Strategy
        var patternReader = new PatternReader();
//        var newStrategy = new AnyStrategy(patternReader.readPattern(
//                PatternDatabase.basicStrategyWithCardCounting, PatternDatabase.standardInsurance));
        var newStrategy = new AnyStrategy(patternReader.readPattern(
                PatternDatabase.newPattern, null));

        // Simulation
        int deckSize = 6;
        int simulationLength = (int) Math.pow(10,9);
        var listener = new Tracker();
        for(int i=0; i<n; i++) {
            var simulation = new Simulation(newStrategy, listener, deckSize, simulationLength/n);
            listener.addSimulation(simulation);
            var thread = new Thread(simulation);
            Future<?> f = executor.submit(thread);
            futures.add(f);
        }

        try {
            for(Future<?> future : futures)
                future.get();
            listener.getResults();
            executor.shutdown();
        }
        catch (Exception e) {
            System.out.println("ERROR WITH CONCURRENCY");
        }

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
