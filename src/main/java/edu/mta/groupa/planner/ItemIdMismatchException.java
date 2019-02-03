package edu.mta.groupa.planner;

public class ItemIdMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemIdMismatchException() {
		super();
	}

	public ItemIdMismatchException(String message, Throwable cause) {
		super(message, cause);
	}
}