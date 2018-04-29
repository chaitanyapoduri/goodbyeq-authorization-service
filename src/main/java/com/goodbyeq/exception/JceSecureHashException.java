package com.goodbyeq.exception;

public class JceSecureHashException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new JceSecureHashCheckException.
	 */
	public JceSecureHashException() {
		super();
	}

	/**
	 * Creates a new JceSecureHashCheckException that contains the given error
	 * message.
	 *
	 * @param message
	 *            String representation of the error message.
	 */
	public JceSecureHashException(final String message) {
		super(message);
	}

	/**
	 * Creates a new JceSecureHashCheckException that wraps the original Throwable
	 * and uses its error message.
	 *
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public JceSecureHashException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new JceSecureHashCheckException that wraps the original thrown
	 * exception and has the given error message.
	 *
	 * @param message
	 *            String representation of the error message.
	 * @param cause
	 *            The original Throwable object, if one was thrown.
	 */
	public JceSecureHashException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
