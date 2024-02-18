package model.round;

import model.*;
import model.basic.Decision;
import model.basic.Result;
import model.basic.Status;
import run.Data;
import run.Tracker;
import strategy.CardCounter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractRound {
    protected int playerCount;
    protected final Player dealer = new Player();
    protected final List<Player> players = new ArrayList<>(playerCount);
    protected final Deck deck;
    protected Tracker tracker;

    public AbstractRound(int playerCount, Deck deck) {
        this.playerCount = playerCount;
        this.deck = deck;
    }

    public void registerListener(Tracker tracker) {
        this.tracker = tracker;
    }

    public abstract void play() throws IOException;
    protected abstract void makeBets();

    protected void handleDecision(CardCounter counter, Hand hand, Decision decision) {
        hand.updateDecision(decision);
        switch(decision) {
            case HIT -> {
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }
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
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }

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
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }
                var newHand = new Hand(player);
                newHand.updateHand(card);
                nextCard = deck.getNextCard();
                newHand.updateHand(nextCard);
                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }
                player.setBalance(player.getBalance()- hand.getBet());
                newHand.setBet(hand.getBet());
                player.addHand(newHand);
            }
            case SURRENDER -> {
                hand.setStatus(Status.BUST);
            }
        }
    }

    protected void printResults(Hand dealerHand, List<Hand> hands) {
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

    protected Result getResult(Hand dealerHand, Hand playerHand) {
        Result result;
        int dealerPoints = dealerHand.getPoints();
        int playerPoints = playerHand.getPoints();
        if(playerHand.getDecision()==Decision.SURRENDER) {
            result = Result.SURRENDER;
        }
        else if(playerPoints>21) {
            result = Result.LOSE;
        }
        else if(playerPoints==21 && playerHand.getCards().size()==2 &&
                (dealerPoints!=21)){
            result = Result.BLACKJACK;
        }
        else if(dealerPoints==playerPoints){
            result = Result.PUSH;
        }
        else if(dealerPoints>21 || playerPoints>dealerPoints){
            result = Result.WIN;
        }
        else {
            result = Result.LOSE;
        }
        if(!Objects.isNull(tracker)) tracker.notify(new Data(result));
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
                case SURRENDER -> player.setBalance(player.getBalance()+playerHand.getBet()*0.5);
            }
        }

    }

    protected void clearHands() {
        for(Player player : players) {
            player.resetHands();
        }
        dealer.resetHands();
    }
}
