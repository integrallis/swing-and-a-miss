package org.integrallis.tdd.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.integrallis.tdd.logic.Calculator;
import org.integrallis.tdd.logic.CalculatorCommand;

public class CalculatorAction extends AbstractAction {
	private CalculatorCommand command;
	private Calculator calculator;

	public CalculatorAction(CalculatorCommand command, Calculator calculator) {
		super(command.getLabel());
		this.command = command;
		this.calculator = calculator;
	}

	public void actionPerformed(ActionEvent e) {
        command.execute(calculator);
	}

	private static final long serialVersionUID = 1L;
}