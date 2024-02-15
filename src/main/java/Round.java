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

    public void play() throws IllegalStateException, IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

//        for(Player player : players) {
//            System.out.println("Player: " + player.getPlayerId());
//            System.out.print("Input bet amount: ");
//            var betAmount = Double.parseDouble(reader.readLine());
//            player.bet(betAmount);
//        }

        for(Player player : players) {
            player.updateHand(deck.getNextCard());
        }

        var dealerCard = deck.getNextCard();
        dealer.updateHand(dealerCard);
        System.out.println("Dealer: ");
        System.out.println("Hand: " + dealerCard.toString());

        for(Player player : players) {
            player.updateHand(deck.getNextCard());
        }

        int playersLeftCount  = playerCount;
        while(playersLeftCount>0) {
            playersLeftCount = 0;
            for (Player player : players) {
                if(player.getStatus()==Status.PLAYING) {
                    System.out.println("Player: " + player.getPlayerId());
                    var playerHand = player.getHand();
                    System.out.print("Hand: ");
                    for(Card card : playerHand.getCards()) {
                        System.out.print(card.toString() + " ");
                    }
                    System.out.println();
                    System.out.print("Input your decision: ");
                    Decision decision = switch (reader.readLine()) {
                        case "hit" -> Decision.HIT;
                        case "stay" -> Decision.STAY;
                        case "double" -> Decision.DOUBLE;
                        case "split" -> Decision.SPLIT;
                        default -> throw new IllegalStateException("Unexpected value: " + reader.readLine());
                    };
                    player.handleDecision(decision, deck);
                    playersLeftCount++;
                }
            }
        }

        while(dealer.getHand().getPoints()<17) {
            dealer.updateHand(deck.getNextCard());
        }
        System.out.println("Dealer: ");
        var dealerHand = dealer.getHand();
        for(Card card : dealerHand.getCards()) {
            System.out.print(card.toString() + " ");
        }
        System.out.println();
    }

}
