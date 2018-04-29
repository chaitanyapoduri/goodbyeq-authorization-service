package com.goodbyeq.authorize.api;

public enum GBQAuthorities {

	READ("READ"), WRITE("WRITE"),UPDATE_USER("UPDATE_USER"),UPDATE_ADMIN("UPDATE_ADMIN"),DELETE("DELETE");

	private String value;

	GBQAuthorities(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
