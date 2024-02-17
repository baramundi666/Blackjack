import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var Round = new Round(3);
        try {
            Round.play();
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }
}
