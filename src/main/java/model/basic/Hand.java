package model.basic;

import model.elementary.Decision;
import model.elementary.Status;
import model.elementary.Value;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Hand {
    private final UUID handId = UUID.randomUUID();
    private final Player player;
    private int points = 0;
    private int aceCount = 0;
    private final List<Card> cards = new LinkedList<>();
    private Status status = Status.PLAYING;
    private Decision decision = Decision.NONE;
    private double bet;
    private boolean isHandInsured = false;
    private boolean hasBeenSplit = false;


    public Hand(Player player) {
        this.player = player;
    }

    public Hand(List<Card> cards, int points, Player player) {
        this.cards.addAll(cards);
        this.points = points;
        this.player = player;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public UUID getHandId() {
        return handId;
    }

    public void updateDecision(Decision decision) {
        this.decision = decision;
    }

    public Decision getDecision() {
        return decision;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public List<Card> getCards() {
        return new LinkedList<>(cards);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void updateHand(Card card) {
        if(card.getValue()== Value.ACE) aceCount++;
        cards.add(card);
        setPoints(getPoints()+card.getValue().getPoints());
    }

    public Card splitHand() {
        var deletedCard = cards.remove(1);
        setPoints(getPoints()-deletedCard.getValue().getPoints());
        return deletedCard;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAceCount() {
        return aceCount;
    }

    public void subtractFromAceCount() {
        aceCount--;
    }

    public boolean isHandInsured() {
        return isHandInsured;
    }

    public void setHandInsured(boolean handInsured) {
        isHandInsured = handInsured;
    }

    public boolean hasBeenSplit() {
        return hasBeenSplit;
    }

    public void setHasBeenSplit(boolean hasBeenSplit) {
        this.hasBeenSplit = hasBeenSplit;
    }
}
