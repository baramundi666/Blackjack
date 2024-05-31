package model.round;

import model.basic.Card;
import model.basic.Deck;
import model.basic.Hand;
import model.basic.Player;
import model.elementary.Decision;
import model.elementary.Result;
import model.elementary.Status;
import model.elementary.Value;
import run.statistics.Data;
import run.statistics.Tracker;
import model.strategy.CardCounter;

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
                hand.setStatus(Status.WAITING);
                if (currentPoints>21) {
                    while (currentPoints>21 && hand.getAceCount()>0) {
                        hand.subtractFromAceCount();
                        hand.setPoints(currentPoints-10);
                        currentPoints = hand.getPoints();
                    }
                    if(currentPoints>21) {
                        hand.setStatus(Status.BUST);
                    }
                }
            }
            case SPLIT -> {
                hand.setHasBeenSplit(true);
                var player = hand.getPlayer();
                var card = hand.splitHand();
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }
                var newHand = new Hand(player);
                newHand.setHasBeenSplit(true);
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
                // Split Aces -> 1 card dealt for each ace, no Blackjack
                if(card.getValue() == Value.ACE) {
                    hand.setStatus(Status.WAITING);
                    newHand.setStatus(Status.WAITING);
                }
            }
            case SURRENDER -> {
                hand.setStatus(Status.BUST);
            }
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
                !(dealerPoints==21 && dealerHand.getCards().size()==2) &&
                !playerHand.hasBeenSplit()){
            // No Blackjack after Splitting Aces
            result = Result.BLACKJACK;
        }
        // Lose All to a Natural
        else if(dealerPoints==21 && dealerHand.getCards().size()==2) {
            result = Result.LOSE;
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
        return result;
    }

    protected void finishBets() {
        var hands = new ArrayList<Hand>();
        for(Player player : players) {
            hands.addAll(player.getHands());
        }
        var dealerHand = dealer.getHands().get(0);
        for(Hand playerHand : hands) {

            var result = getResult(dealerHand, playerHand);
            if(!Objects.isNull(tracker)) tracker.notify(new Data(result));
            var player = playerHand.getPlayer();
            switch(result) {
                case WIN -> player.setBalance(player.getBalance()+playerHand.getBet()*2);
                case LOSE -> {
                    // if dealer has BJ and player has bought insurance
                    if(dealerHand.getCards().size()==2 && dealerHand.getPoints()==21 &&
                            playerHand.isHandInsured())
                        player.setBalance(player.getBalance()+playerHand.getBet()*1.5);
                    }
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
