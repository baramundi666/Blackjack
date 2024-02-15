public class Player {
    private final Hand hand = new Hand();
    private double balance = 0;
    private Decision decision;

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
}
