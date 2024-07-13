package model.round;

import model.basic.Card;
import model.basic.Deck;
import model.basic.Hand;
import model.basic.Player;
import model.elementary.Answer;
import model.elementary.Decision;
import model.elementary.Status;
import model.elementary.Value;
import model.strategy.CardCounter;
import presenter.ChangeListener;
import presenter.TablePresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TableRound extends AbstractRound{

    private final TablePresenter listener;

    public TableRound(int playerCount, Deck deck, TablePresenter listener) {
        super(playerCount, deck);
        this.listener = listener;
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
            listener.updatePlayerHand(player, newHand);
        }


        var dealerCard = deck.getNextCard();
        var dealerHand = new Hand(dealer);
        dealerHand.updateHand(dealerCard);
        dealer.addHand(dealerHand);
        listener.updateDealerHand(dealerHand);

//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(System.in));
        if(dealerCard.getValue() == Value.ACE) {
            for(Player player : players) {
                var answer = listener.askInsurance(player);
                System.out.println("Player: " + player.getPlayerId());
                System.out.print("Do you want to buy insurance?: ");
                switch(answer) {
                    case YES -> {
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
            listener.updatePlayerHand(player, player.getHands().get(0));
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
                    var decision = listener.askPlayerDecision(hand.getPlayer(), hand);
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

        finishBets();
        clearHands();
    }

    @Override
    protected void handleDecision(CardCounter counter, Hand hand, Decision decision) {
        hand.updateDecision(decision);
        switch(decision) {
            case HIT -> {
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                listener.updatePlayerHand(hand.getPlayer(), hand);
                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }
                var currentPoints = hand.getPoints();
                if (currentPoints>21) {
                    while (currentPoints>21 && hand.getAceCount()>0) {
                        hand.subtractFromAceCount();
                        hand.setPoints(currentPoints-10);
                        currentPoints = hand.getPoints();
                    }
                    if(currentPoints>21) {
                        hand.setStatus(Status.BUST);
                        listener.updateHandStatus(hand);
                    }
                }
            }
            case STAND -> {
                hand.setStatus(Status.WAITING);
                listener.updateHandStatus(hand);
            }
            case DOUBLE -> {
                var player = hand.getPlayer();
                player.setBalance(player.getBalance()-hand.getBet());
                hand.setBet(hand.getBet()*2);
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                listener.updatePlayerHand(hand.getPlayer(), hand);
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
                        listener.updateHandStatus(hand);
                    }
                }
            }
            case SPLIT -> {
                hand.setHasBeenSplit(true);
                var player = hand.getPlayer();
                var card = hand.splitHand();

                // 2nd hand
                var newHand = new Hand(player);
                newHand.setHasBeenSplit(true);
                newHand.updateHand(card);
                listener.updatePlayerHand(player, newHand);

                // 1st hand
                listener.updatePlayerHand(player, hand);
                var nextCard = deck.getNextCard();
                hand.updateHand(nextCard);
                listener.updatePlayerHand(player, hand);

                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }

                // 2nd hand
                nextCard = deck.getNextCard();
                newHand.updateHand(nextCard);
                listener.updatePlayerHand(player, newHand);

                if(!Objects.isNull(counter)) {
                    counter.updateCount(nextCard);
                    counter.updateCurrentCardNumber();
                }
                player.setBalance(player.getBalance()- hand.getBet());
                newHand.setBet(hand.getBet());
                player.addHand(newHand);
                // Split Aces -> 1 card dealt for each ace, no Blackjack
                // to be implemented: special behaviour when split aces
                if(card.getValue() == Value.ACE) {
                    hand.setStatus(Status.WAITING);
                    newHand.setStatus(Status.WAITING);
                }
            }
            case SURRENDER -> {
                hand.setStatus(Status.BUST);
                listener.updateHandStatus(hand);
            }
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
