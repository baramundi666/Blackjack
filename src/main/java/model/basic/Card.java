package model.basic;

import model.elementary.Suit;
import model.elementary.Value;

public class Card {
    private final Value value;
    private final Suit suit;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString() + suit.toString();
    }
}
