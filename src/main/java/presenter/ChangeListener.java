package presenter;

import model.basic.Card;
import model.basic.Hand;
import model.basic.Player;
import model.elementary.Answer;
import model.elementary.Decision;

import java.util.List;

public interface ChangeListener {
    void tableInitialized();
    void updatePlayerHand(Player player, Hand hand);
    void updateDealerHand(Hand hand);
    

    Answer askInsurance(Player player);

    Decision askPlayerDecision(Player player, Hand hand);

    void updateHandStatus(Hand hand);

    void splitHand(Hand hand, Card card);
}
