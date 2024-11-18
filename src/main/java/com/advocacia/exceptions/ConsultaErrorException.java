package com.advocacia.exceptions;

public class ConsultaErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConsultaErrorException(String message) {
        super(message);
    }
}
