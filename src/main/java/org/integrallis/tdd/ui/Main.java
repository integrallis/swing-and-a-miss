package org.integrallis.tdd.ui;

public class Main {
	public static void main(String args[]) {
		CalculatorFrame frame = new CalculatorFrame();
		frame.setTitle("Java Swing Calculator");
		frame.setSize(241, 217);
		frame.pack();
		frame.setLocation(400, 250);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
