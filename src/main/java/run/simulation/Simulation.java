package run.simulation;

import model.basic.Deck;
import model.basic.Player;
import model.round.SimulationRound;
import run.statistics.Tracker;
import model.strategy.AbstractStrategy;
import model.strategy.CardCounter;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable{
    private final int simulationLength;
    private final int deckSize;
    private final AbstractStrategy strategy;
    private final Tracker listener;
    private final List<Player> players = new LinkedList<>();

    public Simulation(AbstractStrategy strategy, Tracker listener, int deckSize, int simulationLength) {
        this.strategy = strategy;
        this.deckSize = deckSize;
        this.simulationLength  = simulationLength;
        this.listener = listener;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void run() {
        var deck = new Deck(deckSize);
        var counter = new CardCounter(deckSize);
        var player1 = new Player();
        players.add(player1);
        for (int i = 0; i < simulationLength; i++) {
            var round = new SimulationRound(players, 1, deck, strategy, counter);
            round.registerListener(listener);
            round.play();
        }
    }
}
