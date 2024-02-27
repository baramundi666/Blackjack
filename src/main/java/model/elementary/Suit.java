package model.elementary;

public enum Suit {
    SPADE, HEART, DIAMOND, CLUB;

    @Override
    public String toString() {
        return switch(this) {
            case SPADE -> "♠";
            case HEART -> "♥";
            case DIAMOND -> "♦";
            case CLUB -> "♣";
        };
    }
}
