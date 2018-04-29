package com.goodbyeq.authorization.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("authenticationProvider")
public class GBQAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		Authentication authenticatedToken = null;
		String principal = (String) authentication.getPrincipal();
		String credentials = (String) authentication.getCredentials();
		UserDetails user = getUserDetailsService().loadUserByUsername((String) authentication.getPrincipal());
		authenticatedToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
				user.getAuthorities());
		return authenticatedToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
