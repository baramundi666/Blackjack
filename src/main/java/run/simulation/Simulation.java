package run.simulation;

import model.basic.Deck;
import model.basic.Player;
import model.round.SimulationRound;
import run.statistics.Tracker;
import model.strategy.AbstractStrategy;
import model.strategy.CardCounter;

import java.text.DecimalFormat;
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
        DecimalFormat f = new DecimalFormat("##.00");
        int handCount = listener.getHandCount();
        int resultCount = 0;
        System.out.println("Win: " +
                f.format((double)listener.getWin()/handCount*100) + "%");
        resultCount+=listener.getWin();
        System.out.println("Lose: " +
                f.format((double)listener.getLose()/handCount*100) + "%");
        resultCount+=listener.getLose();
        System.out.println("Push: " +
                f.format((double)listener.getPush()/handCount*100) + "%");
        resultCount+=listener.getPush();
        System.out.println("Surrender: " +
                f.format((double)listener.getSurrender()/handCount*100) + "%");
        resultCount+=listener.getSurrender();
        System.out.println("Blackjack: " +
                f.format((double)listener.getBlackjack()/handCount*100) + "%");
        resultCount+=listener.getBlackjack();
        System.out.println("Status: "+ resultCount + "/" + handCount);
        System.out.println("Simulation result: " + player1.getBalance()/handCount*100 + "%");
    }
}
