/**
 * Copyright © 2007 Thierry TEMPLIER & Jerome BENOIS
 * All rights reserved.  This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 1.0
 * 
 * You can apply any license to the files generated with this template
 * and Acceleo.
 *
 * @author  Jerome Benois <jerome.benois@gmail.com> 
 * @author  Thierry Templier <templth@gmail.com>
 * 
 **/
package org.acceleo.module.pim.uml21.gen.java.spring.services;

import java.util.List;

public class DefaultBeanComputationStrategy implements BeanComputationStrategy {
	public String computeBeanId(String className, List<String> interfacesNames) {
		String id = className;
		if( id.endsWith("Impl") ) {
			id = id.substring(0, id.length()-4);
		}
		if( id.length()>0 ) {
			id = Character.toLowerCase(id.charAt(0)) + id.substring(1);
		}		
		return id; 
	}
}
