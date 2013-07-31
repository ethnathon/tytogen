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

	@Override
	public UserDetails loadUserBySAML(SAMLCredential credential)
			throws UsernameNotFoundException {
		SamlUser user = new SamlUser(getUserName(credential),
				getRoles(credential), getCustomAttributes(credential), null);
		return user;
	}

	protected abstract Object getCustomAttributes(SAMLCredential credential);

	protected String getUserName(SAMLCredential credential) {
		Attribute attribute = credential.getAttributeByName("urn:oid:2.5.4.3");
		List<XMLObject> values = attribute.getAttributeValues();
		String result = null;
		if (values != null) {
			for (XMLObject xmlObject : values) {
				if (xmlObject instanceof XSStringImpl) {
					XSStringImpl nameStr = (XSStringImpl) xmlObject;
					result = nameStr.getValue();
					break;
				}
			}
		}
		return result;
	}

	protected Collection<GrantedAuthority> getRoles(SAMLCredential credential) {
		Attribute attribute = credential.getAttributeByName("roles");
		List<XMLObject> values = attribute.getAttributeValues();
		Set<String> rolesSet = new LinkedHashSet<String>();
		if (values != null) {
			for (XMLObject xmlObject : values) {
				if (xmlObject instanceof XSStringImpl) {
					XSStringImpl nameStr = (XSStringImpl) xmlObject;
					rolesSet.add(nameStr.getValue());
				}
			}
		}
		return rolesSetToSpringRoles(rolesSet);
	}

	protected Collection<GrantedAuthority> rolesSetToSpringRoles(
			Set<String> rolesSet) {
		Set<GrantedAuthority> springRoles = new LinkedHashSet<GrantedAuthority>();
		for (String role : rolesSet) {
			StringBuilder newRole = new StringBuilder("ROLE_");
			newRole.append(role.toUpperCase().trim().replace(" ", "_"));
			springRoles.add(new SimpleGrantedAuthority(newRole.toString()));
		}
		return springRoles;
	}
}
