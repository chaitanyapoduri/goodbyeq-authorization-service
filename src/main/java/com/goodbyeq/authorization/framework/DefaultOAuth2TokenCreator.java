package com.goodbyeq.authorization.framework;

import org.springframework.stereotype.Service;

import com.goodbyeq.authorization.bo.GoodByeQToken;
import com.goodbyeq.authorize.api.OAuth2TokenCreator;

@Service("tokenCreator")
public class DefaultOAuth2TokenCreator implements OAuth2TokenCreator {

	private static final Integer EXPIRATION_TIME = Integer.valueOf(1800);
	private static final String AUTHORIZATION_BEARER = "Bearer";

	@Override
	public GoodByeQToken generateNewToken(final String jwtToken) throws Exception {
		GoodByeQToken token = new GoodByeQToken();
		token.setAccessToken(jwtToken);
		token.setExpiresIn(EXPIRATION_TIME);
		token.setTokenType(AUTHORIZATION_BEARER);
		return token;
	}

}
