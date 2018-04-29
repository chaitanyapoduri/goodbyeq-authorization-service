package com.goodbyeq.rest.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;


public class RestOperationStatusVOX implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String SUCCESS_OPERATION = "SUCCESS";
	public static final String FAILURE_OPERATION = "FAILED";
	public static final String REST_DATA = "REST_RETURN_DATA";

	private String operation;
	// SUCCESS,FAILED
	private String status;

	/**
	 * data returned*
	 */
	private Map<String, Object> data;
	/**
	 * errors *
	 */
	private List<JsonErrorVOX> errors;

	/**
	 * warnings *
	 */
	private List<JsonErrorVOX> warnings;

	public List<JsonErrorVOX> getErrors() {
		return errors;
	}

	public void setErrors(final List<JsonErrorVOX> errors) {
		this.errors = errors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(final String operation) {
		this.operation = operation;
	}

	public void setData(final Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public List<JsonErrorVOX> getWarnings() {
		return warnings;
	}

	public void setWarnings(final List<JsonErrorVOX> warnings) {
		this.warnings = warnings;
	}

	@Override
	public String toString() {
		return BaseJsonHelper.getJsonString(this);
	}

	public void addError(String code, String errorMsg) {
		JsonErrorVOX error = new JsonErrorVOX(code, errorMsg);
		if (errors == null)
			errors = new ArrayList<JsonErrorVOX>();
		errors.add(error);

	}

	public void addWarning(String code, String warningMsg) {
		JsonErrorVOX error = new JsonErrorVOX(code, warningMsg);
		if (warnings == null)
			warnings = new ArrayList<JsonErrorVOX>();
		warnings.add(error);
	}

	public boolean hasErrors() {
		return errors != null && !errors.isEmpty();
	}

	public boolean hasWarnings() {
		return warnings != null && !warnings.isEmpty();
	}

	@JsonIgnore
	public String getErrorString() {
		if (hasErrors()) {
			StringBuilder b = new StringBuilder();
			for (JsonErrorVOX error : errors) {
				b.append(error.getCode()).append(":").append(error.getMessage()).append(";");
			}
			return b.toString();
		}
		return "";

	}

	@JsonIgnore
	public String getWarningString() {
		if (hasWarnings()) {
			StringBuilder b = new StringBuilder();
			for (JsonErrorVOX warning : warnings) {
				b.append(warning.getCode()).append(":").append(warning.getMessage()).append(";");
			}
			return b.toString();
		}
		return "";

	}

	public void addData(String key, Object value) {
		if (data == null)
			data = new HashMap<>();
		data.put(key, value);
	}

}
