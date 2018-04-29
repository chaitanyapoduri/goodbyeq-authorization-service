package com.goodbyeq.authoriation.api.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.goodbyeq.authorization.framework.JwtSettings;
import com.goodbyeq.authorize.api.SecretService;
import com.goodbyeq.authorize.api.TokenValidator;
import com.goodbyeq.exception.GBQTokenException;
import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.exception.JwtTokenMissingException;
import com.goodbyeq.user.db.bo.UserVO;
import com.goodbyeq.user.service.api.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("tokenValidators")
public class JWTTokenValidator implements TokenValidator {

	@Autowired(required = true)
	private JwtSettings jwtSettings;

	@Autowired(required = true)
	private SecretService secretService;

	@Autowired(required = true)
	private UserService userService;

	public JwtSettings getJwtSettings() {
		return jwtSettings;
	}

	public void setJwtSettings(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	public SecretService getSecretService() {
		return secretService;
	}

	public void setSecretService(SecretService secretService) {
		this.secretService = secretService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Boolean validateToken(String requestURI, String oAuth2TokenString) throws GBQTokenException {
		Boolean isTokenValid = false;
		String header = oAuth2TokenString;
		try {
			if (header == null || !header.startsWith("Bearer ")) {
				throw new JwtTokenMissingException("No JWT token found in request headers");
			}
			String authToken = header.substring(7);
			SignatureAlgorithm algorithm = getJwtSettings()
					.getSignatureAlgorithm(getJwtSettings().getTokenSigningAlgorithm());
			// Validate signature
			Jws<Claims> claims = Jwts.parser().setSigningKey(getSecretService().getHS256SecretBytes())
					.parseClaimsJws(authToken);

			// Validate issuer
			if (!getJwtSettings().getTokenIssuer().equals(claims.getBody().getIssuer())) {
				throw new GBQTokenException("Token Issuer did not match");
			}

			// Validate expiration time
			Date expirationDate = claims.getBody().getExpiration();
			long nowMillis = System.currentTimeMillis();
			Timestamp currentTimestamp = new Timestamp(nowMillis);
			if (currentTimestamp.after(expirationDate)) {
				throw new GBQTokenException("Token expired");
			}

			// Validate user exists in system
			isTokenValid = this.validatePrincipalUser(claims.getBody().getSubject());

		} catch (final Exception e) {
			throw new GBQTokenException("Exception while validating token", e);
		}
		return isTokenValid;
	}

	private boolean validatePrincipalUser(final String principalID)
			throws JwtTokenMissingException, GBQTokenException, GBQUserException {
		String regex = "[0-9]+";
		UserVO vo = null;
		if (!StringUtils.hasText(principalID)) {
			throw new GBQTokenException("No JWT subject found in request headers");
		}
		if (principalID.matches(regex)) {
			vo = getUserService().getUserVOByPhoneNumber(principalID);
		} else {
			vo = getUserService().getUserVOByEmailID(principalID);
		}

		if (null == vo) {
			throw new GBQTokenException("User not found");
		}
		return true;
	}

}
