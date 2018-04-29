package com.goodbyeq.user.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class GBQUserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userID;
	private String lastName;
	private String firstName;
	private String gender;
	private String emailID;
	private String password;
	private Timestamp dateOfBirth;
	private String phoneNumber;
	private String userType;
	private String geoLatitude;
	private String geoLongitude;
	private String userSalt;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	private String nationalSecurityID;
	private String isEmailVerified;
	private String isPhoneVerified;
	private String userStatus;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

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

}
