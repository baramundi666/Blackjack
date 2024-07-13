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

    public String getImageURL() {
        var value = switch(this.value) {
            case ACE -> "ace";
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case TEN -> "10";
            case JACK -> "jack";
            case QUEEN -> "queen";
            case KING -> "king";
        };
        var suit = switch(this.suit) {
            case CLUB -> "clubs";
            case DIAMOND -> "diamonds";
            case HEART -> "hearts";
            case SPADE -> "spades";
        };

        return value + "_of_" + suit + ".png";
    }

    @Override
    public String toString() {
        return value.toString() + suit.toString();
    }
}
