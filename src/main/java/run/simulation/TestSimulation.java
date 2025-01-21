package run.simulation;

import model.basic.Card;
import model.basic.Deck;
import model.basic.Player;
import model.elementary.Decision;
import model.elementary.Pair;
import model.elementary.Value;
import model.pattern.Instruction;
import model.round.TestRound;
import run.statistics.Tracker;
import model.strategy.AnyStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class TestSimulation {
    private final int simulationLength;
    private final int deckSize;
    private final List<Card> cards;
    private final Card dealerCard;
    private final AnyStrategy strategy;
    private final Lock lock;

    public TestSimulation(AnyStrategy strategy, int deckSize, int simulationLength, List<Card> cards, Card dealerCard, Lock lock) {
        this.strategy = strategy;
        this.deckSize = deckSize;
        this.simulationLength  = simulationLength;
        this.cards = cards;
        this.dealerCard = dealerCard;
        this.lock = lock;
    }

    public HashMap<Decision, Double> start() {
        var simulationResult = new HashMap<Decision, Double>();
        System.out.println("Test result for:");
        System.out.print("player cards: ");
        System.out.print(cards.get(0).getValue());
        System.out.print(", ");
        System.out.println(cards.get(1).getValue());
        System.out.print("dealer card: ");
        System.out.println(dealerCard.getValue());

        var decisionList = new ArrayList<>(List.of(Decision.HIT, Decision.STAND, Decision.DOUBLE));
        if(cards.get(0)==cards.get(1)) {
            decisionList.add(Decision.SPLIT);
        }
        if(dealerCard.getValue()!=Value.ACE) {
            simulationResult.put(Decision.SURRENDER, -50.0);
            System.out.println(Decision.SURRENDER + ": " + -50.0 + "%");
        }

        var threadList = new LinkedList<Thread>();

        for(Decision decision : decisionList) {
            var pattern = strategy.getPattern();
            pattern.pair().put(new Pair<>(cards.get(0).getValue(), dealerCard.getValue()), new Instruction(decision));
            strategy.setPattern(pattern);
            var thread = new Thread(() -> {
                var listener = new Tracker();
                var deck = new Deck(deckSize);
                var player = new Player();
                for(int i=0;i<simulationLength;i++) {
                    var round = new TestRound(player, 1, deck, decision, strategy, cards, dealerCard);
                    round.registerListener(listener);
                    round.play();
                }
                var percentage = 100 * player.getBalance()/simulationLength;
                simulationResult.put(decision, percentage);
                System.out.println(decision + ": " + percentage + "%");
            });

            threadList.add(thread);
        }

        for (var thread : threadList) {
            thread.start();
        }

        for (var thread : threadList) {
            try {
                thread.join();
            }
            catch (InterruptedException ignored) {}
        }

        return simulationResult;
    }
}
