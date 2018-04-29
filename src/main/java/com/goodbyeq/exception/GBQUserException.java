package com.goodbyeq.exception;

public class GBQUserException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new GBQServiceException.
	 */
	public GBQUserException() {
		super();
	}

	/**
	 * Creates a new GBQServiceException that contains the given error
	 * message.
	 *
	 * @param message
	 *            String representation of the error message.
	 */
	public GBQUserException(final String message) {
		super(message);
	}

	/**
	 * Creates a new GBQServiceException that wraps the original Throwable
	 * and uses its error message.
	 *
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public GBQUserException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new GBQServiceException that wraps the original thrown
	 * exception and has the given error message.
	 *
	 * @param message
	 *            String representation of the error message.
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public GBQUserException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
