package com.advocacia.exceptions;

public class LivroErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LivroErrorException(String message) {
        super(message);
    }
	
}
