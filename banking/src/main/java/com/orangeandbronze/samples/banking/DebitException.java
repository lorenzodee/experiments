package com.orangeandbronze.samples.banking;

public class DebitException extends RuntimeException {

	private static final long serialVersionUID = 2953954078718494803L;

	public DebitException() {
		super();
	}

	public DebitException(String message, Throwable cause) {
		super(message, cause);
	}

	public DebitException(String message) {
		super(message);
	}

	public DebitException(Throwable cause) {
		super(cause);
	}

}
