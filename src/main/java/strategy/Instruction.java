package strategy;

import model.basic.Decision;

public class Instruction {
    private final Decision firstOption;
    private Integer value;
    private Decision secondOption;
    private final boolean IsInstructionNeeded;
    private boolean isInequalityNatural;

    public Instruction(Decision firstOption, Integer value, Decision secondOption, boolean isInequalityNatural) {
        this.firstOption = firstOption;
        this.value = value;
        this.secondOption = secondOption;
        this.IsInstructionNeeded = true;
        this.isInequalityNatural = isInequalityNatural;
    }

    public Instruction(Decision firstOption) {
        this.firstOption = firstOption;
        this.IsInstructionNeeded = false;
    }

    public Decision getInstruction(Double count) {
        if(!IsInstructionNeeded) return firstOption;
        if(isInequalityNatural) {
            if(count>=value) return secondOption;
        }
        else{
            if(count<value) return secondOption;
        }
        return firstOption;
    }

}
