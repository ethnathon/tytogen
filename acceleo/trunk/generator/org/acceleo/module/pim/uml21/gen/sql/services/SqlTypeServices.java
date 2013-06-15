/**
 * Copyright © 2007 - Obeo
 * All rights reserved.  This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 1.0
 * 
 * You can apply any license to the files generated with this template
 * and Acceleo.
 *
 * @author  www.obeo.fr
 * 
 **/
package org.acceleo.module.pim.uml21.gen.sql.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

public class SqlTypeServices {

	/**
	 * Convert a standard name to SQL convention.<br/>
	 * Exemple : myAttribute -> MY_ATTRIBUTE
	 * @param umlName
	 * @return
	 */
	public String toSqlNotation(String umlName) {
		Pattern p = Pattern.compile("(\\p{Lower})(\\p{Upper})");
		Matcher m = p.matcher(umlName);
		String sqlName = m.replaceAll("$1_$2");
		sqlName = sqlName.toUpperCase();
		return sqlName;
	}
	
	public boolean isBiAssociationSource(Property prop) {
		Class contClass = (Class)prop.eContainer();
		Class targetClass = (Class)prop.getType();
		if (contClass.getName().compareTo(targetClass.getName()) > 0)
			return true;
		else
			return false;
	}

}
