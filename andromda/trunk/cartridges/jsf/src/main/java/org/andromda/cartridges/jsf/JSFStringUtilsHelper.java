package org.andromda.cartridges.jsf;

import org.andromda.utils.StringUtilsHelper;

public class JSFStringUtilsHelper extends StringUtilsHelper {
	
	public static String uncapitalizeToPropertyName(String name){
		String baseName = upperCamelCaseName(name);
		if(startsWithLowercaseLetter(baseName.substring(1))){
			baseName = uncapitalize(baseName);
		}
		return baseName;
	}

}
