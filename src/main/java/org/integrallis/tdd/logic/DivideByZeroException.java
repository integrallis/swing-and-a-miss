package org.integrallis.tdd.logic;

public class DivideByZeroException extends Exception {
	public DivideByZeroException() {
		super();
	}

	public DivideByZeroException(String s) {
		super(s);
	}
}
