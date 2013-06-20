package org.andromda.cartridges.jsf;

import java.text.MessageFormat;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Provides access to the application resource messages.
 */
public class Messages
{
    /**
     * The messages bundle name.
     */
    private static final String BUNDLE_NAME = "message-resources";

    /**
     * Gets the message given the <code>key</code> by substituting any
     * required <code>arguments</code>.
     *
     * @param key the message key.
     * @param arguments any message arguments to substitute.
     * @return the message (or key if the message isn't found).
     */
    public static String get(
        String key,
        Object[] arguments)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        String resourceString;
        try
        {
            final ResourceBundle bundle = ResourceBundle.getBundle(
                    BUNDLE_NAME,
                    context.getViewRoot().getLocale());
            resourceString = bundle.getString(key);
            if (arguments != null)
            {
                final MessageFormat format = new MessageFormat(resourceString,
                        context.getViewRoot().getLocale());
                resourceString = format.format(arguments);
            }
        }
        catch (final MissingResourceException exception)
        {
            resourceString = key;
        }
        return resourceString;
    }
}
