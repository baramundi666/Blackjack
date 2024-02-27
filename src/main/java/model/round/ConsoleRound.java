package model.round;

import model.Card;
import model.Deck;
import model.Hand;
import model.Player;
import model.basic.Decision;
import model.basic.Status;
import model.basic.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsoleRound extends AbstractRound {

    public ConsoleRound(int playerCount, Deck deck) {
        super(playerCount, deck);
        for(int i=0;i<playerCount;i++) {
            players.add(new Player());
        }
    }

    @Override
    public void play() throws IllegalStateException, IOException {
        // Each player has one hand at the beginning
        for(Player player : players) {
            var newHand = new Hand(player);
            player.addHand(newHand);
            newHand.updateHand(deck.getNextCard());
        }

        makeBets();

        var dealerCard = deck.getNextCard();
        var dealerHand = new Hand(dealer);
        dealerHand.updateHand(dealerCard);
        dealer.addHand(dealerHand);
        System.out.println("Dealer: ");
        System.out.println("Hand: " + dealerCard);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        if(dealerCard.getValue() == Value.ACE) {
            for(Player player : players) {
                System.out.println("Player: " + player.getPlayerId());
                System.out.print("Do you want to buy insurance?: ");
                switch(reader.readLine()) {
                    case "yes", "Yes", "y", "Y" -> {
                        var hand = player.getHands().get(0);
                        hand.setHandInsured(true);
                        player.setBalance(player.getBalance() - hand.getBet()*0.5);
                    }

                }
                System.out.println();
            }
        }



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
                        case "hit","h" -> Decision.HIT;
                        case "stand","s" -> Decision.STAND;
                        case "double","d" -> Decision.DOUBLE;
                        case "split","p" -> Decision.SPLIT;
                        case "surrender","u" -> Decision.SURRENDER;
                        default -> throw new IllegalStateException(
                                "Unexpected value: " + reader.readLine());
                    };
                    handleDecision(null, hand, decision);
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
        finishBets();
        clearHands();
    }

    @Override
    protected void makeBets() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        for(Player player : players) {
            System.out.println("Player: " + player.getPlayerId());
            System.out.print("Input bet amount: ");
            double betAmount;
            try {
                betAmount = Double.parseDouble(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.getHands().get(0).setBet(betAmount);
        }
    }
}
