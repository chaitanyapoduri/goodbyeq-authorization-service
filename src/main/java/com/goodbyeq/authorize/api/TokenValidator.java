package com.goodbyeq.authorize.api;

import com.goodbyeq.exception.GBQTokenException;

/**
 * Validates jwt token 
 *
 */
public interface TokenValidator {

	/**
	 * API to validate oauth token
	 * @param requestURI
	 * @param oAuth2TokenString
	 * @return
	 * @throws GBQTokenException
	 */
	public Boolean validateToken(final String requestURI, final String oAuth2TokenString) throws GBQTokenException;
}
