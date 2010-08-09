package org.integrallis.tdd.logic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

	public enum Mode { INPUT, RESULT, ERROR }
	
	private String display = "0";
	private String lastOperator;
	private double lastNumber;
	
	private static final int MAX_INPUT_LENGTH = 20;
	private boolean clearOnNextDigit;
	private Mode displayMode;
	
	public boolean isClearOnNextDigit() {
		return clearOnNextDigit;
	}

	public Mode getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(final Mode displayMode) {
		this.displayMode = displayMode;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	public double getLastNumber() {
		return lastNumber;
	}
	
	public void addDigitToDisplay(int digit) {
		if (clearOnNextDigit) {
			setDisplay("");
		}

		String inputString = getDisplay();

		if (inputString.indexOf("0") == 0) {
			inputString = inputString.substring(1);
		}

		if ((!inputString.equals("0") || digit > 0) && inputString.length() < MAX_INPUT_LENGTH) {
			setDisplay(inputString + digit);
		}

		displayMode = Mode.INPUT;
		clearOnNextDigit = false;
	}
	
	public void addDecimalPoint() {
		displayMode = Mode.INPUT;

		if (clearOnNextDigit) {
			setDisplay("");
		}

		String inputString = getDisplay();

		// If the input string already contains a decimal point, don't
		// do anything to it.
		if (inputString.indexOf(".") < 0) {
			setDisplay(new String(inputString + "."));
		}
	}

	public void processSignChange() {
		if (displayMode == Mode.INPUT) {
			String input = getDisplay();

			if (input.length() > 0 && !input.equals("0")) {
				if (input.indexOf("-") == 0) {
					setDisplay(input.substring(1));
				}
				else {
					setDisplay("-" + input);
				}
			}
		}

		else if (displayMode == Mode.RESULT) {
			double numberInDisplay = getNumberInDisplay();

			if (numberInDisplay != 0) {
			    display(Double.toString(-numberInDisplay), Mode.RESULT, true, -numberInDisplay);
			}
		}
	}
	
	public void processOperator(String op) {
		if (displayMode != Mode.ERROR) {
			double numberInDisplay = getNumberInDisplay();

			if (!lastOperator.equals("0")) {
				double result = processLastOperator();
				display(Double.toString(result), Mode.RESULT, true, result);
				lastNumber = result;
			}
			else {
				lastNumber = numberInDisplay;
			}

			clearOnNextDigit = true;
			lastOperator = op;
		}
	}

	public void processEquals() {
		if (displayMode != Mode.ERROR) {
			try {
				double result = processLastOperator();
				display(Double.toString(result), Mode.RESULT, true, result);
			}
			catch (ArithmeticException ae) {
				if (ae.getMessage().equals("/ by zero")) 
					display("Cannot divide by zero!", Mode.ERROR, true, 0);
			}

			lastOperator = "0";
		}
	}

	public double processLastOperator() {
		double result = 0;
		double numberInDisplay = getNumberInDisplay();

		if (lastOperator.equals("/")) {
			if (numberInDisplay == 0) throw (new ArithmeticException("/ by zero"));
			result = lastNumber / numberInDisplay;
		}

		if (lastOperator.equals("*"))
			result = lastNumber * numberInDisplay;

		if (lastOperator.equals("-"))
			result = lastNumber - numberInDisplay;

		if (lastOperator.equals("+"))
			result = lastNumber + numberInDisplay;

		return result;
	}
	
	public void display(String displayString, Mode displayMode, boolean clearOnNextDigit, double lastNumber) {
		setDisplay(displayString);
		this.lastNumber = lastNumber;
		this.displayMode = displayMode;
		this.clearOnNextDigit = clearOnNextDigit;
	}

	public void clearAll() {
		setDisplay("0");
		lastOperator = "0";
		lastNumber = 0;
		displayMode = Mode.INPUT;
		clearOnNextDigit = true;
	}

	public void clearExisting() {
		setDisplay("0");
		clearOnNextDigit = true;
		displayMode = Mode.INPUT;
	}


	public String getDisplay() {
		return display;
	}

	public void setDisplay(String string) {
		String oldValue = display;
		display = string;
		notifyListeners("display", oldValue, display);
	}
	
	public double getNumberInDisplay() {
		return Double.parseDouble(display);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeListerners.add(listener);
	}
	
	private void notifyListeners(String property, Object oldValue, Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, property, oldValue, newValue);
	    for (PropertyChangeListener listener : propertyChangeListerners) {
			listener.propertyChange(event);
		}	
	}
	
	private List<PropertyChangeListener> propertyChangeListerners = new ArrayList<PropertyChangeListener>();
}
