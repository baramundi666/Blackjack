package model;

import model.basic.Decision;
import model.basic.Result;
import model.basic.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Round {
    private int playerCount;
    private final Player dealer = new Player();
    private final List<Player> players = new ArrayList<>(playerCount);
    private final Deck deck = new Deck(6);

    public Round(int playerCount) {
        this.playerCount = playerCount;
        for(int i=0;i<playerCount;i++) {
            players.add(new Player());
        }
    }

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
            if(currentPoints>21 && dealerHand.getAceCount()>0) {
                dealerHand.subtractFromAceCount();
                dealerHand.setPoints(currentPoints-10);
            }
        }

        printResults(dealerHand, hands);

    }



    private void handleDecision(Hand hand, Decision decision) {
        hand.updateDecision(decision);
        switch(decision) {
            case HIT -> {
                hand.updateHand(deck.getNextCard());
                var currentPoints = hand.getPoints();
                if (currentPoints>21) {
                    if (hand.getAceCount()>0) {
                        hand.subtractFromAceCount();
                        hand.setPoints(currentPoints-10);
                    }
                    else {
                        hand.setStatus(Status.BUST);
                    }
                }
            }
            case STAND -> hand.setStatus(Status.WAITING);
            case DOUBLE -> {
                hand.updateHand(deck.getNextCard());
                var currentPoints = hand.getPoints();
                if (currentPoints>21) {
                    if (hand.getAceCount()>0) {
                        hand.subtractFromAceCount();
                        hand.setPoints(currentPoints-10);
                        hand.setStatus(Status.WAITING);
                    }
                    else {
                        hand.setStatus(Status.BUST);
                    }
                }
                hand.setStatus(Status.WAITING);
            }
            case SPLIT -> {
                var player = hand.getPlayer();
                var card = hand.splitHand();
                hand.updateHand(deck.getNextCard());
                var newHand = new Hand(player);
                newHand.updateHand(card);
                newHand.updateHand(deck.getNextCard());
                player.addHand(newHand);
            }
        }
    }

    private void printResults(Hand dealerHand, List<Hand> hands) {
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
        }
    }

    public Result getResult(Hand dealerHand, Hand playerHand) {
        int dealerPoints = dealerHand.getPoints();
        int playerPoints = playerHand.getPoints();
        if(playerPoints==21) return Result.BLACKJACK;
        if(playerPoints>21) return Result.LOSE;
        if(dealerPoints>21 || playerPoints>dealerPoints) return Result.WIN;
        if(dealerPoints==playerPoints) return Result.PUSH;
        return Result.LOSE;
    }

}
