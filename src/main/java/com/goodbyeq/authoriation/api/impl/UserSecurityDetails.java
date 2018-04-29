package com.goodbyeq.authoriation.api.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	public static final String AUTHENTICATION_TYPE_INTERNAL = "INTERNAL";
	public static final String AUTHENTICATION_TYPE_OAUTH2 = "OAUTH2";
	private String userID;
	private String username;
	private String password;
	private Collection<String> roles;
	private Collection<SimpleGrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private String authenticationType;

	public UserSecurityDetails(final String userID,final  String username,final  String password,final  Collection<String> roles,
			final Collection<SimpleGrantedAuthority> authorities,final  boolean accountNonExpired,final  boolean accountNonLocked,
			final boolean credentialsNonExpired, boolean enabled,final String authenticationType) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.authorities = authorities;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.authenticationType = authenticationType;
	}

	@Override
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(final SimpleGrantedAuthority[] authorities) {
		if (authorities == null)
			this.authorities = new LinkedHashSet<SimpleGrantedAuthority>();
		else
			this.authorities = new LinkedHashSet<SimpleGrantedAuthority>(Arrays.asList(authorities));
	}

	public void setAuthorities(final Collection<SimpleGrantedAuthority> authorities) {
		if (authorities == null)
			this.authorities = new LinkedHashSet<SimpleGrantedAuthority>();
		else
			this.authorities = new LinkedHashSet<SimpleGrantedAuthority>(authorities);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(LinkedHashSet<String> roles) {
		this.roles = roles;
	}

	public String getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(LinkedHashSet<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountNonExpired ? 1231 : 1237);
		result = prime * result + (accountNonLocked ? 1231 : 1237);
		result = prime * result + ((authenticationType == null) ? 0 : authenticationType.hashCode());
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + (credentialsNonExpired ? 1231 : 1237);
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSecurityDetails other = (UserSecurityDetails) obj;
		if (accountNonExpired != other.accountNonExpired)
			return false;
		if (accountNonLocked != other.accountNonLocked)
			return false;
		if (authenticationType == null) {
			if (other.authenticationType != null)
				return false;
		} else if (!authenticationType.equals(other.authenticationType))
			return false;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (credentialsNonExpired != other.credentialsNonExpired)
			return false;
		if (enabled != other.enabled)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
