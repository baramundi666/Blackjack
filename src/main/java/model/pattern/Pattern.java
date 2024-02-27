package model.pattern;

import model.elementary.Value;
import model.elementary.Pair;

import java.util.HashMap;

public record Pattern(HashMap<Pair<Integer, Value>, Instruction> normal,
                      HashMap<Pair<Integer, Value>, Instruction> ace,
                      HashMap<Pair<Value, Value>, Instruction> pair,
                      HashMap<Integer, Double> insurance) {
}
