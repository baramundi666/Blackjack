package run;

import model.Card;
import model.Deck;
import model.Player;
import model.basic.Decision;
import model.round.TestRound;
import strategy.AnyStrategy;

import java.util.List;

public class TestSimulation {
    private final int simulationLength;
    private final int deckSize;
    private final List<Card> cards;
    private final Card dealerCard;
    private final AnyStrategy strategy;

    public TestSimulation(AnyStrategy strategy, int deckSize, int simulationLength, List<Card> cards, Card dealerCard) {
        this.strategy = strategy;
        this.deckSize = deckSize;
        this.simulationLength  = simulationLength;
        this.cards = cards;
        this.dealerCard = dealerCard;
    }

    public void start() {
        System.out.println("Simulation result: ");
        List<Decision> list;
        if(cards.get(0).getValue()==cards.get(1).getValue()) {
            list = List.of(Decision.HIT, Decision.DOUBLE,
                    Decision.SPLIT, Decision.STAND, Decision.SURRENDER);
        }
        else {
            list = List.of(Decision.HIT, Decision.DOUBLE,
                    Decision.STAND, Decision.SURRENDER);
        }

        for(Decision decision : list) {
            var listener = new Tracker();
            var deck = new Deck(deckSize);
            var player = new Player();
            for(int i=0;i<simulationLength;i++) {
                var round = new TestRound(player, 1, deck, decision, strategy, cards, dealerCard);
                round.registerListener(listener);
                round.play();
            }
            System.out.println(decision + ": " + player.getBalance()/simulationLength*100 + "%");
        }
    }
}
