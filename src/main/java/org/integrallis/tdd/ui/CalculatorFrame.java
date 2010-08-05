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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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

	private JMenu jmenuFile, jmenuHelp;
	private JMenuItem jmenuitemExit, jmenuitemAbout;

	private JLabel jlbOutput;
	private JButton jbnButtons[];
	private JPanel jplMaster, jplBackSpace, jplControl;

	/*
	 * Font(String name, int style, int size) Creates a new Font from the
	 * specified name, style and point size.
	 */

	Font f12 = new Font("Times New Roman", 0, 12);
	Font f121 = new Font("Times New Roman", 1, 12);

	// Constructor
	public CalculatorFrame() {
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

		jbnButtons = new JButton[23];
		// GridLayout(int rows, int cols, int hgap, int vgap)

		JPanel jplButtons = new JPanel(); // container for Jbuttons

		// Create numeric Jbuttons
		for (int i = 0; i <= 9; i++) {
			// set each Jbutton label to the value of index
			jbnButtons[i] = new JButton(String.valueOf(i));
		}

		// Create operator Jbuttons
		jbnButtons[10] = new JButton("+/-");
		jbnButtons[11] = new JButton(".");
		jbnButtons[12] = new JButton("=");
		jbnButtons[13] = new JButton("/");
		jbnButtons[14] = new JButton("*");
		jbnButtons[15] = new JButton("-");
		jbnButtons[16] = new JButton("+");
		jbnButtons[17] = new JButton("sqrt");
		jbnButtons[18] = new JButton("1/x");
		jbnButtons[19] = new JButton("%");

		jplBackSpace = new JPanel();
		jplBackSpace.setLayout(new GridLayout(1, 1, 2, 2));

		jbnButtons[20] = new JButton("Backspace");
		jplBackSpace.add(jbnButtons[20]);

		jplControl = new JPanel();
		jplControl.setLayout(new GridLayout(1, 2, 2, 2));

		jbnButtons[21] = new JButton(" CE ");
		jbnButtons[22] = new JButton("C");

		jplControl.add(jbnButtons[21]);
		jplControl.add(jbnButtons[22]);

		// Setting all Numbered JButton's to Blue. The rest to Red
		for (int i = 0; i < jbnButtons.length; i++) {
			jbnButtons[i].setFont(f12);

			if (i < 10)
				jbnButtons[i].setForeground(Color.blue);

			else
				jbnButtons[i].setForeground(Color.red);
		}

		// Set panel layout manager for a 4 by 5 grid
		jplButtons.setLayout(new GridLayout(4, 5, 2, 2));

		// Add buttons to keypad panel starting at top left
		// First row
		for (int i = 7; i <= 9; i++) {
			jplButtons.add(jbnButtons[i]);
		}

		// add button / and sqrt
		jplButtons.add(jbnButtons[13]);
		jplButtons.add(jbnButtons[17]);

		// Second row
		for (int i = 4; i <= 6; i++) {
			jplButtons.add(jbnButtons[i]);
		}

		// add button * and x^2
		jplButtons.add(jbnButtons[14]);
		jplButtons.add(jbnButtons[18]);

		// Third row
		for (int i = 1; i <= 3; i++) {
			jplButtons.add(jbnButtons[i]);
		}

		// adds button - and %
		jplButtons.add(jbnButtons[15]);
		jplButtons.add(jbnButtons[19]);

		// Fourth Row
		// add 0, +/-, ., +, and =
		jplButtons.add(jbnButtons[0]);
		jplButtons.add(jbnButtons[10]);
		jplButtons.add(jbnButtons[11]);
		jplButtons.add(jbnButtons[16]);
		jplButtons.add(jbnButtons[12]);

		jplMaster.setLayout(new BorderLayout());
		jplMaster.add(jplBackSpace, BorderLayout.WEST);
		jplMaster.add(jplControl, BorderLayout.EAST);
		jplMaster.add(jplButtons, BorderLayout.SOUTH);

		// Add components to frame
		getContentPane().add(jplMaster, BorderLayout.SOUTH);
		requestFocus();

		// activate ActionListener
		for (int i = 0; i < jbnButtons.length; i++) {
			jbnButtons[i].addActionListener(this);
		}

		jmenuitemAbout.addActionListener(this);
		jmenuitemExit.addActionListener(this);

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

		// Search for the button pressed until end of array or key found
		for (int i = 0; i < jbnButtons.length; i++) {
			if (e.getSource() == jbnButtons[i]) {
				switch (i) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					addDigitToDisplay(i);
					break;

				case 10: // +/-
					processSignChange();
					break;

				case 11: // decimal point
					addDecimalPoint();
					break;

				case 12: // =
					processEquals();
					break;

				case 13: // divide
					processOperator("/");
					break;

				case 14: // *
					processOperator("*");
					break;

				case 15: // -
					processOperator("-");
					break;

				case 16: // +
					processOperator("+");
					break;

				case 17: // sqrt
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

				case 18: // 1/x
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

				case 19: // %
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

				case 20: // backspace
					if (displayMode != ERROR_MODE) {
						setDisplayString(getDisplayString().substring(0,
								getDisplayString().length() - 1));

						if (getDisplayString().length() < 1)
							setDisplayString("0");
					}
					break;

				case 21: // CE
					clearExisting();
					break;

				case 22: // C
					clearAll();
					break;
				}
			}
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

} // End of Swing Calculator Class.

