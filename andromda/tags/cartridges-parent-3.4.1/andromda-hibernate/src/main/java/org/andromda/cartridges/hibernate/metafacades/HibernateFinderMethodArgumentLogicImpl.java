package org.andromda.cartridges.hibernate.metafacades;

import org.andromda.metafacades.uml.ClassifierFacade;

/**
 * MetafacadeLogic implementation for
 * org.andromda.cartridges.hibernate.metafacades.HibernateFinderMethodArgument.
 *
 * @see org.andromda.cartridges.hibernate.metafacades.HibernateFinderMethodArgument
 */
public class HibernateFinderMethodArgumentLogicImpl
    extends HibernateFinderMethodArgumentLogic
{
    private static final long serialVersionUID = 34L;
    // ---------------- constructor -------------------------------
    /**
     * @param metaObject
     * @param context
     */
    public HibernateFinderMethodArgumentLogicImpl(
        Object metaObject,
        String context)
    {
        super(metaObject, context);
    }

    /**
     * Defines if specific setters methods will be created for primitive types
     * and dates
     */
    private static final String USE_SPECIALIZED_SETTERS = "hibernateQueryUseSpecializedSetters";

    /**
     * @see org.andromda.cartridges.hibernate.metafacades.HibernateFinderMethodArgument#getQueryArgumentNameSetter()
     */
    @Override
    protected String handleGetQueryArgumentNameSetter()
    {
        StringBuilder setterName = new StringBuilder();
        boolean specializedSetters =
            Boolean.valueOf(String.valueOf(this.getConfiguredProperty(USE_SPECIALIZED_SETTERS))).booleanValue();
        ClassifierFacade classifier = this.getType();
        if (classifier != null)
        {
            if (specializedSetters)
            {
                if (classifier.isPrimitive())
                {
                    setterName.append("set").append(classifier.getWrapperName().replaceAll("(.)*\\.", ""));
                }
                else if (classifier.isDateType() || classifier.isStringType())
                {
                    setterName.append("set").append(classifier.getName());
                }
                else
                {
                    setterName.append("setParameter");
                }
            }
            else
            {
                setterName.append("setParameter");
            }
            if (classifier.isCollectionType())
            {
                setterName.append("List");
            }
        }
        return setterName.toString();
    }
}