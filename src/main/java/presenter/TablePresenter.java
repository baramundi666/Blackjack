package presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import model.basic.Card;
import model.basic.Deck;
import model.basic.Hand;
import model.basic.Player;
import model.elementary.Answer;
import model.elementary.Decision;
import model.round.TableRound;
import run.simulation.Simulation;

import java.io.IOException;
import java.util.List;

import static java.lang.Math.max;

public class TablePresenter implements ChangeListener {




    private final double width = 800;
    private final double height = 400;
    private final double offset = 50;

    @FXML
    public GridPane table;

    private Decision currentDecision = Decision.NONE;
    private TableRound round;

    @Override
    public void tableInitialized() {
        Platform.runLater(() -> {
            drawTable();
        });
    }

    public void drawTable() {
        double cellSize = 100;

        var players = round.getPlayers();
        for(Player player : players) {
            var hands = player.getHands();
            for(Hand hand : hands) {
                var cards = hand.getCards();
                for(Card card : cards) {
                    System.out.println("a");;
                    var image = new Image(card.getImageURL());
                    table.add(new ImageView(image), 0, 0);
                }
            }

        }


//        table.getColumnConstraints().add(new ColumnConstraints(cellSize));
//        table.getRowConstraints().add(new RowConstraints(cellSize));
//        Label axis = new Label("y\\x");
//        axis.setTextFill(Paint.valueOf("black"));
//        table.add(axis, 0, 0);
//        GridPane.setHalignment(axis, HPos.CENTER);
//
//        for (int i = 0; i < height; i++) {
//            table.getRowConstraints().add(new RowConstraints(cellSize));
//            Label label = new Label(String.valueOf(i));
//            table.add(label, 0, i + 1);
//            GridPane.setHalignment(label, HPos.CENTER);
//        }
//        for (int i = 0; i < width; i++) {
//            table.getColumnConstraints().add(new ColumnConstraints(cellSize));
//            var label = new Label(String.valueOf(i));
//            table.add(label, i + 1, 0);
//            GridPane.setHalignment(label, HPos.CENTER);
//        }
    }


    public void drawCard() {

    }


    @Override
    public void updatePlayerHand(Player player, Hand hand) {

    }

    @Override
    public void updateDealerHand(Hand hand) {

    }

    @Override
    public Answer askInsurance(Player player) {
        return null;
    }

    @Override
    public Decision askPlayerDecision(Player player, Hand hand) {
        int i = 0;
        while(currentDecision == Decision.NONE) {
            i++;
        };
        System.out.println(i);
        var dec = currentDecision;
        currentDecision = Decision.NONE;
        return dec;
    }

    @Override
    public void updateHandStatus(Hand hand) {

    }

    @Override
    public void splitHand(Hand hand, Card card) {

    }

    public void onLaunchClicked() {
        var deck = new Deck(6);
        var playerCount = 1;
        tableInitialized();
        try {
            while(true) {
                round = new TableRound(playerCount, deck, this);
                round.play();
            }
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    public void onHITClicked(ActionEvent actionEvent) {
       currentDecision = Decision.HIT;
    }

    public void onSTANDClicked(ActionEvent actionEvent) {
        currentDecision = Decision.STAND;
    }

    public void onDOUBLEClicked(ActionEvent actionEvent) {
        currentDecision = Decision.DOUBLE;
    }

    public void onSPLITClicked(ActionEvent actionEvent) {
        currentDecision = Decision.SPLIT;
    }
}





