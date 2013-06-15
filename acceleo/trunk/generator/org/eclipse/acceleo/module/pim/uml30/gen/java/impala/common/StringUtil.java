package org.eclipse.acceleo.module.pim.uml30.gen.java.impala.common;

import java.util.Arrays;
import java.util.List;

public class StringUtil {
	public static String convertSingularToPlural(String singular) {
		String result = "";
		if (singular != null && singular.length() > 0) {
			if (singular.toLowerCase().matches("piano|photo|dynamo|magneto|kilo|memento|solo")) {
				result = defaultPlural(singular);
			} else if (singular.matches(".*[aAeEiIoOuU][oO]")) {
				result = defaultPlural(singular);
			} else if (singular.length() > 1) {
				String end = singular.substring(singular.length() - 1);
				String endUpper = end.toUpperCase();
				if (endUpper.equals("O") || endUpper.equals("S")
						|| endUpper.equals("X") || endUpper.equals("Z")) {
					if (end.endsWith(endUpper)) {
						result = singular + "ES";
					} else {
						result = singular + "es";
					}

				} else if (endUpper.equals("F")) {
					if (end.endsWith(endUpper)) {
						result = singular.substring(0, singular.length() - 1)
								+ "VES";
					} else {
						result = singular.substring(0, singular.length() - 1)
								+ "ves";
					}
				} else if (endUpper.equals("Y")) {
					if (end.endsWith(endUpper)) {
						result = singular.substring(0, singular.length() - 1)
								+ "IES";
					} else {
						result = singular.substring(0, singular.length() - 1)
								+ "ies";
					}
				} else {
					result = defaultPlural(singular);
				}
			} else if (singular.length() > 2) {
				String end = singular.substring(singular.length() - 2);
				String endUpper = end.toUpperCase();
				if (endUpper.equals("CH") || endUpper.equals("SH")) {
					if (end.endsWith(endUpper)) {
						result = singular + "ES";
					} else {
						result = singular + "es";
					}
				} else if (endUpper.equals("FE")) {
					if (end.endsWith(endUpper)) {
						result = singular.substring(0, singular.length() - 2)
								+ "VES";
					} else {
						result = singular.substring(0, singular.length() - 2)
								+ "ves";
					}
				} else {
					result = defaultPlural(singular);
				}
			}
		}
		return result;
	}

	private static String defaultPlural(String singular) {
		String result;
		if (singular.toUpperCase().equals(singular)) {
			result = singular + "S";
		} else {
			result = singular + "s";
		}
		return result;
	}
	
	public static List<String> eleminateJavaStrangeLetters(String s) {
		return Arrays.asList(s.split("[^a-zA-Z0-9]"));
	}
}
