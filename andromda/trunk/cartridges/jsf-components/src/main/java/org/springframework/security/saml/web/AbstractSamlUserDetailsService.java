package org.springframework.security.saml.web;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.impl.XSStringImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

public abstract class AbstractSamlUserDetailsService implements
		SAMLUserDetailsService {

	protected abstract Object getCustomAttributes(SAMLCredential credential);

	protected Collection<GrantedAuthority> getRoles(
			final SAMLCredential credential) {
		final Attribute attribute = credential.getAttributeByName("roles");
		final List<XMLObject> values = attribute.getAttributeValues();
		final Set<String> rolesSet = new LinkedHashSet<String>();
		if (values != null) {
			for (final XMLObject xmlObject : values) {
				if (xmlObject instanceof XSStringImpl) {
					final XSStringImpl nameStr = (XSStringImpl) xmlObject;
					rolesSet.add(nameStr.getValue());
				}
			}
		}
		return rolesSetToSpringRoles(rolesSet);
	}

	protected String getUserName(final SAMLCredential credential) {
		final Attribute attribute = credential
				.getAttributeByName("urn:oid:2.5.4.3");
		final List<XMLObject> values = attribute.getAttributeValues();
		String result = null;
		if (values != null) {
			for (final XMLObject xmlObject : values) {
				if (xmlObject instanceof XSStringImpl) {
					final XSStringImpl nameStr = (XSStringImpl) xmlObject;
					result = nameStr.getValue();
					break;
				}
			}
		}
		return result;
	}

	@Override
	public UserDetails loadUserBySAML(final SAMLCredential credential)
			throws UsernameNotFoundException {
		final SamlUser user = new SamlUser(getUserName(credential),
				getRoles(credential), getCustomAttributes(credential), null);
		return user;
	}

	protected Collection<GrantedAuthority> rolesSetToSpringRoles(
			final Set<String> rolesSet) {
		final Set<GrantedAuthority> springRoles = new LinkedHashSet<GrantedAuthority>();
		for (final String role : rolesSet) {
			final StringBuilder newRole = new StringBuilder("ROLE_");
			newRole.append(role.toUpperCase().trim().replace(" ", "_"));
			springRoles.add(new SimpleGrantedAuthority(newRole.toString()));
		}
		return springRoles;
	}
}
