package org.integrallis.tdd.ui;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

@RunWith(Parameterized.class)
public class CalculatorOperationsTest extends UISpecTestCase {
    private String[] operation;
    private String expected;
    
    private Window window;
    
	public CalculatorOperationsTest(String[] operation, String expected) {
		this.operation = operation;
		this.expected = expected;
	}

	@Before
	public void setUp() {
        setAdapter(new MainClassAdapter(Main.class, new String[0]));
        window = getMainWindow();
	}

	@Test
	public void testOperation() throws Exception {
		  // apply the operation
		  for (String button : Arrays.asList(operation)) {
			  window.getButton(button).click();
		  }
		  logger.info("The display shows " + window.getTextBox().getText());
		  assertTrue(window.getTextBox().textEquals(expected));
	}
	   
	@Parameters 
	public static List<Object[]> fooBar() {
		return Arrays.asList(new Object[][] {{ new String[]{"1", "+", "1", "="}, "2.0" },
				                             { new String[]{"1", "+", "2", "="}, "3.0" },
				                             { new String[]{"4", "+", "5", "="}, "9.0" },
				                             { new String[]{"1", "-", "5", "="}, "-4.0" },
				                             { new String[]{"1", "/", "4", "="}, "0.25" },
				                             { new String[]{"2", ".", "5", "*", "2", ".", "5", "="}, "6.25" },
				                             { new String[]{"5", "+/-", "+", "4", "="}, "-1.0" },
				                             { new String[]{"8", "5", ".", "2", "6", "1/x"}, "0.01172882946281961" },
				                             { new String[]{"1", "+", "2", "+", "3", "+", "4", "+", "5", "="}, "15.0" },
				                             { new String[]{"8", "5", "%"}, "0.85" },
				                             { new String[]{"9", "sqrt"}, "3.0" },
				                             { new String[]{"1"}, "1" },
				                             { new String[]{"1", "2", "3"}, "123" },
				                             { new String[]{"1", "2", "3", "Backspace"}, "12" },
				                             { new String[]{"1", "2", "3", "Backspace", "Backspace"}, "1" },
				                             { new String[]{"1", "2", "3", "+"}, "123" },
				                             { new String[]{"1", "+", "2", "+"}, "3.0" },
				                             { new String[]{"1", "+", "2", "+", "3", " CE ", "+", "7", "="}, "10.0" },
				                             { new String[]{"1", "2", "3", "+", "4"}, "4" },
				                             { new String[]{"1", "/", "0", "="}, "Cannot divide by zero!" },
				                             { new String[]{"1", "2", "3", "4", "5", "6", "C"}, "0" },
				                             { new String[]{"1", "2", "3", "+", "4", "5", "6"}, "456" }}
		);
	}
	
	private static Logger logger = Logger.getLogger(CalculatorOperationsTest.class);
}
