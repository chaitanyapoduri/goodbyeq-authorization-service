package com.goodbyeq.authorization.framework;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.goodbyeq.authorize.api.SecretService;
import com.goodbyeq.user.bo.GBQUserVO;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtil {
	@Autowired
	private JwtSettings settings;

	@Autowired
	private SecretService secretService;

	public JwtSettings getSettings() {
		return settings;
	}

	public void setSettings(JwtSettings settings) {
		this.settings = settings;
	}

	public SecretService getSecretService() {
		return secretService;
	}

	public void setSecretService(SecretService secretService) {
		this.secretService = secretService;
	}

	/**
	 * API to get user access token by claims
	 * 
	 * @param username
	 * @param roles
	 * @return
	 */
	public String createAccessJwtToken(final GBQUserVO gbqUser) {
		if (!StringUtils.hasText(gbqUser.getUserID()))
			throw new IllegalArgumentException("Cannot create JWT Token without username");
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String primaryID = null;
		if (BooleanUtils.toBoolean(gbqUser.isEmailVerified())) {
			primaryID = gbqUser.getEmailID();
		} else {
			if (BooleanUtils.toBoolean(gbqUser.isPhoneVerified())) {
				primaryID = gbqUser.getPhoneNumber();
			} else {
				throw new IllegalArgumentException("Cannot create JWT Token without verified ID");
			}
		}
		JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(now).setSubject(primaryID)
				.setIssuer(getSettings().getTokenIssuer())
				.signWith(getSettings().getSignatureAlgorithm(getSettings().getTokenSigningAlgorithm()),
						getSecretService().getHS256SecretBytes());

		// if it has been specified, let's add the expiration
		long expMillis = nowMillis + getSettings().getTokenExpirationTime();
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);

		builder.claim("scope", gbqUser.getUserType());
		return builder.compact();
	}

	/**
	 * API to get user access token by claims
	 * 
	 * @param claims
	 * @return
	 */
	public String createAccessJwtToken(final Map<String, Object> claims) {
		JwtBuilder builder = Jwts.builder();

		claims.forEach((key, value) -> {
			switch (key) {
			case "iss":
				ensureType(key, value, String.class);
				builder.setIssuer((String) value);
				break;
			case "sub":
				ensureType(key, value, String.class);
				builder.setSubject((String) value);
				break;
			case "aud":
				ensureType(key, value, String.class);
				builder.setAudience((String) value);
				break;
			case "exp":
				ensureType(key, value, Long.class);
				builder.setExpiration(Date.from(Instant.ofEpochSecond(Long.parseLong(value.toString()))));
				break;
			case "nbf":
				ensureType(key, value, Long.class);
				builder.setNotBefore(Date.from(Instant.ofEpochSecond(Long.parseLong(value.toString()))));
				break;
			case "iat":
				ensureType(key, value, Long.class);
				builder.setIssuedAt(Date.from(Instant.ofEpochSecond(Long.parseLong(value.toString()))));
				break;
			case "jti":
				ensureType(key, value, String.class);
				builder.setId((String) value);
				break;
			default:
				builder.claim(key, value);
			}
		});

		builder.signWith(SignatureAlgorithm.HS256, secretService.getHS256SecretBytes());
		String jws = Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, getSecretService().getHS256SecretBytes()).compact();
		return jws;
	}

	private void ensureType(String registeredClaim, Object value, Class expectedType) {
		boolean isCorrectType = expectedType.isInstance(value)
				|| expectedType == Long.class && value instanceof Integer;

		if (!isCorrectType) {
			String msg = "Expected type: " + expectedType.getCanonicalName() + " for registered claim: '"
					+ registeredClaim + "', but got value: " + value + " of type: "
					+ value.getClass().getCanonicalName();
			throw new JwtException(msg);
		}
	}

}
