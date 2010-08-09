package org.integrallis.tdd.ui;

import org.integrallis.tdd.logic.Calculator;

public final class Main {
	private Main() {};
	
	public static void main(final String args[]) {
		final CalculatorFrame frame = new CalculatorFrame(new Calculator());
		frame.setTitle("Java Swing Calculator");
		frame.setSize(241, 217);
		frame.pack();
		frame.setLocation(400, 250);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
