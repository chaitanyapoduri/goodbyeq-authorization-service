package com.goodbyeq.authorization.bo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GoodByeQToken implements Serializable {

	private static final long serialVersionUID = 1L;

	/** access token */
	@JsonProperty("access_token")
	private String accessToken;

	/** Expire in */
	@JsonProperty("expires_in")
	private Integer expiresIn;

	/** Token type */
	@JsonProperty("token_type")
	private String tokenType;

	public GoodByeQToken() {
	}

	public GoodByeQToken(String accessToken, Integer expiresIn, String tokenType) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	/**
	 * Get access token
	 * 
	 * @return access token
	 */
	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Set access token
	 * 
	 * @param accessToken
	 *            access token
	 */
	@JsonProperty("access_token")
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Get expires in
	 * 
	 * @return expires in
	 */
	@JsonProperty("expires_in")
	public Integer getExpiresIn() {
		return expiresIn;
	}

	/**
	 * Set expires In
	 * 
	 * @param expiresIn
	 *            expires in
	 */
	@JsonProperty("expires_in")
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	 * TOken type
	 * 
	 * @return token type
	 */
	@JsonProperty("token_type")
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * Set token type
	 * 
	 * @param tokenType
	 *            token type
	 */
	@JsonProperty("token_type")
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "GoodByeQToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", tokenType=" + tokenType
				+ "]";
	}

}
