package org.integrallis.tdd.logic;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum CalculatorCommand {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    SIGN_CHANGE("+/-"),
    DECIMAL_POINT("."),
    EQUALS("="),
    DIVIDE("/"),
    MULTIPLICATION("*"),
    MINUS("-"),
    PLUS("+"),
    SQRT("sqrt"),
    INVERSE("1/x"),
    PERCENT("%"),
    BACKSPACE("Backspace"),
    CE(" CE "),
    C("C"),
    NOOP("NOOP");
    
    private CalculatorCommand(String label) {
    	this.label = label;
    }
    
    @Override
    public String toString() {
    	return label;
    }
    
    private String label;

	public String getLabel() {
		return label;
	}
	
	public static List<CalculatorCommand> asList() {
		return Arrays.asList(values());
	}
	
	public static EnumSet<CalculatorCommand> range(CalculatorCommand begin, CalculatorCommand end) {
		return EnumSet.range(begin, end);
	}

}
