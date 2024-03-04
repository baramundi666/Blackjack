package model.round;

import model.basic.Deck;
import model.basic.Hand;
import model.basic.Player;
import model.elementary.Decision;
import model.elementary.Status;
import model.elementary.Value;
import model.strategy.AbstractStrategy;
import model.strategy.CardCounter;

import java.util.ArrayList;
import java.util.List;

public class SimulationRound extends AbstractRound {
    private final AbstractStrategy strategy;
    private final CardCounter counter;


    public SimulationRound(List<Player> players, int playerCount, Deck deck,
                           AbstractStrategy strategy, CardCounter counter) {
        super(playerCount, deck);
        this.strategy = strategy;
        this.counter = counter;
        this.players.addAll(players);
    }

    @Override
    public void play() {
        // Each player has one hand at the beginning
        for(Player player : players) {
            var newHand = new Hand(player);
            player.addHand(newHand);
            var nextCard = deck.getNextCard();
            newHand.updateHand(nextCard);
            counter.updateCount(nextCard);
            counter.updateCurrentCardNumber();
        }

        var dealerCard = deck.getNextCard();
        var dealerHand = new Hand(dealer);
        dealer.addHand(dealerHand);
        dealerHand.updateHand(dealerCard);
        counter.updateCount(dealerCard);
        counter.updateCurrentCardNumber();
//        System.out.println("Dealer: ");
//        System.out.println("Hand: " + dealerCard.toString());

        for(Player player : players) {
            var nextCard = deck.getNextCard();
            player.getHands().get(0).updateHand(nextCard);
            counter.updateCount(nextCard);
            counter.updateCurrentCardNumber();
        }


        makeBets();


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
                    Decision decision = strategy.getDecision(deck.getDeckSize(),counter, dealerHand, hand);
//                    System.out.println("Player: " + hand.getPlayer().getPlayerId());
//                    System.out.println("Hand: " + hand.getHandId());
//                    for (Card card : hand.getCards()) {
//                        System.out.print(card.toString() + " ");
//                    }
//                    System.out.println();
//                    System.out.print("Input your decision: ");
//                    System.out.println(decision.toString());
//                    System.out.println();
                    handleDecision(counter, hand, decision);
                    handsLeftCount++;
                }
            }
        }

        tracker.setHandCount(tracker.getHandCount()+hands.size());

        boolean areAllHandsOver = true;
        for(Hand hand : hands) {
            // If Blackjack, no reason for dealer to deal rest of the cards
            if(hand.getCards().size()==2 && hand.getPoints()==21 &&
            dealerHand.getPoints()!=11 && dealerHand.getPoints()!=10) continue;
            var status = hand.getStatus();
            if (status == Status.PLAYING || status == Status.WAITING) {
                // Dealer has to deal his cards
                areAllHandsOver = false;
                break;
            }
        }
        if(!areAllHandsOver) {
            while (dealerHand.getPoints() < 17) {
                var nextCard = deck.getNextCard();
                dealerHand.updateHand(nextCard);
                counter.updateCount(nextCard);
                counter.updateCurrentCardNumber();
                var currentPoints = dealerHand.getPoints();
                if (currentPoints > 21 && dealerHand.getAceCount() > 0) {
                    dealerHand.subtractFromAceCount();
                    dealerHand.setPoints(currentPoints - 10);
                }
            }
        }

        //printResults(dealerHand, hands);
        finishBets();
        clearHands();
    }

    @Override
    protected void makeBets() {
        for(Player player : players) {
            player.setBalance(player.getBalance()-1);
            player.getHands().get(0).setBet(1);
        }
    }
}
