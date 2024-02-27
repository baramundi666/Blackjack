package model.strategy;

import model.basic.Card;

public class CardCounter {
    private int currentCount = 0;
    private double realCount = 0;
    private final int deckSize;
    private int currentCardNumber = 0;

    public CardCounter(int deckSize) {
        this.deckSize = deckSize;
    }

    public void updateCurrentCardNumber() {
        currentCardNumber++;
        currentCardNumber%=(deckSize*52);
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public double getRealCount() {
        return realCount;
    }

    public void updateCount(Card card) {
        int points = card.getValue().getPoints();
        if(points<7) currentCount++;
        else if(points>9) currentCount--;
        realCount = currentCount/(deckSize - (double)currentCardNumber/52);
    }
}
