import java.util.UUID;

public class Player {
    private final UUID playerId = UUID.randomUUID();
    private final Hand hand = new Hand();
    private double balance = 0;
    private Decision decision = Decision.NEUTRAL;
    private Status status = Status.PLAYING;
    private double bet;

    public UUID getPlayerId() {
        return playerId;
    }

    public void updateDecision(Decision decision) {
        this.decision = decision;
    }

    public Decision getDecision() {
        return decision;
    }

    public void updateBalance(double money) {
        balance = money;
    }

    public double getBalance() {
        return balance;
    }

    public void updateHand(Card card) {
        hand.addCard(card);
        hand.setPoints(hand.getPoints()+card.getValue().getPoints());
    }

    public Hand getHand() {
        return new Hand(hand.getCards(), hand.getPoints());
    }

    public void bet(double money) {
        bet = money;
        balance -= money;
    }

    public void handleDecision(Decision decision, Deck deck) {
        updateDecision(decision);
        switch(decision) {
            case HIT -> {
                updateHand(deck.getNextCard());
                if (hand.getPoints()>21) {
                    setStatus(Status.DEAD);
                }
            }
            case STAY -> setStatus(Status.STAYED);
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
