/**
 * Copyright © 2007 - Obeo
 * All rights reserved.  This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 1.0
 * 
 * You can apply any license to the files generated with this template
 * and Acceleo.
 * 
 *
 * @author  www.obeo.fr
 * 
 **/
package org.acceleo.module.pim.uml21.services;

import org.eclipse.uml2.uml.Element;

/**
 * Acceleo services for String.
 * 
 */
public class StringServices {

	/**
	 * Compares two strings.
	 */
	public int compare(Element node, String s1, String s2) {
		return s1.compareTo(s2);
	}

	/**
	 * Transforms a String with notation aa.bb.cc to aa/bb/cc. Useful for
	 * package translating.
	 */
	public String toPath(String packageName) {
		return packageName.trim().replaceAll("\\.", "/");
	}

	/**
	 * Returns a unique code from a String. <strong>NB</strong> The returned
	 * code is not guaranteed to be unique, but it is guaranteed to be equal for
	 * two Strings that are equal.
	 * 
	 * @param s
	 *            The string for which you want a unique code.
	 * @return Returns the hash code of the given String
	 */
	public int uniqueCode(String s) {
		return s.hashCode();
	}

	/**
	 * Convert to standard notation.<br/>
	 * Removes spaces, non letter/digit characters.
	 * 
	 * @param name
	 *            Name to transform
	 * @return The String normalized
	 */
	public String toStandardNotation(String name) {
		StringBuffer dest = new StringBuffer(name.length());

		boolean nextMaj = false;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) {
				// ignore
				continue;
			}

			switch (c) {
			case ' ':
				nextMaj = true;
				break;
			case '\u00E9': // e acute
			case '\u00E8': // e grave
			case '\u00EA': // e circumflex
				dest.append('e');
				break;
			case '\u00e0': // a grave
				dest.append('a');
				break;
			case '\u00f9': // u grave
				dest.append('u');
				break;
			default:
				if (nextMaj) {
					nextMaj = false;
					dest.append(Character.toUpperCase(c));
				} else {
					dest.append(c);
				}
				break;
			}
		}
		return dest.toString();
	}

	/**
	 * Converts to standard package notation. Removes spaces, non letter/digit
	 * characters and transforms to lower case.
	 * 
	 * @param name
	 *            Name to transform
	 * @return The String normalized
	 */
	public String toPkgName(String name) {
		return toStandardNotation(name).toLowerCase();
	}

	/**
	 * Repeat nb times the s String.
	 * 
	 * @param node
	 *            Just for apply service.
	 * @param s
	 *            The String to repeat.
	 * @param nb
	 *            How many time it must be repeated.
	 * @return
	 */
	public String repeat(Element node, String s, int nb) {
		int size = nb * s.length();
		StringBuffer strBuf = new StringBuffer(size);
		for (int i = 0; i < size; i++) {
			strBuf.append(s);
		}
		return strBuf.toString();
	}

	/**
	 * Tests if a given String represents a java primitive type.
	 * 
	 * @param typeName
	 *            The name of the type to test.
	 * @return Returns true if and only if the given String represents a java
	 *         primitive type.
	 */
	public boolean isPrimitiveType(String typeName) {
		return "int,boolean,long,double,float,char,byte,".indexOf(typeName
				+ ",") >= 0;
	}
}
