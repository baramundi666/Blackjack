package run;

import strategy.Strategy;

public class Main {
    public static void main(String[] args) {
//        var deck = new Deck(6);
//        var round = new Round(3, deck);
//        try {
//            round.play();
//        } catch (IOException | IllegalStateException e) {
//            throw new RuntimeException(e);
//        }
        // Empty constructor for "Basic Strategy"
        var basicStrategy = new Strategy();
        var simulation = new Simulation(basicStrategy, 6, 1000000);
        simulation.start();
    }


}
