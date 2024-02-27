package strategy;

import model.basic.Decision;
import model.basic.Value;

import java.util.HashMap;

public record Pattern(HashMap<Pair<Integer, Value>, Instruction> normal,
                      HashMap<Pair<Integer, Value>, Instruction> ace,
                      HashMap<Pair<Value, Value>, Instruction> pair,
                      HashMap<Integer, Double> insurance) {
}
