package com.goodbyeq.authoriation.api.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.goodbyeq.authorize.api.GBQAuthorities;
import com.goodbyeq.authorize.api.GBQUserRoles;
import com.goodbyeq.authorize.api.GBQUserType;
import com.goodbyeq.exception.GBQServiceException;
import com.goodbyeq.user.bo.GBQUserVO;
import com.goodbyeq.user.service.api.GBQUserService;

/**
 * This class implementation overrides the spring load by user name to get user
 * information at run time
 *
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS)
public class GBQUserDetailsServiceImpl implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(GBQUserDetailsServiceImpl.class);

	@Autowired
	private GBQUserService gbqUserService;

	public GBQUserService getGbqUserService() {
		return gbqUserService;
	}

	public void setGbqUserService(final GBQUserService gbqUserService) {
		this.gbqUserService = gbqUserService;
	}

	@Override
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		if (!StringUtils.hasText(userName)) {
			throw new UsernameNotFoundException("User name not found");
		}
		GBQUserVO userVO = null;
		UserDetails userDetails = null;
		boolean isUserNameEmail = false;
		try {
			if (isUserNameEmail) {
				logger.debug("Profile retreived by email");
				userVO = getGbqUserService().loadUserByEmailAddress(userName);
			} else {
				logger.debug("Profile retreived by phone");
				userVO = getGbqUserService().loadUserByPhoneNumber(userName);
			}
			userDetails = this.getUserDetailsVO(userVO);
		} catch (GBQServiceException e) {
			logger.error("Exception while loading user", e);
			throw new UsernameNotFoundException("Exception while accessing user details");
		}
		logger.debug("User details returned");
		return userDetails;
	}

	private UserSecurityDetails getUserDetailsVO(final GBQUserVO vo) {
		UserSecurityDetails ud = null;
		if (vo != null) {
			String userName = vo.getFirstName() + "  " + vo.getLastName();
			ud = new UserSecurityDetails(vo.getUserID(), userName, vo.getPassword(), getUserRoles(vo.getUserType()),
					getUserAuthorities(vo.getUserType()), true, true, true, true,
					UserSecurityDetails.AUTHENTICATION_TYPE_OAUTH2);
		}
		return ud;
	}

	private LinkedHashSet<String> getUserRoles(final String userType) {
		LinkedHashSet<String> userRoles = new LinkedHashSet<String>();
		if (GBQUserType.USER.getValue().equals(userType)) {
			userRoles.add(GBQUserRoles.USER.getValue());
		} else {
			userRoles.add(GBQUserRoles.ADMIN.getValue());
		}
		return userRoles;

	}

	private Set<SimpleGrantedAuthority> getUserAuthorities(final String userType) {
		final Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(GBQAuthorities.READ.getValue()));
		if (GBQUserType.ADMIN.getValue().equals(userType)) {
			authorities.add(new SimpleGrantedAuthority(GBQAuthorities.UPDATE_ADMIN.getValue()));
			authorities.add(new SimpleGrantedAuthority(GBQAuthorities.DELETE.getValue()));
		}
		if (GBQUserType.USER.getValue().equals(userType)) {
			authorities.add(new SimpleGrantedAuthority(GBQAuthorities.UPDATE_USER.getValue()));
		}
		return authorities;

	}
}
