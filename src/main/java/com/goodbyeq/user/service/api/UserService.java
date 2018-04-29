package com.goodbyeq.user.service.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.db.bo.UserVO;

public interface UserService {

	/**
	 * API to add user
	 * 
	 * @param userVO
	 * @throws GBQUserException
	 */
	public String addUser(final UserVO userVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws GBQUserException;

	/**
	 * API to update user profile
	 * 
	 * @param userVO
	 * @throws GBQUserException
	 */
	public void updateUser(final UserVO userVO) throws GBQUserException;

	/**
	 * API to delete user
	 * 
	 * @param userID
	 * @throws GBQUserException
	 */
	public void deleteUser(final String userID) throws GBQUserException;

	/**
	 * API to get user information
	 * 
	 * @param userID
	 * @param userIDType
	 * @return
	 * @throws GBQUserException
	 */
	public UserVO getUser(final String userID, final String userIDType) throws GBQUserException;

	/**
	 * API to get user VO by email ID
	 * 
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public UserVO getUserVOByEmailID(final String userID) throws GBQUserException;

	/**
	 * API to get user VO by phone number
	 * 
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public UserVO getUserVOByPhoneNumber(final String userID) throws GBQUserException;

	/**
	 * API to get user VO by phone number
	 * 
	 * @param userVO
	 * @return
	 * @throws GBQUserException
	 */
	public Boolean checkUserLogon(final UserVO userVO) throws GBQUserException;

	/**
	 * API to send verification code to email id
	 * @param emailID
	 * @return
	 * @throws GBQUserException
	 */
	public Cookie sendVerificationCodeToEmail(final String emailID, ModelMap model) throws GBQUserException;

	/**
	 * API to send verification code to email id
	 * @param emailID
	 * @param verifyCode
	 * @param model
	 * @return
	 * @throws GBQUserException
	 */
	public String verifyUser(String email, String verifyCode, ModelMap model, HttpServletRequest httpRequest) throws GBQUserException;

}