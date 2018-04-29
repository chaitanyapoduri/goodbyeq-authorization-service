package com.goodbyeq.authorize.api;

public enum GBQUserRoles {

	USER("USER"), ADMIN("ADMIN");

	private String value;

	GBQUserRoles(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(final String value) {
		this.value = value;
	}
}
