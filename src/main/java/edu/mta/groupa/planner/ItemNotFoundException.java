package edu.mta.groupa.planner;

public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}