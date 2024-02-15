import java.util.LinkedList;
import java.util.List;

public class Hand {
    private int points = 0;
    private final List<Card> cards = new LinkedList<>();

    public Hand() {}

    public Hand(List<Card> cards, int points) {
        this.cards.addAll(cards);
        this.points = points;
    }

    public List<Card> getCards() {
        return new LinkedList<>(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
