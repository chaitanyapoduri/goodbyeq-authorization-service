package com.goodbyeq.authorize.api;

import com.goodbyeq.authorization.bo.GoodByeQToken;

public interface OAuth2TokenCreator {

	public GoodByeQToken generateNewToken(final String jwtToken) throws Exception;
}
