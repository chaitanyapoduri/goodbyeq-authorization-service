package com.goodbyeq.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.goodbyeq.authorization.bo.GBQRequestTokenVO;
import com.goodbyeq.authorization.bo.GoodByeQToken;
import com.goodbyeq.authorization.framework.JwtTokenUtil;
import com.goodbyeq.authorize.api.OAuth2TokenCreator;
import com.goodbyeq.exception.GBQServiceException;
import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.bo.GBQUserVO;
import com.goodbyeq.user.db.bo.UserVO;
import com.goodbyeq.user.service.api.GBQUserService;
import com.goodbyeq.user.service.api.UserService;

@Service("gbqUserService")
@Transactional(propagation = Propagation.SUPPORTS)
public class GBQUserServiceImpl implements GBQUserService {

	private static final Logger logger = LoggerFactory.getLogger(GBQUserServiceImpl.class);
	@Autowired(required = true)
	private UserService userService;

	@Autowired(required = true)
	private JwtTokenUtil tokenUtil;

	@Autowired(required = true)
	private OAuth2TokenCreator tokenCreator;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public JwtTokenUtil getTokenUtil() {
		return tokenUtil;
	}

	public void setTokenUtil(JwtTokenUtil tokenUtil) {
		this.tokenUtil = tokenUtil;
	}

	public OAuth2TokenCreator getTokenCreator() {
		return tokenCreator;
	}

	public void setTokenCreator(OAuth2TokenCreator tokenCreator) {
		this.tokenCreator = tokenCreator;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String createGBQUserToken(final GBQRequestTokenVO userRequestToken) throws GBQServiceException {
		UserVO userVO = null;
		try {
			userVO = getUserService().getUser(userRequestToken.getUserID(), userRequestToken.getUserIDType());
		} catch (final GBQUserException e) {
			logger.error("Exception while fetching user detais", e);
		}
		GBQUserVO gbqUserVO = new GBQUserVO();
		gbqUserVO = getGBQUserVO(userVO);
		String jwtToken = getTokenUtil().createAccessJwtToken(gbqUserVO);
		return jwtToken;
	}

	@Override
	public GoodByeQToken getSingleUseToken(final GBQRequestTokenVO userRequestToken) throws GBQUserException {
		logger.debug("Generating token for user");
		String jwtToken = null;
		GoodByeQToken token = null;
		try {
			jwtToken = this.createGBQUserToken(userRequestToken);
			token = getTokenCreator().generateNewToken(jwtToken);
		} catch (final Exception e) {
			logger.error("Exception while generating token", e);
			throw new GBQUserException(e);
		}
		return token;
	}

	@Override
	public GBQUserVO loadUserByPhoneNumber(final String phoneNumber) throws GBQServiceException {
		UserVO userVO = null;
		try {
			userVO = getUserService().getUserVOByPhoneNumber(phoneNumber);
		} catch (final GBQUserException e) {
			logger.error("Exception while fetching user detais", e);
		}
		GBQUserVO gbqUserVO = getGBQUserVO(userVO);
		return gbqUserVO;
	}

	@Override
	public GBQUserVO loadUserByEmailAddress(final String emailID) throws GBQServiceException {
		UserVO userVO = null;
		try {
			userVO = getUserService().getUserVOByEmailID(emailID);
		} catch (final GBQUserException e) {
			logger.error("Exception while fetching user detais", e);
		}
		GBQUserVO gbqUserVO = getGBQUserVO(userVO);
		return gbqUserVO;
	}

	private GBQUserVO getGBQUserVO(final UserVO userVO) {
		GBQUserVO gbqUserVO = new GBQUserVO();
		BeanUtils.copyProperties(userVO, gbqUserVO);
		gbqUserVO.setIsEmailVerified(userVO.isEmailVerified());
		gbqUserVO.setIsPhoneVerified(userVO.isPhoneVerified());
		return gbqUserVO;
	}

}
