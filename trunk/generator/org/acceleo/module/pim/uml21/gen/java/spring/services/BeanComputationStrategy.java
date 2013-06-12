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

public interface BeanComputationStrategy {
	String computeBeanId(String className, List<String> interfaceNames);
}
