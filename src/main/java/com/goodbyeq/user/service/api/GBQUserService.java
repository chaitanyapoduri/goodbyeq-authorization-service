package com.goodbyeq.user.service.api;

import com.goodbyeq.authorization.bo.GBQRequestTokenVO;
import com.goodbyeq.authorization.bo.GoodByeQToken;
import com.goodbyeq.exception.GBQServiceException;
import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.bo.GBQUserVO;

public interface GBQUserService {

	/**
	 * API to create GBQ user
	 * @param userID
	 * @param userIDType
	 * @throws GBQServiceException
	 */
	public String createGBQUserToken(final GBQRequestTokenVO userRequestToken) throws GBQServiceException;

	/**
	 * API to load user by phone number
	 * @param phoneNumber
	 * @return GBQUserVO
	 * @throws GBQServiceException
	 */
	public GBQUserVO loadUserByPhoneNumber(final String phoneNumber) throws GBQServiceException;

	/**
	 * API to load user by email address
	 * @param emailID
	 * @return GBQUserVO
	 * @throws GBQServiceException
	 */
	public GBQUserVO loadUserByEmailAddress(final String emailID) throws GBQServiceException;

	/**
	 * API to generate user token
	 * @param userRequestToken
	 * @return
	 * @throws GBQUserException
	 */
	public GoodByeQToken getSingleUseToken(GBQRequestTokenVO userRequestToken) throws GBQUserException;

}
