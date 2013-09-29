package org.springframework.security.saml.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSBase64Binary;
import org.opensaml.xml.schema.XSBoolean;
import org.opensaml.xml.schema.XSInteger;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.XSURI;
import org.opensaml.xml.schema.impl.XSStringImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

public abstract class AbstractSamlUserDetailsService implements
		SAMLUserDetailsService {

	protected static final Log LOGGER = LogFactory
			.getLog(AbstractSamlUserDetailsService.class);

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
		SamlUser user;
		try {
			user = new SamlUser(getUserName(credential), getRoles(credential),
					getCustomAttributes(credential),
					getSamlAttributes(credential));
			validateUser(user);
			LOGGER.info("SAML User log in: " + user);
		} catch (UsernameNotFoundException e) {
			LOGGER.info("SAML User login refused: " + e.getMessage()
					+ " credentials:" + credential);
			throw e;
		}
		return user;
	}

	/**
	 * Called after the SamlUser has been created validate to give the application 
	 * the chance to validate the newly created user.
	 *  
	 * @param user
	 * @throws UsernameNotFoundException if the user can't be validated
	 */
	protected abstract void validateUser(SamlUser user) throws UsernameNotFoundException;

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

	protected Map<String, Object> getSamlAttributes(SAMLCredential credential) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Attribute attribute : credential.getAttributes()) {
			result.put(attribute.getName(), getValue(attribute));
		}
		return result;
	}

	protected Object getValue(final Attribute samlAttribute) {
		Object result = null;
		if (samlAttribute != null) {
			final List<XMLObject> attributeValues = samlAttribute
					.getAttributeValues();
			if (attributeValues != null && attributeValues.size() > 0) {
				if (attributeValues.size() == 1) {
					result = getSingleAttributeValue(attributeValues.get(0));
				} else {
					Collection<Object> coll = new ArrayList<Object>();
					for (XMLObject value : attributeValues) {
						coll.add(getSingleAttributeValue(value));
					}
					result = (Object[]) coll.toArray(new Object[coll.size()]);
				}
			}
		}
		return result;
	}

	protected Object getSingleAttributeValue(XMLObject xobj) {
		Object result = null;
		if (xobj instanceof XSString) {
			final XSString attributeXS = (XSString) xobj;
			result = attributeXS.getValue();
		} else if (xobj instanceof XSInteger) {
			final XSInteger attributeXS = (XSInteger) xobj;
			result = attributeXS.getValue();
		} else if (xobj instanceof XSBoolean) {
			final XSBoolean attributeXS = (XSBoolean) xobj;
			result = attributeXS.getValue();
		} else if (xobj instanceof XSURI) {
			final XSURI attributeXS = (XSURI) xobj;
			result = attributeXS.getValue();
		} else if (xobj instanceof XSBase64Binary) {
			final XSBase64Binary attributeXS = (XSBase64Binary) xobj;
			result = attributeXS.getValue();
		}
		return result;
	}
}
