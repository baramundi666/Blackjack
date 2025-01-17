package model.round;

import model.basic.Card;
import model.basic.Deck;
import model.basic.Hand;
import model.basic.Player;
import model.elementary.Decision;
import model.elementary.Status;
import model.elementary.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    private void printResults(Hand dealerHand, List<Hand> hands) {
        System.out.println();
        System.out.println("Results:");
        System.out.println();
        System.out.println("Dealer: ");
        for(Card card : dealerHand.getCards()) {
            System.out.print(card.toString() + " ");
        }
        System.out.println("(Points: " + dealerHand.getPoints() + ")");

        for(Hand hand: hands) {
            System.out.println("Player: " + hand.getPlayer().getPlayerId());
            System.out.println("Hand: " + hand.getHandId());
            for (Card card : hand.getCards()) {
                System.out.print(card.toString() + " ");
            }
            System.out.print("(Points: " + hand.getPoints() + ", ");
            System.out.println("Result:" + getResult(dealerHand, hand).toString() + ")");
            System.out.println();
        }
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
