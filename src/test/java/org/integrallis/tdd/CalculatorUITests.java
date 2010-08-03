package org.integrallis.tdd;

import org.junit.Test;
import org.uispec4j.Button;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;


public class CalculatorUITests extends UISpecTestCase {

	public void setUp() {
        setAdapter(new MainClassAdapter(Calculator.class, new String[0]));
	}
	
	@Test
	public void testApplicationTitle() throws Exception {
		  Window window = getMainWindow();
		  System.out.println(window.getTitle());
		  assertEquals(window.getTitle(), "Java Swing Calculator");
	}
	
	@Test
	public void testPressingADigit() throws Exception {
		  // 1. Retrieve the components
		  Window window = getMainWindow();
		  Button numberOne = window.getButton("1");
		  Button numberTwo = window.getButton("2");
		  numberOne.click();
		  numberTwo.click();
		  System.out.println("The display shows " + window.getTextBox().getText());
		  assertTrue(window.getTextBox().textEquals("12"));
	}


}
