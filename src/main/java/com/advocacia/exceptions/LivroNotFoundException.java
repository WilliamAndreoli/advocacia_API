package com.advocacia.exceptions;

public class LivroNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public LivroNotFoundException(String message) {
        super(message);
    }
}
