package com.advocacia.exceptions;

public class ClienteErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteErrorException(String message) {
        super(message);
    }
}
