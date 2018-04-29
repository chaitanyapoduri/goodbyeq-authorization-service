package com.goodbyeq.rest.api;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseRestController {

	/**
	 * Sets result and returns successful rest operation.
	 * 
	 * @param result
	 * @return
	 */
	protected RestOperationStatusVOX getSuccessRestOperationStatusVOX(final Object result) {
		return getSuccessRestOperationStatusVOX(null, result);
	}

	protected RestOperationStatusVOX getSuccessRestOperationStatusVOX(final String operation, final Object result) {
		final RestOperationStatusVOX restOperationStatusVOX = new RestOperationStatusVOX();
		restOperationStatusVOX.setOperation(operation);
		final Map<String, Object> data = new HashMap<String, Object>();
		data.put(RestOperationStatusVOX.REST_DATA, result);
		restOperationStatusVOX.setData(data);
		restOperationStatusVOX.setStatus(RestOperationStatusVOX.SUCCESS_OPERATION);
		return restOperationStatusVOX;
	}
}
