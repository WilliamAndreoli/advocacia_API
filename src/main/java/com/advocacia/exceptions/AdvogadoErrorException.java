package com.advocacia.exceptions;

public class AdvogadoErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AdvogadoErrorException(String message) {
        super(message);
    }
	
}
