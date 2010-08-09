package org.integrallis.tdd.logic;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.integrallis.tdd.logic.Calculator.Mode;

public enum CalculatorCommand {
    ZERO("0") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    ONE("1") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    TWO("2") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    THREE("3") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    FOUR("4") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    FIVE("5") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    SIX("6") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    SEVEN("7") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    EIGHT("8") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    NINE("9") { public void execute(Calculator calculator) { calculator.addDigitToDisplay(ordinal()); }},
    SIGN_CHANGE("+/-") { public void execute(Calculator calculator) { calculator.processSignChange(); }},
    DECIMAL_POINT(".") { public void execute(Calculator calculator) { calculator.addDecimalPoint(); }},
    EQUALS("=") { public void execute(Calculator calculator) { calculator.processEquals(); }},
    DIVIDE("/") { public void execute(Calculator calculator) { calculator.processOperator("/"); }},
    MULTIPLICATION("*") { public void execute(Calculator calculator) { calculator.processOperator("*"); }},
    MINUS("-") { public void execute(Calculator calculator) { calculator.processOperator("-"); }},
    PLUS("+") { public void execute(Calculator calculator) { calculator.processOperator("+"); }},
    SQRT("sqrt") { public void execute(Calculator calculator) {
		if (calculator.getDisplayMode() != Mode.ERROR) {
			try {
				if (calculator.getDisplay().indexOf("-") == 0) { 
					calculator.display("Invalid input for function!", Mode.ERROR, true, 0);
				} 
				double result = Math.sqrt(calculator.getNumberInDisplay());
				calculator.display(Double.toString(result), Mode.RESULT, true, result);
			}
			catch (Exception ex) {
				calculator.display("Invalid input for function!", Mode.ERROR, true, 0);
				calculator.setDisplayMode(Mode.ERROR);
			}
		}
    }},
    INVERSE("1/x") { public void execute(Calculator calculator) {
		if (calculator.getDisplayMode() != Mode.ERROR) {
			try {
				if (calculator.getNumberInDisplay() == 0) {
					calculator.display("Cannot divide by zero!", Mode.ERROR, true, 0);
				}

				double result = 1 / calculator.getNumberInDisplay();
				calculator.display(Double.toString(result), Mode.RESULT, true, result);
			}
			catch (Exception ex) {
				calculator.display("Cannot divide by zero!", Mode.ERROR, true, 0);
				calculator.setDisplayMode(Mode.ERROR);
			}
		}
    }},
    PERCENT("%") { public void execute(Calculator calculator) {
		if (calculator.getDisplayMode() != Mode.ERROR) {
			try {
				double result = calculator.getNumberInDisplay() / 100;
				calculator.display(Double.toString(result), Mode.RESULT, true, result);
			}
			catch (Exception ex) {
				calculator.display("Invalid input for function!", Mode.ERROR, true, 0);
				calculator.setDisplayMode(Mode.ERROR);
			}
		}
    }},
    BACKSPACE("Backspace") { public void execute(Calculator calculator) {
		if (calculator.getDisplayMode() != Mode.ERROR) {
			calculator.setDisplay(calculator.getDisplay().substring(0, calculator.getDisplay().length() - 1));
			if (calculator.getDisplay().length() < 1) {
				calculator.setDisplay("0");
			}
		}
    }},
    CE(" CE ") { public void execute(Calculator calculator) { calculator.clearExisting(); }},
    C("C") { public void execute(Calculator calculator) { calculator.clearAll(); }},
    NOOP("NOOP") { public void execute(Calculator calculator) {}};
    
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
	
	public abstract void execute(Calculator calculator);

}
