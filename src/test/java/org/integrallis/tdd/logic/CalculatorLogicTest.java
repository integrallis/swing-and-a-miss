package org.integrallis.tdd.logic;

import static org.hamcrest.Matchers.is;
import static org.integrallis.tdd.logic.CalculatorCommand.ONE;
import static org.integrallis.tdd.logic.CalculatorCommand.THREE;
import static org.integrallis.tdd.logic.CalculatorCommand.TWO;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import org.mockito.ArgumentMatcher;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;


public class CalculatorLogicTest {
	@Test
	public void uponCreationThatCalculatorShouldDisplayZero() {
		Calculator calculator = new Calculator();
		assertThat(calculator.getDisplay(), is("0"));
	}
	
	@Test
	public void whenACalculatorDisplayChangesListenersShouldBeNotified() {
		Calculator calculator = new Calculator();
		PropertyChangeListener listener = mock(PropertyChangeListener.class);
		calculator.addPropertyChangeListener(listener);
		
		ONE.execute(calculator);
		TWO.execute(calculator);
		THREE.execute(calculator);
		
		verify(listener, times(3)).propertyChange(any(PropertyChangeEvent.class)); 
	}
	
	@Test
	public void whenACalculatorDisplayChangesListenersShouldBeNotifiedWithTheCorrectArguments() {
		Calculator calculator = new Calculator();
		PropertyChangeListener listener = mock(PropertyChangeListener.class);
		calculator.addPropertyChangeListener(listener);
		
		ONE.execute(calculator);
		TWO.execute(calculator);
		THREE.execute(calculator);

		verify(listener).propertyChange(argThat(new PropertyChangeEventArgumentMatcher(calculator, "display", "0", "1")));
		verify(listener).propertyChange(argThat(new PropertyChangeEventArgumentMatcher(calculator, "display", "1", "12")));
		verify(listener).propertyChange(argThat(new PropertyChangeEventArgumentMatcher(calculator, "display", "12", "123")));
		
	}
	
	private class PropertyChangeEventArgumentMatcher extends ArgumentMatcher<PropertyChangeEvent> {
		private Object source;
        private String propertyName;
        private Object oldValue;
        private Object newValue;
        
		public PropertyChangeEventArgumentMatcher(Object source, String propertyName, Object oldValue, Object newValue) {
			this.source = source;
			this.propertyName = propertyName;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}
		
		@Override
		public boolean matches(Object argument) {
			PropertyChangeEvent pce = (PropertyChangeEvent)argument;
			return new EqualsBuilder()
			  .append(source, pce.getSource())
			  .append(propertyName, pce.getPropertyName())
			  .append(oldValue, pce.getOldValue())
			  .append(newValue, pce.getNewValue())
			  .isEquals();
		}
	}
}
