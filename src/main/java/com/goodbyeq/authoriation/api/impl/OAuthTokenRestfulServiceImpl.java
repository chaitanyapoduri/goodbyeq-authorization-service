package com.goodbyeq.authoriation.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.goodbyeq.authorize.api.OAuthTokenRestfulService;
import com.goodbyeq.user.service.api.GBQUserService;

@Controller
public class OAuthTokenRestfulServiceImpl implements OAuthTokenRestfulService {
	private static final Logger logger = LoggerFactory.getLogger(OAuthTokenRestfulServiceImpl.class);

	private GBQUserService gbqUserService;

	public GBQUserService getGbqUserService() {
		return gbqUserService;
	}

	public void setGbqUserService(GBQUserService gbqUserService) {
		this.gbqUserService = gbqUserService;
	}

}
