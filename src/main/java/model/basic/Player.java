package model.basic;

import model.basic.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    private final UUID playerId = UUID.randomUUID();
    private final List<Hand> hands = new ArrayList<>();
    private double balance = 0;


    public UUID getPlayerId() {
        return playerId;
    }

    public void setBalance(double money) {
        balance = money;
    }

    public double getBalance() {
        return balance;
    }

    public List<Hand> getHands() {
        return new ArrayList<>(hands);
    }

    public void addHand(Hand hand) {
        hands.add(hand);
    }

    public void resetHands() {
        hands.clear();
    }
}
