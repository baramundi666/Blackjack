package model;

import model.basic.Decision;
import model.basic.Result;
import model.basic.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRound {
    protected int playerCount;
    protected final Player dealer = new Player();
    protected final List<Player> players = new ArrayList<>(playerCount);
    protected final Deck deck;

    public AbstractRound(int playerCount, Deck deck) {
        this.playerCount = playerCount;
        for(int i=0;i<playerCount;i++) {
            players.add(new Player());
        }
        this.deck = deck;
    }

    public abstract void play() throws IOException;

    protected void handleDecision(Hand hand, Decision decision) {
        hand.updateDecision(decision);
        switch(decision) {
            case HIT -> {
                hand.updateHand(deck.getNextCard());
                var currentPoints = hand.getPoints();
                if (currentPoints>21) {
                    while (currentPoints>21 && hand.getAceCount()>0) {
                        hand.subtractFromAceCount();
                        hand.setPoints(currentPoints-10);
                        hand.setStatus(Status.WAITING);
                        currentPoints= hand.getPoints();
                    }
                    if(currentPoints>21) {
                        hand.setStatus(Status.BUST);
                    }
                }
            }
            case STAND -> hand.setStatus(Status.WAITING);
            case DOUBLE -> {
                hand.updateHand(deck.getNextCard());
                var currentPoints = hand.getPoints();
                if (currentPoints>21) {
                    while (currentPoints>21 && hand.getAceCount()>0) {
                        hand.subtractFromAceCount();
                        hand.setPoints(currentPoints-10);
                        hand.setStatus(Status.WAITING);
                        currentPoints= hand.getPoints();
                    }
                    if(currentPoints>21) {
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

    protected void printResults(Hand dealerHand, List<Hand> hands) {
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
