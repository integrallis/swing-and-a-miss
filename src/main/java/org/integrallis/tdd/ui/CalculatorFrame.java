package org.integrallis.tdd.ui;

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
//import java.util.EnumSet;
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

import org.integrallis.tdd.logic.Calculator;
import org.integrallis.tdd.logic.CalculatorCommand;

/**
 * A basic Java Swing Calculator
 * Refactored from original code at http://www.java-swing-tutorial.com by Hemanth. B
 *
 * @author bsbodden
 *
 */
public class CalculatorFrame extends JFrame implements ActionListener {

	private Calculator calculator;

	// Constructor
	public CalculatorFrame(Calculator calculator) {
		this.calculator = calculator;
		initComponents();
		calculator.clearAll();

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
		if (e.getSource() == jmenuitemAbout) {
			JDialog dlgAbout = new AboutDialog(this,
					"About Java Swing Calculator", true);
			dlgAbout.setVisible(true);
		} else if (e.getSource() == jmenuitemExit) {
			System.exit(0);
		}

		CalculatorCommand command = NOOP;
		if (e.getSource() instanceof JButton) {
			for (Map.Entry<CalculatorCommand,JButton> commandAndButton : commandsAndbuttons.entrySet()) {
				if (commandAndButton.getValue() == e.getSource()) {
					command = commandAndButton.getKey();
				}
			}
		}
		
		calculator.processCommand(command);
		
		setDisplayString(calculator.getDisplayString());
	}

	void setDisplayString(String s) {
		display.setText(s);
	}

	String getDisplayString() {
		return display.getText();
	}

	double getNumberInDisplay() {
		String input = display.getText();
		return Double.parseDouble(input);
	}

	private JMenu jmenuFile, jmenuHelp;
	private JMenuItem jmenuitemExit, jmenuitemAbout;

	private JLabel display;
	private Map<CalculatorCommand,JButton> commandsAndbuttons;
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

		display = new JLabel("0");
		display.setHorizontalTextPosition(JLabel.RIGHT);
		display.setBackground(Color.WHITE);
		display.setOpaque(true);

		// Add components to frame
		getContentPane().add(display, BorderLayout.NORTH);

		commandsAndbuttons = new HashMap<CalculatorCommand,JButton>();

		JPanel jplButtons = new JPanel(); // container for Jbuttons
		
		// create all buttons based on available commands
		for (CalculatorCommand command : CalculatorCommand.asList()) {
			JButton button = new JButton(new CalculatorAction(command));
			commandsAndbuttons.put(command, button);
		}

		jplBackSpace = new JPanel();
		jplBackSpace.setLayout(new GridLayout(1, 1, 2, 2));

		jplBackSpace.add(commandsAndbuttons.get(BACKSPACE));

		jplControl = new JPanel();
		jplControl.setLayout(new GridLayout(1, 2, 2, 2));

		jplControl.add(commandsAndbuttons.get(CE));
		jplControl.add(commandsAndbuttons.get(C));

		// Setting all Numbered JButton's to Blue. The rest to Red
		for (CalculatorCommand command : CalculatorCommand.asList()) {
			JButton button = commandsAndbuttons.get(command);
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
		for (CalculatorCommand command : CalculatorCommand.range(SEVEN, NINE)) {
			jplButtons.add(commandsAndbuttons.get(command));
		}

		// add button / and sqrt
		jplButtons.add(commandsAndbuttons.get(DIVIDE));
		jplButtons.add(commandsAndbuttons.get(SQRT));

		// Second row
		for (CalculatorCommand command : CalculatorCommand.range(FOUR, SIX)) {
			jplButtons.add(commandsAndbuttons.get(command));
		}

		// add button * and x^2
		jplButtons.add(commandsAndbuttons.get(MULTIPLICATION));
		jplButtons.add(commandsAndbuttons.get(INVERSE));

		// Third row
		for (CalculatorCommand command : CalculatorCommand.range(ONE, THREE)) {
			jplButtons.add(commandsAndbuttons.get(command));
		}

		// adds button - and %
		jplButtons.add(commandsAndbuttons.get(MINUS));
		jplButtons.add(commandsAndbuttons.get(PERCENT));

		// Fourth Row
		// add 0, +/-, ., +, and =
		jplButtons.add(commandsAndbuttons.get(ZERO));
		jplButtons.add(commandsAndbuttons.get(SIGN_CHANGE));
		jplButtons.add(commandsAndbuttons.get(DECIMAL_POINT));
		jplButtons.add(commandsAndbuttons.get(PLUS));
		jplButtons.add(commandsAndbuttons.get(EQUALS));

		jplMaster.setLayout(new BorderLayout());
		jplMaster.add(jplBackSpace, BorderLayout.WEST);
		jplMaster.add(jplControl, BorderLayout.EAST);
		jplMaster.add(jplButtons, BorderLayout.SOUTH);

		// Add components to frame
		getContentPane().add(jplMaster, BorderLayout.SOUTH);
		requestFocus();

		// activate ActionListener
		for (JButton button : commandsAndbuttons.values()) {
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

