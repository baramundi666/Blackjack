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

public class TestRound extends AbstractRound{
    private final AbstractStrategy strategy;
    private final List<Card> cards;
    private final Card dealerCard;
    private final Decision firstDecision;

    public TestRound(Player player, int playerCount, Deck deck,
                     Decision decision, AbstractStrategy strategy, List<Card> cards, Card dealerCard) {
        super(playerCount, deck);
        this.firstDecision = decision;
        this.strategy = strategy;
        this.cards = cards;
        this.dealerCard = dealerCard;
        this.players.add(player);
    }

    @Override
    public void play() {
        // Each player has one hand at the beginning
        int points = cards.get(0).getValue().getPoints()+cards.get(1).getValue().getPoints();
        for(Player player : players) {
            var newHand = new Hand(cards, points, player);
            player.addHand(newHand);
        }

        var dealerHand = new Hand(dealer);
        dealer.addHand(dealerHand);
        dealerHand.updateHand(dealerCard);
//        System.out.println("Dealer: ");
//        System.out.println("Hand: " + dealerCard.toString());

        makeBets();


        var hands = new ArrayList<Hand>();
        for (Player player : players) {
            hands.addAll(player.getHands());
        }
        int handsLeftCount  = playerCount;
        boolean flag = true;
        while(handsLeftCount>0) {
            handsLeftCount = 0;
            hands.clear();
            for (Player player : players) {
                hands.addAll(player.getHands());
            }
            for (Hand hand : hands) {
                if (hand.getStatus() == Status.PLAYING) {
//                    System.out.println("Player: " + hand.getPlayer().getPlayerId());
//                    System.out.println("Hand: " + hand.getHandId());
//                    for (Card card : hand.getCards()) {
//                        System.out.print(card.toString() + " ");
//                    }
//                    System.out.println();
//                    System.out.print("Input your decision: ");
//                    System.out.println(decision.toString());
//                    System.out.println();
                    Decision decision;
                    if(flag) {
                        decision = firstDecision;
                        flag = false;
                    }
                    else {
                        decision = strategy.getDecision(deck.getDeckSize(), new CardCounter(deck.getDeckSize()),
                                dealerHand, hand);
                    }
                    handleDecision(null, hand, decision);
                    handsLeftCount++;
                }
            }
        }

        while(dealerHand.getPoints()<17) {
            var nextCard = deck.getNextCard();
            dealerHand.updateHand(nextCard);
            var currentPoints = dealerHand.getPoints();
            if(currentPoints>21 && dealerHand.getAceCount()>0) {
                dealerHand.subtractFromAceCount();
                dealerHand.setPoints(currentPoints-10);
            }
        }

//        printResults(dealerHand, hands);
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
