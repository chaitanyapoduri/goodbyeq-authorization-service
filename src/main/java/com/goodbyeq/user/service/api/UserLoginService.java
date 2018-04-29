package com.goodbyeq.user.service.api;

import com.goodbyeq.exception.GBQServiceException;
import com.goodbyeq.user.bo.GBQUserLoginVO;

public interface UserLoginService {

	/**
	 * API to add user login information
	 * 
	 * @param userLoginSessionVO
	 * @throws GBQServiceException
	 */
	public void addUserLoginInfo(final GBQUserLoginVO userLoginSessionVO) throws GBQServiceException;
}
