package com.goodbyeq.authorization.framework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;

@Component("jwtSettings")
@Configuration
public class JwtSettings {

	@Value("#{new Integer('${gbq.security.jwt.tokenExpirationTime}')}")
	private Integer tokenExpirationTime;

	@Value("${gbq.security.jwt.tokenIssuer}")
	private String tokenIssuer;

	@Value("${gbq.security.jwt.tokenSigningKey}")
	private String tokenSigningKey;

	@Value("${gbq.security.jwt.tokenSigningAlgorithm}")
	private String tokenSigningAlgorithm;

	public Integer getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Integer tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

	public String getTokenIssuer() {
		return tokenIssuer;
	}

	public void setTokenIssuer(String tokenIssuer) {
		this.tokenIssuer = tokenIssuer;
	}

	public String getTokenSigningKey() {
		return tokenSigningKey;
	}

	public void setTokenSigningKey(String tokenSigningKey) {
		this.tokenSigningKey = tokenSigningKey;
	}

	public String getTokenSigningAlgorithm() {
		return tokenSigningAlgorithm;
	}

	public void setTokenSigningAlgorithm(String tokenSigningAlgorithm) {
		this.tokenSigningAlgorithm = tokenSigningAlgorithm;
	}

	public SignatureAlgorithm getSignatureAlgorithm(final String algorithm) {
		return SignatureAlgorithm.forName(algorithm);
	}

}
