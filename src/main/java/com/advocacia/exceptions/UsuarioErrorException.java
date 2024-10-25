package com.advocacia.exceptions;

public class UsuarioErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioErrorException(String message) {
        super(message);
    }
	
}
