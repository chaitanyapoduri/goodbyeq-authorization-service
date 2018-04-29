package com.goodbyeq.user.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.goodbyeq.exception.GBQServiceException;
import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.bo.GBQUserLoginVO;
import com.goodbyeq.user.dao.api.UserLoginDAO;
import com.goodbyeq.user.db.bo.UserLoginVO;
import com.goodbyeq.user.service.api.UserLoginService;

@Service("userLoginService")
@Transactional(propagation = Propagation.SUPPORTS)
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginDAO userLoginDAO;

	public UserLoginDAO getUserLoginDAO() {
		return userLoginDAO;
	}

	public void setUserLoginDAO(UserLoginDAO userLoginDAO) {
		this.userLoginDAO = userLoginDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addUserLoginInfo(final GBQUserLoginVO gbqUserLoginVO) throws GBQServiceException {
		UserLoginVO userLoginVO = new UserLoginVO();
		try {
			BeanUtils.copyProperties(gbqUserLoginVO, userLoginVO);
			getUserLoginDAO().addUserLoginInfo(userLoginVO);
		} catch (GBQUserException e) {
			throw new GBQServiceException(e);
		}
	}

}
