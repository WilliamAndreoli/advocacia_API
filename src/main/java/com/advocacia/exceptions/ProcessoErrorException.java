package com.advocacia.exceptions;

public class ProcessoErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProcessoErrorException(String message) {
        super(message);
    }
	
	
}
