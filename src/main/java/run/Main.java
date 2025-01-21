package run;

import model.basic.Card;
import model.basic.Deck;
import model.elementary.Decision;
import model.elementary.Pair;
import model.elementary.Suit;
import model.elementary.Value;
import model.pattern.Instruction;
import model.pattern.Pattern;
import model.round.ConsoleRound;
import model.pattern.PatternReader;
import model.strategy.AnyStrategy;
import run.simulation.Simulation;
import run.simulation.TestSimulation;
import run.statistics.PatternDatabase;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Application;
import run.statistics.Tracker;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Main {
    public static void main(String[] args) {
        //runConsole();
        runSimulation();
//        runTest();
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
        int n = 1;
//        List<Future<?>> futures = new ArrayList<Future<?>>();
//        ExecutorService executor = newFixedThreadPool(n);
//        var threadList = new LinkedList<Thread>();


        // Strategy
        var patternReader = new PatternReader();
//        var newStrategy = new AnyStrategy(patternReader.readPattern(
//                PatternDatabase.basicStrategyWithCardCounting, PatternDatabase.standardInsurance));
        var newStrategy = new AnyStrategy(patternReader.readPattern(
                PatternDatabase.testGeneratedPattern, null));

        // Simulation
        int deckSize = 6;
        int simulationLength = (int) Math.pow(10,9);
        var listener = new Tracker();
        for(int i=0; i<n; i++) {
            var simulation = new Simulation(newStrategy, listener, deckSize, simulationLength/n);
            listener.addSimulation(simulation);
            simulation.run();
//            var thread = new Thread(simulation);
//            Future<?> f = executor.submit(thread);
//            futures.add(f);
//            threadList.add(thread);
        }

//        for (var thread : threadList) {
//            thread.start();
//        }
//
//        for (var thread : threadList) {
//            try {
//                thread.join();
//            }
//            catch (InterruptedException ignored) {}
//        }

//        try {
//            for(Future<?> future : futures)
//                future.get();
            listener.getResults();
//            executor.shutdown();
//        }
//        catch (Exception e) {
//            System.out.println("ERROR WITH CONCURRENCY");
//        }

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
        var king = new Card(Value.KING, Suit.CLUB);
        var ace = new Card(Value.ACE, Suit.CLUB);

        var allCards = List.of(two, three, four, five, six, seven, eight, nine, ten, ace);

        int deckSize = 6;
        int simulationLength = (int) Math.pow(10,7);

        var generatedPattern = new Pattern(new HashMap<>(), new HashMap<>(), new HashMap<>(), null);
        var strategy = new AnyStrategy(generatedPattern);

        // normal 1
        for(Value value : Value.values())
            generatedPattern.normal().put(new Pair<>(21, value), new Instruction(Decision.STAND));
        for(var cardPair : List.of(
                new Pair<>(ten, king),
                new Pair<>(ten, nine),
                new Pair<>(ten, eight),
                new Pair<>(ten, seven),
                new Pair<>(ten, six),
                new Pair<>(ten, five),
                new Pair<>(ten, four),
                new Pair<>(ten, three)
        )) {
            for (var dealerCard : allCards) {
                var playerCards = List.of(cardPair.a(), cardPair.b());
                var testSimulation = new TestSimulation(strategy, deckSize,
                        simulationLength, playerCards, dealerCard, null);
                var simulationResult = testSimulation.start();
                var finalDecision = Decision.NONE;
                var finalPercentage = -200.0;
                for (var entrySet : simulationResult.entrySet()) {
                    if(entrySet.getValue() > finalPercentage) {
                        finalPercentage = entrySet.getValue();
                        finalDecision = entrySet.getKey();
                    }
                }
                var value = cardPair.a().getValue().getPoints() + cardPair.b().getValue().getPoints();
                generatedPattern.normal().put(new Pair<>(value, dealerCard.getValue()), new Instruction(finalDecision));
            }
        }

        // ace
        for(Value value : Value.values())
            generatedPattern.ace().put(new Pair<>(10, value), new Instruction(Decision.STAND));
        for(var card : List.of(nine, eight, seven, six, five, four, three, two)) {
            var playerCards = List.of(ace, card);

            for (var dealerCard : allCards) {
                var testSimulation = new TestSimulation(strategy, deckSize,
                        simulationLength, playerCards, dealerCard, null);
                var simulationResult = testSimulation.start();
                var value = card.getValue().getPoints();
                var finalDecision = Decision.NONE;
                var finalPercentage = -200.0;
                for (var entrySet : simulationResult.entrySet()) {
                    if(entrySet.getValue() > finalPercentage) {
                        finalPercentage = entrySet.getValue();
                        finalDecision = entrySet.getKey();
                    }
                }
                generatedPattern.ace().put(new Pair<>(value, dealerCard.getValue()), new Instruction(finalDecision));
            }
        }

        // normal 2
        strategy = new AnyStrategy(generatedPattern);
        for(var cardPair : List.of(
                new Pair<>(ten, two),
                new Pair<>(nine, two),
                new Pair<>(eight, two),
                new Pair<>(seven, two),
                new Pair<>(six, two),
                new Pair<>(five, two),
                new Pair<>(four, two),
                new Pair<>(three, two)
        )) {
            for (var dealerCard : allCards) {
                var value = cardPair.a().getValue().getPoints() + cardPair.b().getValue().getPoints();
                var newKey = new Pair<>(value, dealerCard.getValue());
                if (generatedPattern.normal().containsKey(newKey)) continue;
                var playerCards = List.of(cardPair.a(), cardPair.b());
                var testSimulation = new TestSimulation(strategy, deckSize,
                        simulationLength, playerCards, dealerCard, null);
                var simulationResult = testSimulation.start();
                var finalDecision = Decision.NONE;
                var finalPercentage = -200.0;
                for (var entrySet : simulationResult.entrySet()) {
                    if(entrySet.getValue() > finalPercentage) {
                        finalPercentage = entrySet.getValue();
                        finalDecision = entrySet.getKey();
                    }
                }
                generatedPattern.normal().put(newKey, new Instruction(finalDecision));
            }
        }

        // pair
        strategy = new AnyStrategy(generatedPattern);
        for (var card : allCards) {
            for (var dealerCard : allCards) {
                var playerCards = List.of(card, card);
                var testSimulation = new TestSimulation(strategy, deckSize,
                        simulationLength, playerCards, dealerCard, null);
                var simulationResult = testSimulation.start();
                var finalDecision = Decision.NONE;
                var finalPercentage = -200.0;
                for (var entrySet : simulationResult.entrySet()) {
                    if(entrySet.getValue() > finalPercentage) {
                        finalPercentage = entrySet.getValue();
                        finalDecision = entrySet.getKey();
                    }
                }
                generatedPattern.pair().put(new Pair<>(card.getValue(), dealerCard.getValue()), new Instruction(finalDecision));
            }
        }
        System.out.println("normal");
        for (var entrySet : generatedPattern.normal().entrySet()) {
            System.out.println(entrySet.getKey().a().toString() + ", " + entrySet.getKey().b().toString() + ": " + entrySet.getValue().getInstruction(0.0).toString());
        }
        System.out.println("ace");
        for (var entrySet : generatedPattern.ace().entrySet()) {
            System.out.println(entrySet.getKey().a().toString() + ", " + entrySet.getKey().b().toString() + ": " + entrySet.getValue().getInstruction(0.0).toString());
        }
        System.out.println("pair");
        for (var entrySet : generatedPattern.pair().entrySet()) {
            System.out.println(entrySet.getKey().a().toString() + ", " + entrySet.getKey().b().toString() + ": " + entrySet.getValue().getInstruction(0.0).toString());
        }
    }
}
