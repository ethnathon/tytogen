/**
 * Copyright � 2007 - Obeo
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

public class Uml2Services {

	/**
	 * Verify if an Element have a stereotype.
	 * Use keyword and profile to find stereotype. 
	 * Multiple stereotype are allowed.
	 * @param elt Element used.
	 * @param stereotype Stereotype to search.
	 * @return true if found. false else.
	 * @
	 */
	public boolean hasStereotype(Element elt, String stereotype)  {
		Stereotype stereotypeFound = elt.getAppliedStereotype(stereotype);
		if (stereotypeFound == null) {
			
			// search with keywords
			if (elt.hasKeyword(stereotype))
					return true;
			
			return false;
		}
		else
			return true;		
	}

	/**
	 * Returns the tagged value for a given name and stereotype on an element.
	 * @param element A UML element of the model
	 * @param stereotypeName The stereotype name
	 * @param propertyName The tagged value name
	 * @return Returns an object or null if the tagged value does not exist.
	 */
	public Object getValue(Element element, String stereotypeName, String propertyName) {
	Stereotype stereotype = element.getAppliedStereotype(stereotypeName);
	if (stereotype == null) {
		return null;
	}
	return element.getValue(stereotype, propertyName);
	}

	
	/**
	 * Filter a list of Element which have to stereotype specify as parameter
	 * @param nodes
	 * @param stereotype
	 * @
	 */
	public List<Element> filterWithStereotype(List<Element> elements, String stereotype)  {
		List<Element> result = new ArrayList<Element>();
		Iterator<? extends Element> it = elements.iterator();
		while (it.hasNext()){
			Element element = it.next();
			if (hasStereotype(element, stereotype)) {
				result.add(element);
				//System.out.println("With : "+((NamedElement)element).getName());
			}
		}
		return result;
	}
	
	//TODO : � enlever apr�s correction bug 58 et � laisser dans .mt
	public List<Element> attributeIds(List<Element> elements)  {
		return filterWithStereotype(elements, "id");
	}
	
	
	/**
	 * Filter a list of Element which doesn't have to stereotype specify as parameter
	 * @param nodes
	 * @param stereotype
	 * @
	 */
	public List<Element> filterWithOutStereotype(List<Element> elements, String stereotype)  {
		List<Element> result = new ArrayList<Element>();
		Iterator<Element> it = elements.iterator();
		while (it.hasNext()){
			Element element = it.next();
			if (!hasStereotype(element, stereotype)) {
				result.add(element);
				//System.out.println("Without : "+((NamedElement)elt).getName());
			}
		}
		return result;
	}

	public List<Element> attributeNotIds(List<Element> elements)  {		
		/*return ExpressionTools.sub(
				new ENode(nodes, new ENode(ENode.EMPTY)), 
				new ENode(filterWithStereotype(nodes, "id"), new ENode(ENode.EMPTY)) );*/
		return filterWithOutStereotype(elements, "id");
	}

	/**
	 * Return only the primitive type. Test if the Meta Class is PrimitiveType or
	 * the name of the type of the attribute 
	 * @param c
	 * @return
	 * @
	 */
	public List<Property> primitiveAttributes(Class c)  {		
		List<Property> result = new ArrayList<Property>();
		Iterator<Property> it = c.getAttributes().iterator();
		
		while (it.hasNext()){
			Property prop = (Property)it.next();
			String name = prop.getType().getName();
		
			if (	prop.getType() instanceof PrimitiveType ||
					name.equals("String") || 
					name.equals("Integer") || 
					name.equals("Date") || 
					name.equals("Boolean") ) {				
				result.add(prop);
			}
		}
		return result;
	}

	/**
	 * Find all subclass of a superclass. Browse all the model to find children.
	 * @param c The superclass
	 * @return A collection of org.eclipse.uml2.Class
	 * @
	 * @throws FactoryException
	 */
	public List<Class> subClass(Class c)  {
		Element elt = c;
		do {
			elt = elt.getOwner();
		} while(elt.getOwner() != null || !(elt instanceof Model));
		
		if (!(elt instanceof Model)) {
			throw new RuntimeException("Root node element is not a Model but is " + elt.eClass().getName());
		}
		
		// search all class of the model
		List<Class> subClasses = new ArrayList<Class>();
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

	/**
	 * Find all properties which are an association to this class. Browse all the model to find them.
	 * @param c The class pointed
	 * @return A collection of org.eclipse.uml2.Property
	 * @
	 * @throws FactoryException
	 */
	public List<Property> pointedAttribute(Class c) {
		Element elt = c;
		do {
			elt = elt.getOwner();
		} while(elt.getOwner() != null || !(elt instanceof Model));
		
		if (!(elt instanceof Model)) {
			throw new RuntimeException("Root node element is not a Model but is " + elt.eClass().getName());
		}
		
		// search all class of the model
		List<Property> properties = new ArrayList<Property>();
		for (Iterator<?> iter = elt.eAllContents(); iter.hasNext();) {
			EObject attObj = (EObject) iter.next();
			if (attObj instanceof Property) {
				Property prop = (Property)attObj;
				// select properties which have as target the c Class		
				if (prop.getType() == c) {
					properties.add(prop);
				}
			}
		}
		
		return properties;
	}
	
	/**
	 * Give to opposite attribute of an association.
	 * @param p
	 * @return
	 * @
	 * @throws FactoryException
	 */
	public Property oppositeAttribute(Property p) {
		Association asso = p.getAssociation();
		if (asso != null) {			
			List<Property> list = asso.getMemberEnds();
			for (Iterator<Property> iter = list.iterator(); iter.hasNext();) {
				Property prop = (Property) iter.next();
				if (prop != p){
					return prop;
				}
			}
		}
		return null;
	}

	public Property oppositeAttributeOf(Association association, Classifier element) {
		List<Property> list = association.getMemberEnds();
		for (Iterator<Property> iter = list.iterator(); iter.hasNext(); ) {
			Property prop = (Property) iter.next();
			if (!EcoreUtil.equals(prop.getType(),element))
				return prop;
		}
		return null;
	}

	/**
	 * Return a list of parameters of an operation without the return type
	 * @param node
	 * @return
	 * @
	 * @throws FactoryException
	 */
	public List<Parameter> getRealParameters(Operation operator) {
		List<Parameter> parListFiltered = new ArrayList<Parameter>();
		List<Parameter> parList = operator.getOwnedParameters();
		for (Iterator<Parameter> iterPar = parList.iterator(); iterPar.hasNext();) {
			Parameter par = (Parameter) iterPar.next();
			if (!par.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL))
				parListFiltered.add(par);
		}
		return parListFiltered;		
	}
	
	/**
	 * Return return parameter
	 * @param op
	 * @return
	 */
	public Parameter returnResult(Operation op) {
		return op.getReturnResult();
	}
	
	/**
	 * Return the list of class' associations
	 * @param element
	 * @return
	 */
	public  List<Association> getAssociations(Class element) {
		return element.getAssociations();
	}
	
	/**
	 * Returns the list of the given interface's associations.
	 * @param element
	 * @return
	 */
	public List<Association> getAssociations(Interface element) {
		return element.getAssociations();
	}
	
	/**
	 * This service allows acceleo scripts to access the navigability of a
	 * property. The UML specification states that a property is navigable if
	 * it is NOT owned by an assoiation, or if it is owned by an association
	 * AND is among the association's navigableOwnedEnds.
	 * @param prop
	 * @return
	 */
	public boolean isNavigable(Property prop) {
		return prop.isNavigable();
	}
	
	/**
	 * Service that looks for an element in the model by its xmi id. If the
	 * element is not found in the current resource, it is looked for in the
	 * other resources of its resource set.
	 * @param xmiId The xmi id to look for
	 * @param e The node from which to look for the element.
	 * @return Returns the correct element, or null if it is not found
	 * in e's resource or any other resource of e's resource set.
	 */
	public Element getElementByXmiId(String xmiId, Element e) {
		Resource r= e.eResource();
		EObject res = r.getEObject(xmiId);
		// If it is not found in the current resource, look for it
		// in the other resources of its resourceSet
		if (res == null) {
			List<Resource> resources = r.getResourceSet().getResources();
			for (Iterator<Resource> it = resources.iterator(); it.hasNext() && res == null;) {
				Resource nextResource = (Resource) it.next();
				if (nextResource != r) {
					res = nextResource.getEObject(xmiId);
				}
			}
		}
		return (Element)res;
	}
}
