package org.springframework.security.saml.web;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class AbstractTestAuthenticationProvider implements
		AuthenticationProvider, InitializingBean {
	protected String propertyFile = null;
	/**
	 * Format of the file
	 * 
	 * %username%.password = ... (optional) %username%.roles = ... (comma
	 * separated list of roles: mandatory) %username%.... = ... (you can add
	 * extra data here)
	 * 
	 */
	protected Properties userData = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isBlank(propertyFile)) {
			propertyFile = "/test-users.properties";
		}
		final InputStream userProps = this.getClass().getResourceAsStream(
				propertyFile);
		if (userProps == null) {
			throw new FileNotFoundException("File [" + propertyFile
					+ "] not found");
		}

		userData = new Properties();
		userData.load(userProps);

	}

	@Override
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		Authentication result = null;
		if (authentication instanceof UsernamePasswordAuthenticationToken) {
			final UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
			String username = authToken.getName();
			final Object credentials = authToken.getCredentials();
			if (StringUtils.isBlank(username)) {
				throw new UsernameNotFoundException("Blank username");
			}
			username = username.trim();
			final Collection<GrantedAuthority> authorities = getRoles(username);

			final String password = userData.getProperty(
					username + ".password", "");
			checkPassword(credentials, password);
			result = new UsernamePasswordAuthenticationToken(
					authentication.getPrincipal(), credentials, authorities);
			final Object authDetails = getAuthenticationDetails(username,
					authToken.getDetails());

			((UsernamePasswordAuthenticationToken) result).setDetails(authDetails);
		}

		return result;
	}

	protected Collection<GrantedAuthority> getRoles(String username) {
		final String roles = userData.getProperty(username + ".roles", "");
		if (StringUtils.isBlank(roles)) {
			throw new UsernameNotFoundException("username [" + username
					+ "] check [" + propertyFile + "] contains the key "
					+ username + ".roles");
		}
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		final String[] roleArray = roles.split(",");
		for (final String role : roleArray) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	protected void checkPassword(final Object credentials, final String password) {
		if (StringUtils.isNotBlank(password)) {
			final String providedPass = (String) credentials;
			if (!password.trim().equals(providedPass)) {
				throw new BadCredentialsException("Password error");
			}

		}
	}

	protected abstract Object getAuthenticationDetails(String userName,
			Object oldDetails);

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication
				.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}

}
