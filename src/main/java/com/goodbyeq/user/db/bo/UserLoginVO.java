package com.goodbyeq.user.db.bo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GBQ_USER_LOGIN")
public class UserLoginVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@Column(name = "ID", updatable = false, nullable = false)
	private String userLoginSysGUID;*/
	@Id
	@Column(name = "LOGIN_ID")
	private String loginID;
	@Column(name = "LOGIN_IP", length = 20)
	private String loginIP;
	@Column(name = "NOTES")
	private String notes;
	@Column(name = "LOGIN_TS")
	private Timestamp loginTimestamp;
	@Column(name = "LOGOUT_TS")
	private Timestamp logoutTimestamp;

	/*public String getUserLoginSysGUID() {
		return userLoginSysGUID;
	}

	public void setUserLoginSysGUID(String userLoginSysGUID) {
		this.userLoginSysGUID = userLoginSysGUID;
	}*/

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
