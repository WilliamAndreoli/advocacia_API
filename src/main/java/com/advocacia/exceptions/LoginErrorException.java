package com.advocacia.exceptions;

public class LoginErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginErrorException(String message) {
        super(message);
    }
	
}
