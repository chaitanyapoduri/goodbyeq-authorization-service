package com.goodbyeq.user.service.impl;

import java.security.SecureRandom;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.goodbyeq.authorization.framework.SecureHasher;
import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.dao.api.UserDAO;
import com.goodbyeq.user.db.bo.UserVO;
import com.goodbyeq.user.service.api.GBQUserService;
import com.goodbyeq.user.service.api.UserService;
import com.goodbyeq.util.AuthorizationServiceConstants;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private GBQUserService gbqUserService;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public GBQUserService getGbqUserService() {
		return gbqUserService;
	}

	public void setGbqUserService(GBQUserService gbqUserService) {
		this.gbqUserService = gbqUserService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String addUser(final UserVO userVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws GBQUserException {
		userVO.setUserID(UUID.randomUUID().toString());
		logger.debug("addUser()::Adding user with user id:- " + userVO.getUserID());
		getUserDAO().addUser(userVO);
		logger.debug("addUser()::Sending verification code to email id:- " + userVO.getEmailID() + " of "
				+ userVO.getFirstName() + " " + userVO.getLastName());
		Cookie cookie = sendVerificationCodeToEmail(userVO.getEmailID(), model);
		response.addCookie(cookie);
		return AuthorizationServiceConstants.VERIFY_CODE_PAGE;
	}

	@Override
	public UserVO getUser(final String userID, final String userIDType) throws GBQUserException {
		if (AuthorizationServiceConstants.USER_ID_TYPE_EMAIL.equals(userIDType)) {
			return getUserDAO().getUserVOByEmailID(userID);
		} else {
			return getUserDAO().getUserVOByPhoneNumber(userID);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(final UserVO userVO) throws GBQUserException {
		getUserDAO().updateUser(userVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(final String userID) throws GBQUserException {
		getUserDAO().deleteUser(userID);
	}

	@Override
	public UserVO getUserVOByEmailID(final String userID) throws GBQUserException {
		return getUserDAO().getUserVOByEmailID(userID);
	}

	@Override
	public UserVO getUserVOByPhoneNumber(final String userID) throws GBQUserException {
		return getUserDAO().getUserVOByPhoneNumber(userID);
	}

	@Override
	public Boolean checkUserLogon(final UserVO userVO) throws GBQUserException {
		String email = userVO.getEmailID();
		String phoneNumber = userVO.getPhoneNumber();
		UserVO userInfo = null;
		if (StringUtils.hasText(email)) {
			userInfo = getUserDAO().getUserVOByEmailID(email);
		}
		if (StringUtils.hasText(phoneNumber)) {
			userInfo = getUserDAO().getUserVOByPhoneNumber(phoneNumber);
		}
		if (null == userInfo) {
			throw new GBQUserException("User Not Found");
		}
		try {
			SecureHasher hasher = SecureHasher.getDefaultEngine();
			hasher.setSalt(userVO.getUserSalt());
			return hasher.check(userVO.getPassword(), userInfo.getPassword());
		} catch (Exception e) {
			throw new GBQUserException("Exception while comparing user hash", e);
		}
	}

	@Override
	public Cookie sendVerificationCodeToEmail(final String emailID, ModelMap model) throws GBQUserException {
		int randomInteger = 0;
		SecureRandom random = new SecureRandom();
		randomInteger = random.nextInt(AuthorizationServiceConstants.SECURE_RANDOM_LIMIT);

		logger.debug("addUser()::Verification code to email id:- " + randomInteger);
		Cookie cookie = new Cookie(AuthorizationServiceConstants.SECURE_VERIFICATION_CODE, String.valueOf(randomInteger));
		
		model.addAttribute(AuthorizationServiceConstants.SECURE_VERIFICATION_CODE, String.valueOf(randomInteger));

		/*
		 * EmailService emailService = new EmailService(); MailVO mailVO = new MailVO();
		 * mailVO.setFromAddress(Constants.FROM_EMAIL_ADDRESS);
		 * mailVO.setToAddress(Constants.FROM_EMAIL_ADDRESS);
		 * mailVO.setSubject("Send Random number"); mailVO.setBody("Test Body" +
		 * randomInteger); emailService.sendEmail(mailVO);
		 */
		return cookie;
	}

	@Override
	public String verifyUser(String email, String verifyCode, ModelMap model, HttpServletRequest httpRequest) throws GBQUserException {
		Cookie[] cookiesFromRequest = httpRequest.getCookies();
		String verifyCodeFromModel = null;
		for(Cookie cookie : cookiesFromRequest) {
			if(cookie.getName().equalsIgnoreCase(AuthorizationServiceConstants.SECURE_VERIFICATION_CODE)) {
				verifyCodeFromModel  = cookie.getValue();
			}
		}
		String response = AuthorizationServiceConstants.LOGIN_PAGE;
		logger.debug("verifyUser()::verifyCode:- " + verifyCode + " verifyCodeFromModel:- " + verifyCodeFromModel);
		if (StringUtils.hasText(verifyCode) && StringUtils.hasText(verifyCodeFromModel)
				&& verifyCode.equals(verifyCodeFromModel)) {
			logger.debug("verifyUser()::User ID is verified.");
			response = AuthorizationServiceConstants.LOGIN_PAGE; 
		}
		else if (StringUtils.hasText(verifyCode) && StringUtils.hasText(verifyCodeFromModel)
				&& !verifyCode.equals(verifyCodeFromModel)) {
			throw new GBQUserException("Verification code is invalid.");
		}
		else  if (!StringUtils.hasText(verifyCode) || !StringUtils.hasText(verifyCodeFromModel)){
			throw new GBQUserException("System Exception has occurred. Please try again!");
		}
		return response;
	}
}
