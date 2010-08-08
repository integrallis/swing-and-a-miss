package org.integrallis.tdd.logic;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.integrallis.tdd.logic.CalculatorCommand.*;

import java.util.EnumSet;
import java.util.List;

import org.junit.Test;

public class CalculatorCommandTest {
	
	@Test
    public void testAsList() {
    	List<CalculatorCommand> allCommands = CalculatorCommand.asList();
    	assertThat(allCommands.size(), is(24));
    }
	
	@Test
	public void testRange() {
		EnumSet<CalculatorCommand> range = CalculatorCommand.range(FIVE, SQRT);
		assertThat(range.size(), is(13));
		assertThat(range, hasItem(FIVE));
		assertThat(range, hasItem(SIX));
		assertThat(range, hasItem(SEVEN));
		assertThat(range, hasItem(EIGHT));
		assertThat(range, hasItem(NINE));
		assertThat(range, hasItem(SIGN_CHANGE));
		assertThat(range, hasItem(DECIMAL_POINT));
		assertThat(range, hasItem(EQUALS));
		assertThat(range, hasItem(DIVIDE));
		assertThat(range, hasItem(MULTIPLICATION));
		assertThat(range, hasItem(MINUS));
		assertThat(range, hasItem(PLUS));
		assertThat(range, hasItem(SQRT));
	}
	
	
}
