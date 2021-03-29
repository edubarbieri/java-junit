package br.com.edubarbieri.services;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.edubarbieri.exceptions.CannotDivideByZeroException;

public class CalculadoraTest {

	private Calculadora calc;

	@Before
	public void setup() {
		calc = new Calculadora();
	}

	@Test
	public void shouldSumTwoValues() {
		// cenario
		int valueOne = 5;
		int valueTwo = 3;
		// acao
		int result = calc.sum(valueOne, valueTwo);

		// verificacao

		assertEquals(8, result);
	}

	@Test
	public void shouldSobtractTwoValues() {
		// cenario
		int valueOne = 15;
		int valueTwo = 3;
		// acao
		int result = calc.subtract(valueOne, valueTwo);

		// verificacao

		assertEquals(12, result);
	}
	
	@Test
	public void shouldDivideTwoValues() throws CannotDivideByZeroException {
		// cenario
		int valueOne = 15;
		int valueTwo = 3;
		// acao
		int result = calc.divide(valueOne, valueTwo);
		// verificacao
		assertEquals(5, result);
	}
	
	@Test(expected = CannotDivideByZeroException.class )
	public void shouldThrowExceptionWhenDivideByZero() throws CannotDivideByZeroException {
		// cenario
		int valueOne = 15;
		int valueTwo = 0;
		// acao
		calc.divide(valueOne, valueTwo);
	}

}
