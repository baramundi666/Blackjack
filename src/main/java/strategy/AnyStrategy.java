package strategy;

import model.Hand;
import model.basic.Decision;
import model.basic.Value;

public class AnyStrategy extends AbstractStrategy{
    public AnyStrategy(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Decision getDecision(CardCounter counter, Hand dealerHand, Hand playerHand) {
        //System.out.println(counter.getCurrentCount() + " " + counter.getRealCount());
        Decision decision;
        var playerCards = playerHand.getCards();
        var dealerCards = dealerHand.getCards();
        if(playerCards.size()==2 && playerCards.get(0).getValue()==playerCards.get(1).getValue()) {
            //System.out.println("pair" + playerHand.getPoints());
            var pair = pattern.pair().get(
                            new Pair<>(playerCards.get(0).getValue(), dealerCards.get(0).getValue()));

            decision = pair.a();
            var realCount = counter.getRealCount();
            if(pair.b()*realCount>0) { // both negative or both positive
                if(Math.abs(realCount)>=Math.abs(pair.b())) {
                    //System.out.println("pair "+ pair.b() + " " + realCount);
                    var pc = playerHand.getPoints();
                    if(pc == 18 || pc == 20 || pc == 10) {
                        decision = switch (pair.a()) {
                            case DOUBLE -> Decision.HIT;
                            case HIT -> Decision.DOUBLE;
                            case STAND -> Decision.SPLIT;
                            case SPLIT -> Decision.STAND;
                            default -> throw new
                                    IllegalStateException("Unexpected value: " + pair.a());
                        };
                    }
                    else {
                        decision = switch (pair.a()) {
                            case HIT -> Decision.SPLIT;
                            case SPLIT -> Decision.HIT;
                            default -> throw new
                                    IllegalStateException("Unexpected value: " + pair.a());
                        };
                    }
                }
            }
        }
        else if(playerHand.getAceCount()==1 && playerHand.getPoints()<22) {
            //System.out.println("ace" + playerHand.getPoints());
            var pair = pattern.ace().get(
                            new Pair<>(playerHand.getPoints()-11, dealerCards.get(0).getValue()));

            decision = pair.a();
            var realCount = counter.getRealCount();
            if(pair.b()*realCount>0) { // both negative or both positive
                if(Math.abs(realCount)>=Math.abs(pair.b())) {
                    //System.out.println("ace "+ pair.b() + " " + realCount);
                    decision = switch (pair.a()) {
                        case HIT,STAND -> Decision.DOUBLE;
                        case DOUBLE -> Decision.HIT;
                        default -> throw new
                                IllegalStateException("Unexpected value: " + pair.a());
                    };
                }
            }

            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                if(playerHand.getPoints()-11<7) decision = Decision.HIT;
                else decision = Decision.STAND;
            }
        }
        else {
            //System.out.println("normal" + playerHand.getPoints());
            var pair = pattern.normal().get(
                            new Pair<>(playerHand.getPoints(), dealerCards.get(0).getValue()));

            decision = pair.a();
            var realCount = counter.getRealCount();
            if(pair.b()*realCount>0) { // both negative or both positive
                if(Math.abs(realCount)>=Math.abs(pair.b())) {
                    //System.out.println("normal "+ pair.b() + " " + realCount);
                    var pc = playerHand.getPoints();
                    if(pc < 12) {
                        decision = switch (pair.a()) {
                            case DOUBLE -> Decision.HIT;
                            case HIT -> Decision.DOUBLE;
                            default -> throw new
                                    IllegalStateException("Unexpected value: " + pair.a());
                        };
                    }
                    else {
                        decision = switch (pair.a()) {
                            case HIT -> Decision.STAND;
                            case STAND -> Decision.HIT;
                            default -> throw new
                                    IllegalStateException("Unexpected value: " + pair.a());
                        };
                    }
                }
            }

            // not possible to double
            if(decision==Decision.DOUBLE && playerHand.getCards().size()>2) {
                decision = Decision.HIT;
            }
        }

        return decision;
    }
}
