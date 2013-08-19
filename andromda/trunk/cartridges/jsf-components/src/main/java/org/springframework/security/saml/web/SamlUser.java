package org.springframework.security.saml.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SamlUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7372716308259035130L;

	/**
	 * The value of this attribute is calculated in SamlUserDetailsServiceImpl
	 * by the generated application
	 */
	protected final Object customAttributes;
	protected final Map<String, Object> samlAttributes;

	public SamlUser(final String username,
			final Collection<? extends GrantedAuthority> authorities,
			final Object customAttributes,
			final Map<String, Object> samlAttributes) {
		super(username, " ", authorities);
		this.customAttributes = customAttributes;
		this.samlAttributes = samlAttributes;
	}

	public Object getCustomAttributes() {
		return customAttributes;
	}

	public Map<String, Object> getSamlAttributes() {
		return samlAttributes;
	}

}
