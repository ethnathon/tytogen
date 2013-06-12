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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Class;


/**
 * <p>Services provides utility for Spring Framework</p>
 *   
 */
public class SpringServices extends Uml2Services {
	
	private final static String DAO = "Dao";
	private final static String SERVICE = "Service";
	
	private final static String BEAN_NAME = "beanName";	
	
	private final static String TRANSACTIONAL = "Transactional";
	private final static String PROPAGATION = "propagation";
	private final static String ISOLATION = "isolation";
	
	private final static String REMOTE = "Remote";
	
	/*
	public String st(Class cl){
		String s = "list : ";
		for(Object o : cl.getAppliedStereotypes()){
			Stereotype stereotype = (Stereotype)o;
			s+=stereotype.getQualifiedName()+" ";
		}
		return s;
	}
	*/
	
	/**
	 * <p> Return "beanName" property value.
	 * This property use on <<Service>> and <<Dao>> stereotypes.
	 * </p>
	 *  
	 * @param element
	 * @return bean name or null
	 * @throws ENodeCastException 
	 * @see UML2Services#getStereotypeProperty(Element, String, String)
	 */
	private String getSpecifiedBeanId(Element element) {
		if( hazzStereotype(element, DAO) ) {
			return getStereotypeProperty(element, DAO, BEAN_NAME);
		} else if( hazzStereotype(element, SERVICE)  ) {
			return getStereotypeProperty(element, SERVICE, BEAN_NAME);
		}
		return null;
	}

	/**
	 * <p>Build Spring bean identifier.
	 * 
	 * Used BeanComputationStrategy in order to define binding between interface and impl class.
	 * </p>
	 * 
	 * @param element
	 * @return bean identitifier
	 * @throws ENodeCastException 
	 * @see #getSpecifiedBeanId(Element)
	 * @see BeanComputationStrategy
	 * @see DefaultBeanComputationStrategy
	 * @see BeanComputationStrategy#computeBeanId(String, List)
	 */
	public String computeBeanId(Element element) {
		org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class)element;
		String beanId = getSpecifiedBeanId(element);
		if( beanId==null ) {
			List<Interface> implementedInterfaces = clazz.getAllImplementedInterfaces();
			List<String> interfacesNames = new ArrayList<String>();
			for(Iterator<Interface> i = implementedInterfaces.iterator(); i.hasNext();) {
				Interface implementedInterface = (Interface)i.next();
				interfacesNames.add(implementedInterface.getName());
			}
			BeanComputationStrategy beanComputationStrategy = new DefaultBeanComputationStrategy();
			return beanComputationStrategy.computeBeanId(clazz.getName(), interfacesNames);
		} else {
			return beanId;
		}
	}
	
	public String computeBeanId(String s){
		StringBuffer sb = new StringBuffer();
		int pos;
		while( (pos = s.indexOf("."))!=-1){			
			sb.append(s.substring(0,pos));							
			sb.append(s.substring(pos+1,pos+2).toUpperCase());
			s = s.substring(pos+2,s.length());			
		}
		sb.append(s.substring(pos+1,s.length()));
		return sb.toString();
	}
	
	/**
	 * <p> This method traverse model in order to find <<Service>> with <<Transactional>> Operation.
	 * </p>
	 * @param element
	 * @return true if transactional operation found
	 * @throws ENodeCastException 
	 */
	public boolean hasServicesRemoteOperations(Element element)  {
		Model model = element.getModel();
		for(Iterator<?> i = model.eAllContents();i.hasNext();) {
			Object tmpElement = i.next();
			if( tmpElement instanceof org.eclipse.uml2.uml.Class ) {
				org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class)tmpElement;
//				List<Operation> operations = clazz.getOwnedOperations();
				return hasRemoteOperations(clazz);
			}
		}
		
		return false;
	}
	/**
	 * <p> This method traverse model in order to find <<Service>> with <<Transactional>> Operation.
	 * </p>
	 * @param element
	 * @return true if transactional operation found
	 * @throws ENodeCastException 
	 */
	public boolean hasServicesTransactionOperations(Element element)  {
		Model model = element.getModel();
		for(Iterator<?> i = model.eAllContents();i.hasNext();) {
			Object tmpElement = i.next();
			if( tmpElement instanceof org.eclipse.uml2.uml.Class ) {
				org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class)tmpElement;
				return hasTransactionalOperations(clazz);
			}
		}
		
		return false;
	}
	
	
	/**
	 * <p> This method traverse given class in order to find <<Transactional>> Operation.</p>
	 * @param class service
	 * @return true if transactional operation found
	 * @throws ENodeCastException 
	 */
	public boolean hasTransactionalOperations(org.eclipse.uml2.uml.Class c)  {
		List<Operation> operations = c.getOwnedOperations();
		for(Iterator<Operation> i = operations.iterator(); i.hasNext();) {
			Operation operation = (Operation)i.next();	
			if( hazzStereotype(operation, TRANSACTIONAL) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRemoteOperations(Model model)  {		
		Iterator<?> it = model.eAllContents();
		while(it.hasNext()){
			Object element = it.next();
			if(element instanceof Classifier){
				Classifier classifier = (Classifier)element;
				if(hasRemoteOperations(classifier)){
					return true;
				}
			}
		}		
		return false;
	}
	
	/**
	 * <p> This method traverse given class in order to find <<Remote>> Operation.</p>
	 * @param class service
	 * @return true if transactional operation found
	 * @throws ENodeCastException 
	 */
	public boolean hasRemoteOperations(org.eclipse.uml2.uml.Classifier c)  {
		List<Operation> operations = c.getOperations();
		for(Iterator<Operation> i = operations.iterator(); i.hasNext();) {
			Operation operation = (Operation)i.next();	
			if( hazzStereotype(operation, REMOTE) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p> Get list of <<Transactional>> operations for given class.</p>
	 * @param class service
	 * @return List list of transactional operations
	 * @throws ENodeCastException 
	 */
	public List<Operation> getTransactionalOperations(Class clazz)  {
		List<Operation> operations = clazz.getOwnedOperations();
		List<Operation> transactionalOperations = new ArrayList<Operation>();
		for(Iterator<Operation> i = operations.iterator(); i.hasNext();) {
			Operation operation = (Operation)i.next();
			if( hazzStereotype(operation, TRANSACTIONAL) ) {
				transactionalOperations.add(operation);
			}
		}
		return transactionalOperations;
	}
	
	/**
	 * <p> Get list of <<Remote>> operations for given class.</p>
	 * @param class service
	 * @return List list of transactional operations
	 * @throws ENodeCastException 
	 */
	public List<Operation> getRemoteOperations(Class clazz)  {
		List<Operation> operations = clazz.getOwnedOperations();
		List<Operation> transactionalOperations = new ArrayList<Operation>();
		for(Iterator<Operation> i = operations.iterator(); i.hasNext();) {
			Operation operation = i.next();
			if( hazzStereotype(operation, REMOTE) ) {
				transactionalOperations.add(operation);
			}
		}
		return transactionalOperations;
	}
	
	public List<Class> getRemoteClasses(Model model)  {
		List<Class> remoteClasses = new ArrayList<Class>();		
		for(Iterator<?> i = model.eAllContents();i.hasNext();) {
			Object tmpElement = i.next();
			if( tmpElement instanceof Class ) {
				Class clazz = (Class)tmpElement;
				if(hasRemoteOperations(clazz)){
					remoteClasses.add(clazz);
				}				
			}
		}
		return remoteClasses;
	}
	
	/** 
	 * Check propagation property.
	 * 
	 * @param element
	 * @return true if Propagation property is Defined.
	 * @see UML2Services#getStereotypeProperty(Element, String, String)
	 */
	public boolean isTransactionalPropagationValueSpecified(Element element) {
		return (getStereotypeProperty(element, TRANSACTIONAL, PROPAGATION)!=null);
	}

	/**
	 * <p>Get value of propagation property, used on given <<Transactional>> operation</p>
	 * 
	 * @param operation 
	 * @return value of propagation property
	 * @see UML2Services#getStereotypeProperty(Element, String, String)
	 */
	public String getTransactionalPropagationValue(Operation operation) {
		return getStereotypeProperty(operation, TRANSACTIONAL, PROPAGATION);
	}

	/** 
	 * Check isolation property.
	 * 
	 * @param element
	 * @return true if isolation property is Defined.
	 * @see UML2Services#getStereotypeProperty(Element, String, String)
	 */
	public boolean isTransactionalIsolationValueSpecified(Element element) {
		return (getStereotypeProperty(element, TRANSACTIONAL, ISOLATION)!=null);
	}

	/**
	 * <p>Get value of isolation property, used on given <<Transactional>> operation</p>
	 * 
	 * @param operation 
	 * @return value of isolation property
	 * @see UML2Services#getStereotypeProperty(Element, String, String)
	 */
	public String getTransactionalIsolationValue(Element element) {
		return getStereotypeProperty(element, TRANSACTIONAL, ISOLATION);
	}

	/**
	 * <p>Get associate business object from given class.</p>
	 * 
	 * @param element
	 * @return business object
	 * @throws ENodeCastException
	 * @see #getDependencies(Element)
	 * @see Dependency#getSuppliers()
	 */	
	public Class getAssociatedBusinessObject(Class clazz) {
		List<Dependency> dependencies = getDependencies(clazz);		
		if( dependencies.size()>0 ) {
			Dependency dependency = (Dependency)dependencies.get(0);
			Class businessObject = (Class)dependency.getSuppliers().get(0);			
			return businessObject;
		}
		return null;
	}	
	
	/**
	 * <p>Build variable initialization statement.</p>
	 * 
	 * @param element typed element
	 * @return bloc to initialize variable statement
	 */
	public String getDefaultValue(Element element) {		
		Type type = null;
		if( element instanceof Parameter ) {
			type = ((Parameter)element).getType();
		} else if( element instanceof Type ) {
			type = (Type)element;
		}

		if( type!=null){
			if("int".equals(type.getName()) || "long".equalsIgnoreCase(type.getName())		
				|| "short".equalsIgnoreCase(type.getName())
				|| "float".equalsIgnoreCase(type.getName()) || "double".equalsIgnoreCase(type.getName()) ) {
			return "0";
			} else if( "boolean".equalsIgnoreCase(type.getName()) ) {
				return "false";
			}		
		}
		return "null";				
	}
	
	public boolean isJavaClassifier(Classifier classifier){
		if(classifier.getAppliedStereotypes().size()==0){
			return true;
		}else{
			return false;
		}
	}
		
}
