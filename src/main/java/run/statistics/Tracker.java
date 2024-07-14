package run.statistics;

import model.basic.Player;
import run.simulation.Simulation;
import run.statistics.Data;
import run.statistics.Listener;

import java.util.LinkedList;
import java.util.List;

public class Tracker implements Listener {
    private int win = 0;
    private int lose = 0;
    private int blackjack = 0;
    private int push = 0;
    private int surrender = 0;
    private int handCount = 0;

    private final List<Simulation> simulationList = new LinkedList<>();

    public void notify(Data data) {
        switch(data.getResult()) {
            case WIN -> win++;
            case LOSE -> lose++;
            case PUSH -> push++;
            case BLACKJACK -> blackjack++;
            case SURRENDER -> surrender++;
        }
    }

    public void addSimulation(Simulation simulation) {
        simulationList.add(simulation);
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public int getBlackjack() {
        return blackjack;
    }

    public int getPush() {
        return push;
    }

    public int getSurrender() {return surrender;}

    public int getHandCount() {
        return handCount;
    }

    public void setHandCount(int handCount) {
        this.handCount = handCount;
    }

    public void getResults() {
        double totalBalance = 0.0;
        for(Simulation simulation : simulationList) {
            var players = simulation.getPlayers();
            for(Player player : players) {
                System.out.println("Player " + player.getPlayerId() + "'s balance: " + player.getBalance());
                totalBalance += player.getBalance();
            }
        }

        int handCount = getHandCount();
        int resultCount = 0;
        System.out.println("Win: " +
                getPercentage(getWin(), handCount) + "%");
        resultCount+=getWin();
        System.out.println("Lose: " +
                getPercentage(getLose(), handCount) + "%");
        resultCount+=getLose();
        System.out.println("Push: " +
                getPercentage(getPush(), handCount) + "%");
        resultCount+=getPush();
        System.out.println("Surrender: " +
                getPercentage(getSurrender(), handCount) + "%");
        resultCount+=getSurrender();
        System.out.println("Blackjack: " +
                getPercentage(getBlackjack(), handCount) + "%");
        resultCount+=getBlackjack();
        System.out.println("Status: "+ resultCount + "/" + handCount);
        System.out.println("Simulation result: " +
                totalBalance/handCount*100 + "%");
    }

    private double getPercentage(int value, int handCount) {
        double val = (double) value / handCount;
        val*=10000;
        return (double) Math.round(val) / 100;
    }

}
