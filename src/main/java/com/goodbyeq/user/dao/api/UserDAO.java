package com.goodbyeq.user.dao.api;

import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.db.bo.UserVO;

public interface UserDAO {

	/**
	 * API to add user
	 * @param userVO
	 * @throws GBQUserException
	 */
	public void addUser(final UserVO userVO) throws GBQUserException;
	
	/**
	 * API to update user profile
	 * @param userVO
	 * @throws GBQUserException
	 */
	public void updateUser(final UserVO userVO) throws GBQUserException;
	
	/**
	 * API to delete user
	 * @param userID
	 * @throws GBQUserException
	 */
	public void deleteUser(final String userID) throws GBQUserException;
	
	/**
	 * API to get user VO by email ID
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public UserVO getUserVOByEmailID(final String userID)throws GBQUserException;
	
	/**
	 * API to get user VO by phone number
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public UserVO getUserVOByPhoneNumber(final String userID)throws GBQUserException;
	
}
