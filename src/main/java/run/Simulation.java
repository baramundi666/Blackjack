package run;

import model.*;
import model.round.SimulationRound;
import strategy.AbstractStrategy;
import strategy.CardCounter;

import java.util.List;

public class Simulation {
    private final int simulationLength;
    private final int deckSize;
    private final AbstractStrategy strategy;

    public Simulation(AbstractStrategy strategy, int deckSize, int simulationLength) {
        this.strategy = strategy;
        this.deckSize = deckSize;
        this.simulationLength  = simulationLength;
    }

    public void start() {
        var listener = new Tracker();
        var deck = new Deck(deckSize);
        var counter = new CardCounter(deckSize);
        var player1 = new Player();
        var players = List.of(player1);
        for(int i=0;i<simulationLength;i++) {
            var round = new SimulationRound(players, 1, deck, strategy, counter);
            round.registerListener(listener);
            round.play();
        }
        for(Player player : players) {
            System.out.println("Player " + player.getPlayerId() + "'s balance: " + player.getBalance());
        }
        System.out.println("Win: " + listener.getWin());
        System.out.println("Lose: " + listener.getLose());
        System.out.println("Push: " + listener.getPush());
        System.out.println("Blackjack: " + listener.getBlackjack());
        System.out.println("Simulation result: " + player1.getBalance()/simulationLength*100 + "%");
    }
}
