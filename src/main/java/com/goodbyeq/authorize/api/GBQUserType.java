package com.goodbyeq.authorize.api;

public enum GBQUserType {

	USER("U"), ADMIN("A");

	private String value;

	GBQUserType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
