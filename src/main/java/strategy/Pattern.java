package strategy;

import model.basic.Decision;
import model.basic.Value;
import strategy.Pair;

import java.util.HashMap;

public record Pattern(HashMap<Pair<Integer, Value>, Pair<Decision,Integer>> normal,
                      HashMap<Pair<Integer, Value>, Pair<Decision,Integer>> ace,
                      HashMap<Pair<Value, Value>, Pair<Decision,Integer>> pair) {
}
