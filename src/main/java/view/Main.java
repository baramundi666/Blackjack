package view;

import model.Round;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var round = new Round(3);
        try {
            round.play();
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }
}
