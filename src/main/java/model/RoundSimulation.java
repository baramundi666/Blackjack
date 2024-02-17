package model;

import model.basic.Decision;
import model.basic.Status;
import view.Strategy;

import java.util.ArrayList;
import java.util.List;

public class RoundSimulation extends AbstractRound{
    private final Strategy strategy;


    public RoundSimulation(List<Player> players, int playerCount, Deck deck, Strategy strategy) {
        super(playerCount, deck);
        this.strategy = strategy;
        this.players.addAll(players);
    }

    @Override
    public void play() {
        // Each player has one hand at the beginning
        for(Player player : players) {
            var newHand = new Hand(player);
            player.addHand(newHand);
            newHand.updateHand(deck.getNextCard());
        }

        var dealerCard = deck.getNextCard();
        var dealerHand = new Hand(dealer);
        dealer.addHand(dealerHand);
        dealerHand.updateHand(dealerCard);
//        System.out.println("Dealer: ");
//        System.out.println("Hand: " + dealerCard.toString());

        for(Player player : players) {
            player.getHands().get(0).updateHand(deck.getNextCard());
        }


        makeBets(players);


        var hands = new ArrayList<Hand>();
        for (Player player : players) {
            hands.addAll(player.getHands());
        }
        int handsLeftCount  = playerCount;
        while(handsLeftCount>0) {
            handsLeftCount = 0;
            hands.clear();
            for (Player player : players) {
                hands.addAll(player.getHands());
            }
            for (Hand hand : hands) {
                if (hand.getStatus() == Status.PLAYING) {
//                    System.out.println("Player: " + hand.getPlayer().getPlayerId());
//                    System.out.println("Hand: " + hand.getHandId());
//                    for (Card card : hand.getCards()) {
//                        System.out.print(card.toString() + " ");
//                    }
//                    System.out.println();
//                    System.out.print("Input your decision: ");
                    Decision decision = strategy.getDecision(dealerHand, hand);
//                    System.out.println(decision.toString());
//                    System.out.println();
                    handleDecision(hand, decision);
                    handsLeftCount++;
                }
            }
        }

        while(dealerHand.getPoints()<17) {
            dealerHand.updateHand(deck.getNextCard());
            var currentPoints = dealerHand.getPoints();
            if(currentPoints>21 && dealerHand.getAceCount()>0) {
                dealerHand.subtractFromAceCount();
                dealerHand.setPoints(currentPoints-10);
            }
        }

//        printResults(dealerHand, hands);
        finishBets();
        clearHands();
    }

    private void makeBets(List<Player> players) {
        for(Player player : players) {
            player.setBalance(player.getBalance()-1);
            player.getHands().get(0).setBet(1);
        }
    }
}
