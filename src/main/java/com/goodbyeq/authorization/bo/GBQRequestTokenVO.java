package com.goodbyeq.authorization.bo;

import java.io.Serializable;

public class GBQRequestTokenVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userID;
	private String userIDType;
	private String clientSecret;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserIDType() {
		return userIDType;
	}

	public void setUserIDType(String userIDType) {
		this.userIDType = userIDType;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
