package model.basic;

public enum Value {
    ACE, KING, QUEEN, JACK, TEN, NINE,
    EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;

    @Override
    public String toString() {
        return switch(this) {
            case ACE -> "A";
            case KING -> "K";
            case QUEEN -> "Q";
            case JACK -> "J";
            case TEN -> "10";
            case NINE -> "9";
            case EIGHT -> "8";
            case SEVEN -> "7";
            case SIX -> "6";
            case FIVE -> "5";
            case FOUR -> "4";
            case THREE -> "3";
            case TWO -> "2";
        };
    }

    public int getPoints() {
        return switch(this) {
            case ACE -> 11;
            case KING, QUEEN, JACK, TEN -> 10;
            case NINE -> 9;
            case EIGHT -> 8;
            case SEVEN -> 7;
            case SIX -> 6;
            case FIVE -> 5;
            case FOUR -> 4;
            case THREE -> 3;
            case TWO -> 2;
        };
    }
}
