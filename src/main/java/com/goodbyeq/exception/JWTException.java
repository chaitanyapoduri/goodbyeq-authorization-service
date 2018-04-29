package com.goodbyeq.exception;

public class JWTException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new JWTException.
	 */
	public JWTException() {
		super();
	}

	/**
	 * Creates a new JWTException that contains the given error
	 * message.
	 *
	 * @param message
	 *            String representation of the error message.
	 */
	public JWTException(final String message) {
		super(message);
	}

	/**
	 * Creates a new JWTException that wraps the original Throwable
	 * and uses its error message.
	 *
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public JWTException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new JWTException that wraps the original thrown
	 * exception and has the given error message.
	 *
	 * @param message
	 *            String representation of the error message.
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public JWTException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
