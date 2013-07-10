package org.andromda.cartridges.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import org.andromda.metafacades.uml.AssociationEndFacade;
import org.andromda.metafacades.uml.ClassifierFacade;
import org.andromda.metafacades.uml.Entity;
import org.andromda.metafacades.uml.EntityAttribute;
import org.andromda.metafacades.uml.EntityMetafacadeUtils;
import org.andromda.metafacades.uml.MetafacadeUtils;
import org.andromda.metafacades.uml.UMLProfile;
import org.apache.commons.lang.StringUtils;

public class PatchEntityUtils {


	/**
	 * Gets all identifier attributes for an entity. If 'follow' is true, and if
	 * no identifiers can be found on the entity, a search up the inheritance
	 * chain will be performed, and the identifiers from the first super class
	 * having them will be used. All identifier association relationships are
	 * traversed to find the identifying attributes of the related
	 * associationEnd classifiers, in addition to the primitive/wrapped
	 * identifiers on the Entity itself.
	 * 
	 * @param entity
	 *            the entity for which to retrieve the identifiers
	 * @param follow
	 *            a flag indicating whether or not the inheritance hierarchy
	 *            should be followed
	 * @return the collection of entity identifier attributes.
	 */
	public static Collection<EntityAttribute> getIdentifierAttributes(
			final Entity entity, final boolean follow) {
		Collection<EntityAttribute> identifierAttributes = new ArrayList<EntityAttribute>();
		final Collection<EntityAttribute> identifiers = EntityMetafacadeUtils
				.getIdentifiers(entity, follow);
		identifierAttributes.addAll(identifiers);
		// Find identifiers of association identifiers otherEnd
        final Collection<AssociationEndFacade> associations = new ArrayList<AssociationEndFacade>(entity.getAssociationEnds());
        MetafacadeUtils.filterByStereotype(
                associations,
                UMLProfile.STEREOTYPE_IDENTIFIER);
		for (AssociationEndFacade identifier : associations) {

				AssociationEndFacade associationEnd = identifier.getOtherEnd();
				ClassifierFacade classifier = associationEnd.getType();
				if (classifier instanceof Entity) {
					Collection<EntityAttribute> entityIdentifiers = getIdentifierAttributes(
							(Entity) classifier, true);
					identifierAttributes.addAll(entityIdentifiers);
				}
		
		}
		if (identifiers.isEmpty() && follow
				&& entity.getGeneralization() instanceof Entity) {
			identifierAttributes.addAll(EntityMetafacadeUtils.getIdentifiers(
					(Entity) entity.getGeneralization(), follow));
		}
		// Reorder join columns if order is specified - must match FK column
		// order
		String joinOrder = (String) entity
				.findTaggedValue("andromda_persistence_joincolumn_order");
		/*
		 * System.out.println(entity.getName() + " getIdentifierAttributes " +
		 * joinOrder + " tags=" + entity.getTaggedValues().size() +
		 * " identifierAttr=" + identifierAttributes.size()); if
		 * (entity.getTaggedValues().size() > 1) { for ( TaggedValueFacade value
		 * : entity.getTaggedValues()) { System.out.println(entity.getName() +
		 * " tag name=" + value.getName() + " value=" + value); } }
		 */
		if (StringUtils.isNotBlank(joinOrder)) {
			final Collection<EntityAttribute> sortedIdentifiers = new ArrayList<EntityAttribute>();
			String[] joinList = StringUtils.split(joinOrder, " ,;|");
			for (String column : joinList) {
				for (EntityAttribute facade : identifierAttributes) {
					/*
					 * if (facade instanceof EntityAssociationEnd) {
					 * EntityAssociationEnd assoc =
					 * (EntityAssociationEnd)facade; if
					 * (assoc.getColumnName().equalsIgnoreCase(column)) {
					 * sortedIdentifiers.add(assoc); } } else if (facade
					 * instanceof EntityAttribute)
					 */
					{
						EntityAttribute attr = (EntityAttribute) facade;
						if (attr.getColumnName().equalsIgnoreCase(column)) {
							sortedIdentifiers.add(attr);
						}
					}
				}
			}
			// System.out.println(entity.getName() + " getIdentifierAttributes "
			// + joinOrder + identifiers.size());
			// Add remaining identifiers not found in joincolumn ordered list
			if (sortedIdentifiers.size() < identifierAttributes.size())
				for (EntityAttribute facade : identifierAttributes) {
					if (!sortedIdentifiers.contains(facade)) {
						sortedIdentifiers.add(facade);
					}
				}
			identifierAttributes = sortedIdentifiers;
		} else {
			identifiers.addAll(identifierAttributes);
		}
		return identifierAttributes;
	}

}
