package org.integrallis.tdd.logic;

public class Calculator {
	private static final int MAX_INPUT_LENGTH = 20;
	private boolean clearOnNextDigit;
	private int displayMode;
	
	public boolean isClearOnNextDigit() {
		return clearOnNextDigit;
	}

	public void setClearOnNextDigit(boolean clearOnNextDigit) {
		this.clearOnNextDigit = clearOnNextDigit;
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public double getLastNumber() {
		return lastNumber;
	}

	public void setLastNumber(double lastNumber) {
		this.lastNumber = lastNumber;
	}

	public static final int INPUT_MODE = 0;
	public static final int RESULT_MODE = 1;
	public static final int ERROR_MODE = 2;
	
	private String displayString = "0";
	private String lastOperator;
	private double lastNumber;
	
	public void addDigitToDisplay(int digit) {
		if (clearOnNextDigit)
			setDisplayString("");

		String inputString = getDisplayString();

		if (inputString.indexOf("0") == 0) {
			inputString = inputString.substring(1);
		}

		if ((!inputString.equals("0") || digit > 0)
				&& inputString.length() < MAX_INPUT_LENGTH) {
			setDisplayString(inputString + digit);
		}

		displayMode = INPUT_MODE;
		clearOnNextDigit = false;
	}
	
	public void addDecimalPoint() {
		displayMode = INPUT_MODE;

		if (clearOnNextDigit)
			setDisplayString("");

		String inputString = getDisplayString();

		// If the input string already contains a decimal point, don't
		// do anything to it.
		if (inputString.indexOf(".") < 0)
			setDisplayString(new String(inputString + "."));
	}

	public void processSignChange() {
		if (displayMode == INPUT_MODE) {
			String input = getDisplayString();

			if (input.length() > 0 && !input.equals("0")) {
				if (input.indexOf("-") == 0)
					setDisplayString(input.substring(1));

				else
					setDisplayString("-" + input);
			}

		}

		else if (displayMode == RESULT_MODE) {
			double numberInDisplay = getNumberInDisplay();

			if (numberInDisplay != 0)
			    display(Double.toString(-numberInDisplay), RESULT_MODE, true, -numberInDisplay);
		}
	}
	
	public void processOperator(String op) {
		if (displayMode != ERROR_MODE) {
			double numberInDisplay = getNumberInDisplay();

			if (!lastOperator.equals("0")) {
				double result = processLastOperator();
				display(Double.toString(result), RESULT_MODE, true, result);
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
		if (displayMode != ERROR_MODE) {
			try {
				double result = processLastOperator();
				display(Double.toString(result), RESULT_MODE, true, result);
			}
			catch (ArithmeticException ae) {
				if (ae.getMessage().equals("/ by zero")) 
					display("Cannot divide by zero!", ERROR_MODE, true, 0);
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
	
	public void display(String displayString, int displayMode, boolean clearOnNextDigit, double lastNumber) {
		setDisplayString(displayString);
		this.lastNumber = lastNumber;
		this.displayMode = displayMode;
		this.clearOnNextDigit = clearOnNextDigit;
	}

	public void clearAll() {
		setDisplayString("0");
		lastOperator = "0";
		lastNumber = 0;
		displayMode = INPUT_MODE;
		clearOnNextDigit = true;
	}

	public void clearExisting() {
		setDisplayString("0");
		clearOnNextDigit = true;
		displayMode = INPUT_MODE;
	}


	public String getDisplayString() {
		return displayString;
	}

	public void setDisplayString(String string) {
		displayString = string;
		
	}
	
	public double getNumberInDisplay() {
		return Double.parseDouble(displayString);
	}
	
	public void processCommand(CalculatorCommand command) {
		switch (command) {
		case ZERO:
		case ONE:
		case TWO:
		case THREE:
		case FOUR:
		case FIVE:
		case SIX:
		case SEVEN:
		case EIGHT:
		case NINE:
			addDigitToDisplay(command.ordinal());
			break;

		case SIGN_CHANGE: // +/-
			processSignChange();
			break;

		case DECIMAL_POINT: // decimal point
			addDecimalPoint();
			break;

		case EQUALS: // =
			processEquals();
			break;

		case DIVIDE: // divide
			processOperator("/");
			break;

		case MULTIPLICATION: // *
			processOperator("*");
			break;

		case MINUS: // -
			processOperator("-");
			break;

		case PLUS: // +
			processOperator("+");
			break;

		case SQRT: // sqrt
			if (displayMode != ERROR_MODE) {
				try {
					if (getDisplayString().indexOf("-") == 0)
					   display("Invalid input for function!", ERROR_MODE, true, 0);
					double result = Math.sqrt(getNumberInDisplay());
					display(Double.toString(result), RESULT_MODE, true, result);
				}

				catch (Exception ex) {
					display("Invalid input for function!", ERROR_MODE, true, 0);
					displayMode = ERROR_MODE;
				}
			}
			break;

		case INVERSE: // 1/x
			if (displayMode != ERROR_MODE) {
				try {
					if (getNumberInDisplay() == 0)
					    display("Cannot divide by zero!", ERROR_MODE, true, 0);

					double result = 1 / getNumberInDisplay();
					display(Double.toString(result), RESULT_MODE, true, result);
				}

				catch (Exception ex) {
					display("Cannot divide by zero!", ERROR_MODE, true, 0);
					displayMode = ERROR_MODE;
				}
			}
			break;

		case PERCENT: // %
			if (displayMode != ERROR_MODE) {
				try {
					double result = getNumberInDisplay() / 100;
					display(Double.toString(result), RESULT_MODE, true, result);
				}

				catch (Exception ex) {
					display("Invalid input for function!", ERROR_MODE, true, 0);
					displayMode = ERROR_MODE;
				}
			}
			break;

		case BACKSPACE: // backspace
			if (displayMode != ERROR_MODE) {
				setDisplayString(getDisplayString().substring(0,
						getDisplayString().length() - 1));

				if (getDisplayString().length() < 1)
					setDisplayString("0");
			}
			break;

		case CE: // CE
			clearExisting();
			break;

		case C: // C
			clearAll();
			break;
		}		
	}   
}
