package org.integrallis.tdd.ui;

/*
 Name: Hemanth. B
 Website: http://www.java-swing-tutorial.com

 Topic: A basic Java Swing Calculator

 Conventions Used in Source code
 ---------------------------------
 1. All JLabel components start with jlb*
 2. All JPanel components start with jpl*
 3. All JMenu components start with jmenu*
 4. All JMenuItem components start with jmenuItem*
 5. All JDialog components start with jdlg*
 6. All JButton components start with jbn*
 */

import static org.integrallis.tdd.logic.CalculatorCommand.BACKSPACE;
import static org.integrallis.tdd.logic.CalculatorCommand.C;
import static org.integrallis.tdd.logic.CalculatorCommand.CE;
import static org.integrallis.tdd.logic.CalculatorCommand.DECIMAL_POINT;
import static org.integrallis.tdd.logic.CalculatorCommand.DIVIDE;
import static org.integrallis.tdd.logic.CalculatorCommand.EQUALS;
import static org.integrallis.tdd.logic.CalculatorCommand.FOUR;
import static org.integrallis.tdd.logic.CalculatorCommand.INVERSE;
import static org.integrallis.tdd.logic.CalculatorCommand.MINUS;
import static org.integrallis.tdd.logic.CalculatorCommand.MULTIPLICATION;
import static org.integrallis.tdd.logic.CalculatorCommand.NINE;
import static org.integrallis.tdd.logic.CalculatorCommand.NOOP;
import static org.integrallis.tdd.logic.CalculatorCommand.ONE;
import static org.integrallis.tdd.logic.CalculatorCommand.PERCENT;
import static org.integrallis.tdd.logic.CalculatorCommand.PLUS;
import static org.integrallis.tdd.logic.CalculatorCommand.SEVEN;
import static org.integrallis.tdd.logic.CalculatorCommand.SIGN_CHANGE;
import static org.integrallis.tdd.logic.CalculatorCommand.SIX;
import static org.integrallis.tdd.logic.CalculatorCommand.SQRT;
import static org.integrallis.tdd.logic.CalculatorCommand.THREE;
import static org.integrallis.tdd.logic.CalculatorCommand.ZERO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.integrallis.tdd.logic.CalculatorCommand;

public class CalculatorFrame extends JFrame implements ActionListener {

	// Variables
	final int MAX_INPUT_LENGTH = 20;
	final int INPUT_MODE = 0;
	final int RESULT_MODE = 1;
	final int ERROR_MODE = 2;
	int displayMode;

	boolean clearOnNextDigit, percent;
	double lastNumber;
	String lastOperator;


	// Constructor
	public CalculatorFrame() {
		initComponents();
		clearAll();

		// add WindowListener for closing frame and ending program
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	} // End of Contructor Calculator

	// Perform action
	public void actionPerformed(ActionEvent e) {
		double result = 0;

		if (e.getSource() == jmenuitemAbout) {
			JDialog dlgAbout = new AboutDialog(this,
					"About Java Swing Calculator", true);
			dlgAbout.setVisible(true);
		} else if (e.getSource() == jmenuitemExit) {
			System.exit(0);
		}

		CalculatorCommand command = NOOP;
		if (e.getSource() instanceof JButton) {
			for (Map.Entry<CalculatorCommand,JButton> commandAndButton : jbnButtons.entrySet()) {
				if (commandAndButton.getValue() == e.getSource()) {
					command = commandAndButton.getKey();
				}
			}
		}
				
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

					result = Math.sqrt(getNumberInDisplay());
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

					result = 1 / getNumberInDisplay();
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
					result = getNumberInDisplay() / 100;
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

	void setDisplayString(String s) {
		jlbOutput.setText(s);
	}

	String getDisplayString() {
		return jlbOutput.getText();
	}

	void addDigitToDisplay(int digit) {
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

	void addDecimalPoint() {
		displayMode = INPUT_MODE;

		if (clearOnNextDigit)
			setDisplayString("");

		String inputString = getDisplayString();

		// If the input string already contains a decimal point, don't
		// do anything to it.
		if (inputString.indexOf(".") < 0)
			setDisplayString(new String(inputString + "."));
	}

	void processSignChange() {
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

	void clearAll() {
		setDisplayString("0");
		lastOperator = "0";
		lastNumber = 0;
		displayMode = INPUT_MODE;
		clearOnNextDigit = true;
	}

	void clearExisting() {
		setDisplayString("0");
		clearOnNextDigit = true;
		displayMode = INPUT_MODE;
	}

	double getNumberInDisplay() {
		String input = jlbOutput.getText();
		return Double.parseDouble(input);
	}

	void processOperator(String op) {
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

	void processEquals() {
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

	double processLastOperator() {
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
	
	void display(String displayString, int displayMode, boolean clearOnNextDigit, double lastNumber) {
		setDisplayString(displayString);
		this.lastNumber = lastNumber;
		this.displayMode = displayMode;
		this.clearOnNextDigit = clearOnNextDigit;
	}
	
	private JMenu jmenuFile, jmenuHelp;
	private JMenuItem jmenuitemExit, jmenuitemAbout;

	private JLabel jlbOutput;
	private Map<CalculatorCommand,JButton> jbnButtons;
	private JPanel jplMaster, jplBackSpace, jplControl;

	/*
	 * Font(String name, int style, int size) Creates a new Font from the
	 * specified name, style and point size.
	 */

	Font f12 = new Font("Times New Roman", 0, 12);
	Font f121 = new Font("Times New Roman", 1, 12);
	
	private void initComponents() {
		/*
		 * Set Up the JMenuBar. Have Provided All JMenu's with Mnemonics Have
		 * Provided some JMenuItem components with Keyboard Accelerators
		 */
		jmenuFile = new JMenu("File");
		jmenuFile.setFont(f121);
		jmenuFile.setMnemonic(KeyEvent.VK_F);

		jmenuitemExit = new JMenuItem("Exit");
		jmenuitemExit.setFont(f12);
		jmenuitemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		jmenuFile.add(jmenuitemExit);

		jmenuHelp = new JMenu("Help");
		jmenuHelp.setFont(f121);
		jmenuHelp.setMnemonic(KeyEvent.VK_H);

		jmenuitemAbout = new JMenuItem("About Calculator");
		jmenuitemAbout.setFont(f12);
		jmenuHelp.add(jmenuitemAbout);

		JMenuBar mb = new JMenuBar();
		mb.add(jmenuFile);
		mb.add(jmenuHelp);
		setJMenuBar(mb);

		// Set frame layout manager

		setBackground(Color.gray);

		jplMaster = new JPanel();

		jlbOutput = new JLabel("0");
		jlbOutput.setHorizontalTextPosition(JLabel.RIGHT);
		jlbOutput.setBackground(Color.WHITE);
		jlbOutput.setOpaque(true);

		// Add components to frame
		getContentPane().add(jlbOutput, BorderLayout.NORTH);

		jbnButtons = new HashMap<CalculatorCommand,JButton>();

		JPanel jplButtons = new JPanel(); // container for Jbuttons
		
		// create all buttons based on available commands
		for (CalculatorCommand command : CalculatorCommand.asList()) {
			JButton button = new JButton(new CalculatorAction(command));
			jbnButtons.put(command, button);
		}

		jplBackSpace = new JPanel();
		jplBackSpace.setLayout(new GridLayout(1, 1, 2, 2));

		jplBackSpace.add(jbnButtons.get(BACKSPACE));

		jplControl = new JPanel();
		jplControl.setLayout(new GridLayout(1, 2, 2, 2));

		jplControl.add(jbnButtons.get(CE));
		jplControl.add(jbnButtons.get(C));

		// Setting all Numbered JButton's to Blue. The rest to Red
		for (CalculatorCommand command : CalculatorCommand.asList()) {
			JButton button = jbnButtons.get(command);
			button.setFont(f12);
			if (command.ordinal() <= NINE.ordinal()) {
				button.setForeground(Color.blue);
			}
			else {
				button.setForeground(Color.red);
			}

		}

		// Set panel layout manager for a 4 by 5 grid
		jplButtons.setLayout(new GridLayout(4, 5, 2, 2));

		// Add buttons to keypad panel starting at top left
		// First row
		for (CalculatorCommand command : EnumSet.range(SEVEN, NINE)) {
			jplButtons.add(jbnButtons.get(command));
		}

		// add button / and sqrt
		jplButtons.add(jbnButtons.get(DIVIDE));
		jplButtons.add(jbnButtons.get(SQRT));

		// Second row
		for (CalculatorCommand command : EnumSet.range(FOUR, SIX)) {
			jplButtons.add(jbnButtons.get(command));
		}

		// add button * and x^2
		jplButtons.add(jbnButtons.get(MULTIPLICATION));
		jplButtons.add(jbnButtons.get(INVERSE));

		// Third row
		for (CalculatorCommand command : EnumSet.range(ONE, THREE)) {
			jplButtons.add(jbnButtons.get(command));
		}

		// adds button - and %
		jplButtons.add(jbnButtons.get(MINUS));
		jplButtons.add(jbnButtons.get(PERCENT));

		// Fourth Row
		// add 0, +/-, ., +, and =
		jplButtons.add(jbnButtons.get(ZERO));
		jplButtons.add(jbnButtons.get(SIGN_CHANGE));
		jplButtons.add(jbnButtons.get(DECIMAL_POINT));
		jplButtons.add(jbnButtons.get(PLUS));
		jplButtons.add(jbnButtons.get(EQUALS));

		jplMaster.setLayout(new BorderLayout());
		jplMaster.add(jplBackSpace, BorderLayout.WEST);
		jplMaster.add(jplControl, BorderLayout.EAST);
		jplMaster.add(jplButtons, BorderLayout.SOUTH);

		// Add components to frame
		getContentPane().add(jplMaster, BorderLayout.SOUTH);
		requestFocus();

		// activate ActionListener
		for (JButton button : jbnButtons.values()) {
			button.addActionListener(this);
		}

		jmenuitemAbout.addActionListener(this);
		jmenuitemExit.addActionListener(this);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

} // End of Swing Calculator Class.

