package com.goodbyeq.user.dao.api;

import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.db.bo.UserLoginVO;

public interface UserLoginDAO {

	/**
	 * API to add user login  information
	 * @param userLoginSessionVO
	 * @throws GBQUserException
	 */
	public void addUserLoginInfo(final UserLoginVO userLoginVO) throws GBQUserException;
}
