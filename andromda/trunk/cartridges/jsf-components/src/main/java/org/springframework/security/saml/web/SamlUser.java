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
	protected final Map<String,String> samlAttributes;
	
	public SamlUser(String username,
			Collection<? extends GrantedAuthority> authorities, 
			Object customAttributes,Map<String,String> samlAttributes) {
		super(username, null, authorities);
		this.customAttributes = customAttributes;
		this.samlAttributes = samlAttributes;
	}

	public Object getCustomAttributes() {
		return customAttributes;
	}

	public Map<String, String> getSamlAttributes() {
		return samlAttributes;
	}
	
}
