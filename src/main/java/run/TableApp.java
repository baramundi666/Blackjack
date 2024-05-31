package run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.round.TableRound;
import presenter.TablePresenter;

import static java.util.concurrent.Executors.newFixedThreadPool;


public class TableApp extends Application {

    private static TablePresenter table;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("windows/table.fxml"));
        BorderPane viewRoot = loader.load();
        table = loader.getController();
        configureStage(primaryStage,viewRoot);
        primaryStage.show();
    }

    private static void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack table app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}