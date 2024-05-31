package presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

    private Decision currentDecision = Decision.NONE;

    @Override
    public void tableInitialized(int playerCount) {
        Platform.runLater(() -> {
            drawTable(playerCount);
        });
    }

    public void drawTable(int playerCount) {
        double start;

//        mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
//        mapGrid.getRowConstraints().add(new RowConstraints(cellSize));
//        Label axis = new Label("y\\x");
//        axis.setTextFill(Paint.valueOf("black"));
//        mapGrid.add(axis, 0, 0);
//        GridPane.setHalignment(axis, HPos.CENTER);
//
//        for (int i = 0; i < height; i++) {
//            mapGrid.getRowConstraints().add(new RowConstraints(cellSize));
//            Label label = new Label(String.valueOf(i));
//            mapGrid.add(label, 0, i + 1);
//            GridPane.setHalignment(label, HPos.CENTER);
//        }
//        for (int i = 0; i < width; i++) {
//            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
//            var label = new Label(String.valueOf(i));
//            mapGrid.add(label, i + 1, 0);
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
        return null;
    }

    @Override
    public void updateHandStatus(Hand hand) {

    }

    @Override
    public void splitHand(Hand hand, Card card) {

    }

    public void onLaunchClicked() {
        var deck = new Deck(6);
        var playerCount = 2;
        tableInitialized(playerCount);
        var round = new TableRound(playerCount, deck, this);
        try {
            while(true) {
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





