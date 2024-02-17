package view;

import model.Deck;
import model.Player;
import model.Round;
import model.RoundSimulation;

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
        var deck = new Deck(deckSize);
        var player1 = new Player();
        var players = List.of(player1);
        for(int i=0;i<simulationLength;i++) {
            var round = new RoundSimulation(players, 1, deck, strategy);
            round.play();
        }
        for(Player player : players) {
            System.out.println("Player " + player.getPlayerId() + "'s balance: " + player.getBalance());
        }
    }
}
