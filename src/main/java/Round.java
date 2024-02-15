import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Round {
    private int playerCount;
    private final Hand dealerHand = new Hand();
    private final List<Hand> playerHands = new ArrayList<>(playerCount);
    private final Deck deck = new Deck(6);
}
