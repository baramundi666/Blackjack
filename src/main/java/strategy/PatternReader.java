package strategy;

import model.basic.Decision;
import model.basic.Value;

import java.util.*;

public class PatternReader {
    private final Map<String, Decision> inputTranslator = new HashMap<>();
    private final Map<Integer, Set<Value>> columnTranslator = new HashMap<>();
    public Pattern readPattern(String[][] array) {
        initialize();

        var normal = new HashMap<Pair<Integer, Value>, Instruction>();
        for(int i=0;i<16;i++) {
            for(int j=0;j<10;j++) {
                for(Value value : columnTranslator.get(j)) {
                    normal.put(new Pair<>(i+5, value),
                            new Instruction(inputTranslator.get(array[i][j].substring(0,1)),
                                    Integer.parseInt(array[i][j].substring(2)),
                                    inputTranslator.get(array[i][j].substring(1,2)),
                                    true));
                }
            }
        }
        for(Value value : Value.values())
            normal.put(new Pair<>(21, value), new Instruction(Decision.STAND));

        var ace = new HashMap<Pair<Integer, Value>, Instruction>();
        for(int i=16;i<24;i++) {
            for(int j=0;j<10;j++) {
                for(Value value : columnTranslator.get(j)) {
                    ace.put(new Pair<>(i-14, value),
                            new Instruction(inputTranslator.get(array[i][j].substring(0,1)),
                                    Integer.parseInt(array[i][j].substring(2)),
                                    inputTranslator.get(array[i][j].substring(1,2)),
                                    true));
                }
            }
        }
        for(Value value : Value.values())
            ace.put(new Pair<>(10, value), new Instruction(Decision.STAND));

        var pair = new HashMap<Pair<Value, Value>, Instruction>();
        // i = 24
        for(int j=0;j<10;j++) {
            for(Value value : columnTranslator.get(j)) {
                pair.put(new Pair<>(Value.ACE, value),
                        new Instruction(inputTranslator.get(array[24][j].substring(0, 1)),
                                Integer.parseInt(array[24][j].substring(2)),
                                inputTranslator.get(array[24][j].substring(1,2)),
                                true));
            }
        }

        for(int i=25;i<34;i++) {
            for(int j=0;j<10;j++) {
                for(Value value1 : columnTranslator.get(i-25)) {
                    for(Value value2 : columnTranslator.get(j)) {
                        pair.put(new Pair<>(value1, value2),
                                new Instruction(inputTranslator.get(array[i][j].substring(0,1)),
                                        Integer.parseInt(array[i][j].substring(2)),
                                        inputTranslator.get(array[i][j].substring(1,2)),
                                        true));
                    }
                }

            }
        }
        return new Pattern(normal, ace, pair);
    }

    private void initialize() {
        inputTranslator.put("H", Decision.HIT);
        inputTranslator.put("S", Decision.STAND);
        inputTranslator.put("D", Decision.DOUBLE);
        inputTranslator.put("P", Decision.SPLIT);
        inputTranslator.put("U", Decision.SURRENDER);
        columnTranslator.put(0, new HashSet<>(List.of(Value.TWO)));
        columnTranslator.put(1, new HashSet<>(List.of(Value.THREE)));
        columnTranslator.put(2, new HashSet<>(List.of(Value.FOUR)));
        columnTranslator.put(3, new HashSet<>(List.of(Value.FIVE)));
        columnTranslator.put(4, new HashSet<>(List.of(Value.SIX)));
        columnTranslator.put(5, new HashSet<>(List.of(Value.SEVEN)));
        columnTranslator.put(6, new HashSet<>(List.of(Value.EIGHT)));
        columnTranslator.put(7, new HashSet<>(List.of(Value.NINE)));
        columnTranslator.put(8, new HashSet<>(List.of(Value.TEN, Value.JACK, Value.QUEEN, Value.KING)));
        columnTranslator.put(9, new HashSet<>(List.of(Value.ACE)));
    }
}
