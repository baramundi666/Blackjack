package run;

import model.basic.Result;

public class Tracker implements Listener{
    private int win = 0;
    private int lose = 0;
    private int blackjack = 0;
    private int push = 0;
    public void notify(Data data) {
        switch(data.getResult()) {
            case WIN -> win++;
            case LOSE -> lose++;
            case PUSH -> push++;
            case BLACKJACK -> blackjack++;
        }
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public int getBlackjack() {
        return blackjack;
    }

    public int getPush() {
        return push;
    }
}
