package br.com.edubarbieri.services;

import br.com.edubarbieri.exceptions.CannotDivideByZeroException;

public class Calculadora {

	public int sum(int valueOne, int valueTwo) {
		return valueOne + valueTwo;
	}

	public int subtract(int valueOne, int valueTwo) {
		return valueOne - valueTwo;
	}

	public int divide(int valueOne, int valueTwo) throws CannotDivideByZeroException {
		if (valueTwo == 0) {
			throw new CannotDivideByZeroException();
		}
		return valueOne / valueTwo;
	}

}
