package model;

import model.basic.Suit;
import model.basic.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final int deckCount;
    private List<Card> cards;
    private int currentCardId = 0;

    public Deck(int deckCount) {
        this.deckCount = deckCount;
        this.cards = generateRandomDeck();
    }

    public Card getNextCard() {
        Card card;
        if (currentCardId<52*deckCount) {
            card = cards.get(currentCardId);
            currentCardId++;
        }
        else {
            cards = generateRandomDeck();
            card = cards.get(0);
            currentCardId = 1;
        }
        return card;
    }

    private List<Card> generateRandomDeck() {
        List<Card> randomDeck = new ArrayList<>();
        for(int i=0;i<deckCount;i++) {
            var deck = generateSingleDeck();
            Collections.shuffle(deck);
            randomDeck.addAll(deck);
        }
        return randomDeck;
    }

    private List<Card> generateSingleDeck() {
        List<Card> deck = new ArrayList<>();
        for (Value value : Value.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(value, suit));
            }
        }
        return deck;
    }
}
