package view;

import model.Deck;
import model.Round;
import model.RoundSimulation;

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
        for(int i=0;i<simulationLength;i++) {
            var round = new RoundSimulation(1, deck, strategy);
            round.play();
        }
    }
}
