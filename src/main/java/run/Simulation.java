package run;

import model.*;
import model.round.RoundSimulation;
import strategy.Strategy;

import java.util.List;

public class Simulation {
    private final int simulationLength;
    private final int deckSize;
    private final Strategy strategy;

    public Simulation(Strategy strategy, int deckSize, int simulationLength) {
        this.strategy = strategy;
        this.deckSize = deckSize;
        this.simulationLength  = simulationLength;
    }

    public void start() {
        var listener = new Listener();
        var deck = new Deck(deckSize);
        var player1 = new Player();
        var players = List.of(player1);
        for(int i=0;i<simulationLength;i++) {
            var round = new RoundSimulation(players, 1, deck, strategy);
            round.registerListener(listener);
            round.play();
        }
        for(Player player : players) {
            System.out.println("Player " + player.getPlayerId() + "'s balance: " + player.getBalance());
        }
        System.out.println("Wins: " + listener.getWin());
        System.out.println("Lose: " + listener.getLose());
        System.out.println("Push: " + listener.getPush());
        System.out.println("Blackjack: " + listener.getBlackjack());
        System.out.println("Simulation result: " + player1.getBalance()/simulationLength*100 + "%");
    }
}
