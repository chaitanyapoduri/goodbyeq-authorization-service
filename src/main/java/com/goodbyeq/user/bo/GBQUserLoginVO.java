package com.goodbyeq.user.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class GBQUserLoginVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String loginID;
	private String loginIP;
	private String notes;
	private Timestamp loginTimestamp;
	private Timestamp logoutTimestamp;

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Timestamp getLoginTimestamp() {
		return loginTimestamp;
	}

	public void setLoginTimestamp(Timestamp loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

	public Timestamp getLogoutTimestamp() {
		return logoutTimestamp;
	}

	public void setLogoutTimestamp(Timestamp logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}

}
