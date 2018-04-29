

package com.goodbyeq.rest.api;

import java.io.Serializable;

public class JsonErrorVOX implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String fieldName;

	public JsonErrorVOX() {
		//default
	}

	public JsonErrorVOX(final String code, final String errorMessage) {
		this.code = code;
		this.message = errorMessage;
	}

	public JsonErrorVOX(final String code, final String errorMessage,  final String fieldName) {
		this.code = code;
		this.fieldName = fieldName;
		this.message = errorMessage;
		
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
