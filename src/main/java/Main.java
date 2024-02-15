import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var Round = new Round(1);
        try {
            Round.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
