package edu.mta.groupa.planner;

public class EmailExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailExistsException() {
		super();
	}
	
	public EmailExistsException(String message) {
		super(message);
	}

	public EmailExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
