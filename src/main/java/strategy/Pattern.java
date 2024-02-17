package strategy;

import model.basic.Decision;
import model.basic.Value;
import strategy.Pair;

import java.util.HashMap;

public record Pattern(HashMap<Pair<Integer, Value>, Decision> normal,
                      HashMap<Pair<Integer, Value>, Decision> ace,
                      HashMap<Pair<Value, Value>, Decision> pair) {
}
