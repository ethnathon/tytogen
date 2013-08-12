package org.andromda.cartridges.jsf.validator;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;

/**
 * A JSF validator that uses the apache commons-validator to perform either
 * client or server side validation.
 */
public class JSFValidator implements Validator, Serializable {
	private static final Log logger = LogFactory.getLog(JSFValidator.class);

	/**
	 * Returns the commons-validator action that's appropriate for the validator
	 * with the given <code>name</code>. This method lazily configures validator
	 * resources by reading <code>/WEB-INF/validator-rules.xml</code> and
	 * <code>/WEB-INF/validation.xml</code>.
	 * 
	 * @param name
	 *            The name of the validator
	 * @return getValidatorResources().getValidatorAction(name)
	 */
	public static ValidatorAction getValidatorAction(final String name) {
		return getValidatorResources().getValidatorAction(name);
	}

	private String formId;

	/**
	 * Validator type.
	 */
	private String type;

	/**
	 * Parameter for the error message. This parameter is passed through to the
	 * commons-validator.
	 */
	private String[] args;

	/**
	 * The parameters for this validator.
	 */
	private final Map parameters = new HashMap();

	/**
	 * The commons-validator action, that carries out the actual validation.
	 */
	private ValidatorAction validatorAction;

	/**
	 * The location of the validator rules.
	 */
	public static final String RULES_LOCATION = "/WEB-INF/validator-rules.xml";

	/**
	 * The key that stores the validator resources
	 */
	private static final String VALIDATOR_RESOURCES_KEY = "org.andromda.jsf.validator.resources";

	private static final long serialVersionUID = 1;

	/**
	 * Returns the commons-validator resources. This method lazily configures
	 * validator resources by reading <code>/WEB-INF/validator-rules.xml</code>
	 * and <code>/WEB-INF/validation.xml</code>.
	 * 
	 * @return the commons-validator resources
	 */
	public static ValidatorResources getValidatorResources() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final ExternalContext external = context.getExternalContext();
		final Map applicationMap = external.getApplicationMap();
		ValidatorResources validatorResources = (ValidatorResources) applicationMap
				.get(VALIDATOR_RESOURCES_KEY);
		if (validatorResources == null) {
			final String rulesResource = RULES_LOCATION;
			final String validationResource = "/WEB-INF/validation.xml";
			final InputStream rulesInput = external
					.getResourceAsStream(rulesResource);
			if (rulesInput == null) {
				throw new JSFValidatorException("Could not find rules file '"
						+ rulesResource + '\'');
			}
			final InputStream validationInput = external
					.getResourceAsStream(validationResource);
			if (validationInput != null) {
				final InputStream[] inputs = new InputStream[] { rulesInput,
						validationInput };
				try {
					validatorResources = new ValidatorResources(inputs);
					applicationMap.put(VALIDATOR_RESOURCES_KEY,
							validatorResources);
				} catch (final Throwable throwable) {
					throw new JSFValidatorException(throwable);
				}
			} else {
				logger.info("No validation rules could be loaded from --> '"
						+ validationResource + ", validation not configured");
			}
		}
		return validatorResources;
	}

	/**
	 * Default constructor for faces-config.xml
	 */
	public JSFValidator() {
		// - default constructor for faces-config.xml
	}

	/**
	 * Constructs a new instance of this class with the given <code>form</code>
	 * and <code>validatorAction</code>.
	 * 
	 * @param formIdIn
	 * @param validatorActionIn
	 */
	public JSFValidator(final String formIdIn,
			final ValidatorAction validatorActionIn) {
		formId = formIdIn;
		validatorAction = validatorActionIn;
	}

	/**
	 * Adds the parameter with the given <code>name</code> and the given
	 * <code>value</code>.
	 * 
	 * @param name
	 *            the name of the parameter.
	 * @param value
	 *            the parameter's value
	 */
	public void addParameter(final String name, final Object value) {
		parameters.put(name, value);
	}

	/**
	 * Retrieves an error message, using the validator's message combined with
	 * the errant value.
	 * 
	 * @param context
	 * @return ValidatorMessages.getMessage
	 */
	public String getErrorMessage(final FacesContext context) {
		return ValidatorMessages.getMessage(validatorAction, args, context);
	}

	/**
	 * Attempts to retrieve the form field from the form with the given
	 * <code>formName</code> and the field with the given <code>fieldName</code>
	 * . If it can't be retrieved, null is returned.
	 * 
	 * @param form
	 *            the form to validate.
	 * @param fieldName
	 *            the name of the field.
	 * @return the found field or null if it could not be found.
	 */
	private Field getFormField(final Form form, final String fieldName) {
		Field field = null;
		if (form != null) {
			field = form.getField(fieldName);
		}
		return field;
	}

	/**
	 * Gets the parameters for this validator (keyed by name).
	 * 
	 * @return a map containing all parameters.
	 */
	public Map getParameters() {
		return parameters;
	}

	/**
	 * The getter method for the <code>type</code> property. This property is
	 * passed through to the commons-validator.
	 * 
	 * @return this.type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the validator class from the underlying <code>validatorAction</code>
	 * .
	 * 
	 * @return the validator class
	 * @throws ClassNotFoundException
	 */
	private Class getValidatorClass() throws ClassNotFoundException {
		final FacesContext context = FacesContext.getCurrentInstance();
		final ExternalContext external = context.getExternalContext();
		final Map applicationMap = external.getApplicationMap();
		final String validatorClassName = validatorAction.getClassname();
		Class validatorClass = (Class) applicationMap.get(validatorClassName);
		if (validatorClass == null) {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader == null) {
				classLoader = getClass().getClassLoader();
			}
			validatorClass = classLoader.loadClass(validatorClassName);
			applicationMap.put(validatorClassName, validatorClass);
		}
		return validatorClass;
	}

	/**
	 * Gets the validator method for the underlying <code>validatorAction</code>
	 * .
	 * 
	 * @return the validator method.
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	private Method getValidatorMethod() throws ClassNotFoundException,
			NoSuchMethodException {
		final Class[] parameterTypes = new Class[] {
				javax.faces.context.FacesContext.class, Object.class,
				java.util.Map.class, java.util.Collection.class,
				org.apache.commons.validator.ValidatorAction.class,
				org.apache.commons.validator.Field.class };
		return getValidatorClass().getMethod(validatorAction.getMethod(),
				parameterTypes);
	}

	/**
	 * The setter method for the <code>args</code> property. This property is
	 * passed through to the commons-validator..
	 * 
	 * @param argsIn
	 *            The new value for the <code>args</code> property.
	 */
	public void setArgs(final String[] argsIn) {
		args = argsIn;
	}

	/**
	 * The setter method for the <code>type</code> property. This property is
	 * passed through to the commons-validator.
	 * 
	 * @param typeIn
	 *            The new value for the <code>type</code> property.
	 */
	public void setType(final String typeIn) {
		type = typeIn;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + ":formId=" + formId + ", validatorAction="
				+ (validatorAction != null ? validatorAction.getName() : null);
	}

	/**
	 * This <code>validate</code> method is called by JSF to verify the
	 * component to which the validator is attached.
	 * 
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, Object)
	 */
	@Override
	public void validate(final FacesContext context,
			final UIComponent component, final Object value) {
		if (formId != null) {
			try {
				final Form validatorForm = getValidatorResources().getForm(
						Locale.getDefault(), formId);
				if (validatorForm != null) {
					final Field field = getFormField(validatorForm,
							component.getId());
					if (field != null) {
						final Collection errors = new ArrayList();
						getValidatorMethod().invoke(getValidatorClass(),
								context, value, getParameters(), errors,
								validatorAction, field);
						if (!errors.isEmpty()) {
							throw new ValidatorException(new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									(String) errors.iterator().next(), null));
						}
					} else {
						logger.error("No field with id '" + component.getId()
								+ "' found on form '" + formId + '\'');
					}
				} else {
					logger.error("No validator form could be found with id '"
							+ formId + '\'');
				}
			} catch (final ValidatorException exception) {
				throw exception;
			} catch (final Exception exception) {
				logger.error(exception.getMessage(), exception);
			}
		}
	}
}