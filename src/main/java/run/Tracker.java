package run;

import model.basic.Result;

public class Tracker implements Listener{
    private int win = 0;
    private int lose = 0;
    private int blackjack = 0;
    private int push = 0;
    private int surrender = 0;
    private int handCount = 0;
    public void notify(Data data) {
        switch(data.getResult()) {
            case WIN -> win++;
            case LOSE -> lose++;
            case PUSH -> push++;
            case BLACKJACK -> blackjack++;
            case SURRENDER -> surrender++;
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

    public int getSurrender() {return surrender;}

    public int getHandCount() {
        return handCount;
    }

    public void setHandCount(int handCount) {
        this.handCount = handCount;
    }
}
