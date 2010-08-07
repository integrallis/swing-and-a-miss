package org.integrallis.tdd.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.integrallis.tdd.logic.CalculatorCommand;

public class CalculatorAction extends AbstractAction {
	private CalculatorCommand command;

	public CalculatorAction(CalculatorCommand command) {
		super(command.getLabel());
		this.command = command;
	}

	public void actionPerformed(ActionEvent e) {
        System.out.println("Action Performed for " + e.toString() + " for Command " + command);
	}

	private static final long serialVersionUID = 1L;
}
// static class MyAction extends AbstractAction {
// public MyAction(String name, Icon icon) {
// super(name, icon);
// }
//
// public void actionPerformed(ActionEvent e) {
// System.out.println("Selected: " + getValue(Action.NAME));
// }
// }