package com.goodbyeq.authorize.api;

import com.goodbyeq.exception.JceSecureHashException;

public interface SecureHashCommand {

	/**
	 * Hash the provided String parameter
	 * 
	 * @param secret (String) to hash
	 * @returns the hash
	 * @throws JceSecureHashException
	 * if something goes wrong while hashing
	 */
	public String hash(String secret) throws JceSecureHashException;

}
