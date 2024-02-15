import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Round {
    private int playerCount;
    private final Player dealer = new Player();
    private final List<Player> players = new ArrayList<>(playerCount);
    private final Deck deck = new Deck(6);

    public Round(int playerCount) {
        this.playerCount = playerCount;
        for(int i=0;i<playerCount;i++) {
            players.add(new Player());
        }
    }

    public void play() throws IOException {
        for(Player player : players) {
            player.updateHand(deck.getNextCard());
        }
        dealer.updateHand(deck.getNextCard());
        for(Player player : players) {
            player.updateHand(deck.getNextCard());
        }
        for(Player player : players) {
            player.updateHand(deck.getNextCard());
        }
        for(Player player : players) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            Decision decision = switch(reader.readLine()) {
                case "hit" -> Decision.HIT;
                case "stay" -> Decision.STAY;
                case "double" -> Decision.DOUBLE;
                case "split" -> Decision.SPLIT;
                default -> throw new IllegalStateException("Unexpected value: " + reader.readLine());
            };
            player.updateDecision(decision);
        }
    }

}
