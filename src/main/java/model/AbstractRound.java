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
    protected Listener listener;

    public AbstractRound(int playerCount, Deck deck) {
        this.playerCount = playerCount;
        this.deck = deck;
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
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
                var player = hand.getPlayer();
                player.setBalance(player.getBalance()-hand.getBet());
                hand.setBet(hand.getBet()*2);
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
                player.setBalance(player.getBalance()- hand.getBet());
                newHand.setBet(hand.getBet());
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
            System.out.println();
        }
    }

    protected Result getResult(Hand dealerHand, Hand playerHand) {
        Result result;
        int dealerPoints = dealerHand.getPoints();
        int playerPoints = playerHand.getPoints();
        if(playerPoints>21) {
            result = Result.LOSE;
        }
        else if(playerPoints==21 && playerHand.getCards().size()==2 && dealerPoints!=21){
            result = Result.BLACKJACK;
        }
        else if(playerPoints==21 && dealerPoints==21){
            result = Result.PUSH;
        }
        else if(dealerPoints>21 || playerPoints>dealerPoints){
            result = Result.WIN;
        }
        else if(dealerPoints==playerPoints){
            result = Result.PUSH;
        }
        else {
            result = Result.LOSE;
        }
        listener.notify(result);
        return result;
    }

    protected void finishBets() {
        var hands = new ArrayList<Hand>();
        for(Player player : players) {
            hands.addAll(player.getHands());
        }

        for(Hand playerHand : hands) {
            var result = getResult(dealer.getHands().get(0), playerHand);
            var player = playerHand.getPlayer();
            switch(result) {
                case WIN -> player.setBalance(player.getBalance()+playerHand.getBet()*2);
                case LOSE -> {}
                case PUSH -> player.setBalance(player.getBalance()+playerHand.getBet());
                case BLACKJACK -> player.setBalance(player.getBalance()+playerHand.getBet()*2.5);
            }
        }

    }

    protected void clearHands() {
        for(Player player : players) {
            player.resetHands();
        }
    }
}
