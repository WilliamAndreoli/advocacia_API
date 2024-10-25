package com.advocacia.exceptions;

public class JWTTokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JWTTokenException(String message) {
        super(message);
    }
	
}
