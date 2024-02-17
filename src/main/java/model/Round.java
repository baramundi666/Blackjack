package model;

import model.basic.Decision;
import model.basic.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Round extends AbstractRound{

    public Round(int playerCount, Deck deck) {
        super(playerCount, deck);
        for(int i=0;i<playerCount;i++) {
            players.add(new Player());
        }
    }

    @Override
    public void play() throws IllegalStateException, IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

//        for(Player player : players) {
//            System.out.println("Player: " + player.getPlayerId());
//            System.out.print("Input bet amount: ");
//            var betAmount = Double.parseDouble(reader.readLine());
//            player.bet(betAmount);
//        }

        // Each player has one hand at the beginning
        for(Player player : players) {
            var newHand = new Hand(player);
            player.addHand(newHand);
            newHand.updateHand(deck.getNextCard());
        }

        var dealerCard = deck.getNextCard();
        var dealerHand = new Hand(dealer);
        dealerHand.updateHand(dealerCard);
        System.out.println("Dealer: ");
        System.out.println("Hand: " + dealerCard.toString());

        for(Player player : players) {
            player.getHands().get(0).updateHand(deck.getNextCard());
        }


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
                    System.out.println("Player: " + hand.getPlayer().getPlayerId());
                    System.out.println("Hand: " + hand.getHandId());
                    for (Card card : hand.getCards()) {
                        System.out.print(card.toString() + " ");
                    }
                    System.out.println();
                    System.out.print("Input your decision: ");
                    Decision decision = switch (reader.readLine()) {
                        case "hit" -> Decision.HIT;
                        case "stand" -> Decision.STAND;
                        case "double" -> Decision.DOUBLE;
                        case "split" -> Decision.SPLIT;
                        default -> throw new IllegalStateException("Unexpected value: " + reader.readLine());
                    };
                    handleDecision(hand, decision);
                    handsLeftCount++;
                }
            }
        }

        while(dealerHand.getPoints()<17) {
            dealerHand.updateHand(deck.getNextCard());
            var currentPoints = dealerHand.getPoints();
            while(currentPoints>21 && dealerHand.getAceCount()>0) {
                dealerHand.subtractFromAceCount();
                dealerHand.setPoints(currentPoints-10);
            }
        }

        printResults(dealerHand, hands);

    }
}
