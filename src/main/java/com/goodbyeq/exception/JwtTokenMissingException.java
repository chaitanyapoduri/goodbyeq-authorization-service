package com.goodbyeq.exception;

public class JwtTokenMissingException  extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new JwtTokenMissingException.
	 */
	public JwtTokenMissingException() {
		super();
	}

	/**
	 * Creates a new JwtTokenMissingException that contains the given error
	 * message.
	 *
	 * @param message
	 *            String representation of the error message.
	 */
	public JwtTokenMissingException(final String message) {
		super(message);
	}

	/**
	 * Creates a new JwtTokenMissingException that wraps the original Throwable
	 * and uses its error message.
	 *
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public JwtTokenMissingException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new JwtTokenMissingException that wraps the original thrown
	 * exception and has the given error message.
	 *
	 * @param message
	 *            String representation of the error message.
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public JwtTokenMissingException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
