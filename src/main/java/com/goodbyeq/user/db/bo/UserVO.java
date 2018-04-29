package com.goodbyeq.user.db.bo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GBQ_USER")
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String userID;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "GENDER", length = 1)
	private String gender;
	@Column(name = "EMAIL")
	private String emailID;
	@Column(name = "PASSWD")
	private String password;
	@Column(name = "DOB")
	private Timestamp dateOfBirth;
	@Column(name = "PHONE", length = 15)
	private String phoneNumber;
	@Column(name = "USER_TYPE")
	private String userType;
	@Column(name = "GEO_LATITUDE")
	private String geoLatitude;
	@Column(name = "GEO_LONGITUDE")
	private String geoLongitude;
	@Column(name = "SALT")
	private String userSalt;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "CITY")
	private String city;
	@Column(name = "STATE")
	private String state;
	@Column(name = "ZIPCODE", length = 15)
	private String zipcode;
	@Column(name = "NATIONAL_SECURITY_ID")
	private String nationalSecurityID;
	@Column(name = "ISEMAILVERFIED", length = 1)
	private String isEmailVerified;
	@Column(name = "ISPHONEVERIFIED", length = 1)
	private String isPhoneVerified;
	@Column(name = "USER_STATUS", length = 1)
	private String userStatus;
/*
	public String getUserSysGUID() {
		return userSysGUID;
	}

	public void setUserSysGUID(String userSysGUID) {
		this.userSysGUID = userSysGUID;
	}
*/
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGeoLatitude() {
		return geoLatitude;
	}

	public void setGeoLatitude(String geoLatitude) {
		this.geoLatitude = geoLatitude;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGeoLongitude() {
		return geoLongitude;
	}

	public void setGeoLongitude(String geoLongitude) {
		this.geoLongitude = geoLongitude;
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getNationalSecurityID() {
		return nationalSecurityID;
	}

	public void setNationalSecurityID(String nationalSecurityID) {
		this.nationalSecurityID = nationalSecurityID;
	}

	public String isEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(String isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public String isPhoneVerified() {
		return isPhoneVerified;
	}

	public void setIsPhoneVerified(String isPhoneVerified) {
		this.isPhoneVerified = isPhoneVerified;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
