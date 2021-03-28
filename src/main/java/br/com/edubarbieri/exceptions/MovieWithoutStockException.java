package br.com.edubarbieri.exceptions;

public class MovieWithoutStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public MovieWithoutStockException() {
		super();
	}

	public MovieWithoutStockException(String message) {
		super(message);
	}
	
}
