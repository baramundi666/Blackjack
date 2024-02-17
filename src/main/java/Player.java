import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    private final UUID playerId = UUID.randomUUID();
    private final List<Hand> hands = new ArrayList<>();
    private double balance = 0;

    private double bet;

    public UUID getPlayerId() {
        return playerId;
    }

    public void updateBalance(double money) {
        balance = money;
    }

    public double getBalance() {
        return balance;
    }

    public List<Hand> getHands() {
        return new ArrayList<>(hands);
    }

    public void bet(double money) {
        bet = money;
        balance -= money;
    }

    public void addHand(Hand hand) {
        hands.add(hand);
    }
}
