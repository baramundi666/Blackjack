package model.round;

import model.Card;
import model.Deck;
import model.Hand;
import model.Player;
import model.basic.Decision;
import model.basic.Status;
import strategy.AbstractStrategy;
import strategy.CardCounter;

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
                    Decision decision = strategy.getDecision(counter, dealerHand, hand);
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
            var status = hand.getStatus();
            if(status==Status.PLAYING || status==Status.WAITING) areAllHandsOver=false;
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
