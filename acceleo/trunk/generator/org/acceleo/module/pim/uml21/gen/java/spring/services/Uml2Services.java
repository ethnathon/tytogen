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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;


public class Uml2Services {

	/*public Uml2Services() {
		Trace.TRACE = true;
	}*/
	
	/**
	 * Verify if an Element have a stereotype.
	 * Use keyword and profil to find stereotype. 
	 * Multiple stereotype are allow.
	 * @param elt Element used.
	 * @param stereotype Stereotype to search.
	 * @return true if found. false else.
	 * @throws ENodeCastException
	 */
	//FIXME remove this one
	public static boolean hazzStereotype(Element elt, String stereotype) {
		// search with real stereotype				
		Stereotype stereotypeFound = elt.getAppliedStereotype(stereotype);
		if (stereotypeFound == null) {
			for(Object o :elt.getAppliedStereotypes()){			
				Stereotype stereo=(Stereotype)o;
				if(stereo.getName().equals(stereotype)){
					stereotypeFound=stereo;
				}
			}			
		}		
		if (stereotypeFound == null) {
			// search with keywords
			return elt.hasKeyword(stereotype);
		}
		else{
			return true;
		}		
	}	
	
	
		
	//tous les types de parametres de m�thode + return de mani�re unique
	/**
	 * <p>Get set of types used by operations parameters</p>
	 * 
	 * @param c class
	 * @return Set<Type> set of types
	 */
	/*
	public Collection<Type> getMethodParametersTypes(Class c) {
		HashMap<String, Type> types = new HashMap<String, Type>();
		for(Object o : c.getOwnedOperations()){
			Operation op = (Operation)o;
			for(Object o2 : op.getOwnedParameters()){
				Parameter parameter = (Parameter)o2;
				if(!isPrimitiveType(parameter.getType())){					
					if(!types.containsKey(parameter.getType().getName())){		
						types.put(parameter.getType().getName(), parameter.getType());
					}
				}
			}
		}
		return types.values();
	}
	*/
//	
//	private boolean isPrimitiveType(Type type){		
//		if(type.getName().equalsIgnoreCase("boolean")||
//				type.getName().equalsIgnoreCase("integer")||
//				type.getName().equalsIgnoreCase("string")){		
//			return true;
//		}else{
//			return false;
//		}
//	}
	
	/**
	 * <p>Get list of Properties used at end of assocation</p>
	 * 
	 * @param element
	 * @return propertues list
	 * @throws ENodeCastException
	 */
	public static List<Property> getAssociationProperties(Element element) {
		return getAssociationProperties(element,false);
	}
	
	/**
	 * <p>Get list of Properties used at end of assocation</p>
	 * 
	 * @param element
	 * @param filterNavigable check if association is navigable 
	 * @return propertues list
	 * @throws ENodeCastException
	 */
	public static List<Property> getAssociationProperties(Element element, boolean filterNavigable) {
		Model model = element.getModel();
		List<Property> properties = new ArrayList<Property>();		
		for(Iterator<?> i = model.eAllContents(); i.hasNext(); ) {
			Object containedElement = i.next();			
			if( containedElement instanceof Association ) {				
				Association association = (Association)containedElement;				
				for(Object o : association.getMemberEnds()){
					Property property = (Property)o;
					if(property.getType()!=element){
						if(filterNavigable){
							if(property.isNavigable()){
								properties.add(property);
							}
						}else{
							properties.add(property);
						}
					}
				}
			}
		}				
		return properties;
	}	
	
	/**
	 * <p>Get value of given stereotype property (same as Tagged Value in UML1.4)
	 * 
	 * @param element
	 * @param stereotypeName stereotype name
	 * @param propertyName property name
	 * @return value
	 */
	public static String getStereotypeProperty(Element element, String stereotypeName, String propertyName) {
		Stereotype stereotype = getStereotype(element, stereotypeName);
		if( stereotype!=null ) {
			try {
				Object value = element.getValue(stereotype, propertyName);
				if( value instanceof String ) {
					return (String)value;
				} else if( value instanceof Boolean ) {
					return ((Boolean)value).toString();
				} else if( value instanceof Integer ) {
					return ((Integer)value).toString();
				}
			} catch(Exception ex) {}
		}
		return null;
	}
	
	/**
	 * <p>Get Stereotype instance from name</p>
	 * 
	 * @param element
	 * @param stereotypeName stereotype name
	 * @return stereotype instance
	 * @see Element#getApplicableStereotype(String)
	 */
	public static Stereotype getStereotype(Element element, String stereotypeName) {
		EList<Stereotype> stereotypes = element.getAppliedStereotypes();
		for(Iterator<Stereotype> i = stereotypes.iterator(); i.hasNext();) {
			Stereotype stereotype = i.next();
			if( stereotype.getName().equals(stereotypeName) ) {
				return stereotype;
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public static List<NamedElement> getUniqDependencies(Model model){		
		HashMap<String, NamedElement> dependencies = new HashMap<String, NamedElement>();
		for(Iterator<?> i = model.eAllContents(); i.hasNext(); ) {
			Object containedElement = i.next();			
			if( containedElement instanceof Dependency ) {
				Dependency dependency = (Dependency)containedElement;				
				NamedElement supplier = (NamedElement) dependency.getSuppliers().get(0);
				if(!dependencies.containsKey(supplier.getName())){
					dependencies.put(supplier.getName(),supplier);				
				}
			}
		}
		return new ArrayList<NamedElement>(dependencies.values());
	}
	
	/**
	 * <p>Get list of dependencies for given class</p>
	 * 
	 * @param element
	 * @return dependencies
	 */
	public static List<Dependency> getDependencies(Element element) {
		Model model = element.getModel();
		List<Dependency> dependencies = new ArrayList<Dependency>();
		for(Iterator<?> i = model.eAllContents(); i.hasNext(); ) {
			Object containedElement = i.next();
			if( containedElement instanceof Dependency ) {
				Dependency dependency = (Dependency)containedElement;
				if( isAssociatedDependency(dependency, element) ) {
					dependencies.add(dependency);
				}
			}
		}
		return dependencies;
	}

	/**
	 * @return check if dependency associated to given element
	 */
	private static boolean isAssociatedDependency(Dependency dependency, Element element) {
		List<?> clients = dependency.getClients();
		for(Iterator<?> i = clients.iterator(); i.hasNext(); ) {
			Type type = (Type)i.next();
			if( type.getName().equals(((org.eclipse.uml2.uml.Class)element).getName()) ) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Type> getReturnParameterSubClasses(Classifier classifier) {
		HashMap<String,Type> tMap = new HashMap<String,Type>();
		for(Iterator<Operation> iter = classifier.getOperations().iterator(); iter.hasNext(); ) {
			Operation operation = iter.next();
//		for(Operation operation:classifier.getOperations()){
			Parameter parameter = operation.getReturnResult();
			if(parameter!=null){
				Type type = parameter.getType();
				if(type!=null && !tMap.containsKey(type.getQualifiedName())){
					if(type instanceof Classifier){
						Classifier clazz=(Classifier)type;
						//traverse inheritance tree, all subclasses
						Collection<Classifier> subClasses = subClass(clazz);
						if(subClasses.size()>0){
							tMap.put(type.getQualifiedName(), type);
							for(Classifier sub:subClasses){
								if(!tMap.containsKey(sub.getQualifiedName())){
									tMap.put(sub.getQualifiedName(), sub);
								}
							}
						}
						if(clazz instanceof Interface){
//							Interface i = (Interface)clazz;
							Collection<Classifier> realizationClasses = subClass(clazz);
							if(realizationClasses.size()>0){
								tMap.put(type.getQualifiedName(), type);
								for(Classifier sub:realizationClasses){
									if(!tMap.containsKey(sub.getQualifiedName())){
										tMap.put(sub.getQualifiedName(), sub);
									}
								}
							}	
						}
					}
				}
			}
		}
		return new ArrayList<Type>(tMap.values());
	}
	
	public static List<Classifier> realizationClass(Interface i) {
		// search all class of the model
		List<Classifier> realizationClasses = new ArrayList<Classifier>();
		for (Iterator<?> iter = i.getModel().eAllContents(); iter.hasNext();) {	
			EObject classObj = (EObject) iter.next();
			if (classObj instanceof Class) {
				Class realizationClass = (Class)classObj;
				// select only children
				for(Iterator<InterfaceRealization> iter2 = realizationClass.getInterfaceRealizations().iterator(); iter2.hasNext(); ) {
					InterfaceRealization interfaceRealization = iter2.next();
//				for(InterfaceRealization interfaceRealization : realizationClass.getInterfaceRealizations()){
					if(interfaceRealization.getSupplier(i.getName())!=null){
						realizationClasses.add(realizationClass);	
					}
				}
			}
		}		
		return realizationClasses;
	}
	
	/**
	 * Find all subclass of a superclass. Browse all the model to find children.
	 * @param c The superclass
	 * @return A collection of org.eclipse.uml2.Class
	 * @throws ENodeCastException
	 * @throws FactoryException
	 */
	public static List<Classifier> subClass(Classifier c) {
		Element elt = c;
		do {
			elt = elt.getOwner();
		} while(elt.getOwner() != null || !(elt instanceof Model));
		
		if (!(elt instanceof Model)) {
			throw new ClassCastException("Root node element is not a Model but is " + elt.eClass().getName());
		}
		
		// search all class of the model
		List<Classifier> subClasses = new ArrayList<Classifier>();
		for (Iterator<?> iter = elt.eAllContents(); iter.hasNext();) {	
			EObject classObj = (EObject) iter.next();
			if (classObj instanceof Class) {
				Class subClass = (Class)classObj;
				// select only children
				if (subClass.getSuperClass(c.getName()) != null) {
					subClasses.add(subClass);
				}
			}
		}
		
		return subClasses;
	}
	
	public static List<Operation> getAllOperations(org.eclipse.uml2.uml.Class classifier){		
		Map<String, Operation> map2 = getAllOperations(classifier,  new HashMap<String, Operation>());
		return new ArrayList<Operation>(map2.values());
	}
	
	private static Map<String, Operation> getAllOperations(Classifier classifier, Map<String, Operation> map){	
		

		for (Iterator<Operation> iter = classifier.getAllOperations().iterator(); iter.hasNext(); ) {
			Operation op = iter.next();
//		for(Operation op: classifier.getAllOperations()){
			if(!map.containsKey(op.getName())){
				map.put(op.getName(), op);
			}
		}
		for (Iterator<Classifier> iter = classifier.getGenerals().iterator(); iter.hasNext(); ) {
			Classifier superClass = iter.next();
//		for(Classifier superClass : classifier.getGenerals()){
			map = getAllOperations(superClass,  map);
		}		
		return map;
	}
	
}

