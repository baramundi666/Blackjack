import java.util.LinkedList;
import java.util.List;

public class Hand {
    private int points;
    private final List<Card> cards = new LinkedList<>();

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
