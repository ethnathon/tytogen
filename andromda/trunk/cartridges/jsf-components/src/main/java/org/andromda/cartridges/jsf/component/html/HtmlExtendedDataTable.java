package org.andromda.cartridges.jsf.component.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.component.html.ext.HtmlDataTable;

/**
 * Extends the datatable and provides the ability to have a backing value: this
 * is useful when submitting tables of information.
 * 
 * @author Chad Brandon
 */
public class HtmlExtendedDataTable extends HtmlDataTable {
	/**
	 * The value attribute.
	 */
	private static final String VALUE = "value";

	private Object backingValue = null;

	/**
	 * The attribute that stores the backing value.
	 */
	public static final String BACKING_VALUE = "backingValue";

	/**
	 * Stores the identifier columns attribute.
	 */
	private String identifierColumns;

	/**
	 * The attrribute that stores the identifier columns.
	 */
	public static final String IDENTIFIER_COLUMNS = "identifierColumns";

	/**
	 * Indicates whether or not this objects are equal, first by comparing them
	 * directly for equality and if not equal in that sense compares the columns
	 * returned by {@link #getIdentifierColumns()}.
	 * 
	 * @param object1
	 *            the first object.
	 * @param object2
	 *            the second object.
	 * @param properties
	 *            if equality fails, then these properties are compared for
	 *            equality (all of them must be equal or else this operation
	 *            will return false).
	 * @return equal true/false
	 */
	private boolean equal(final Object object1, final Object object2,
			final String[] properties) {
		boolean equal = object1 == object2;
		if (!equal && object1 != null && object2 != null) {
			for (final String property : properties) {
				if (StringUtils.isNotBlank(property)) {
					final Object value1 = getProperty(object1, property);
					final Object value2 = getProperty(object2, property);
					if (value1 != null && value2 != null) {
						equal = value1.equals(value2);
						if (!equal) {
							break;
						}
					}
				}
			}
		}
		return equal;
	}

	/**
	 * Retrieves the backing value of this extended data table (the backing
	 * value contains the values which the result of the value attribute are
	 * compared against).
	 * 
	 * @return this.backingValue
	 */
	protected Object getBackingValue() {
		if (backingValue == null) {
			final ValueBinding binding = getValueBinding(BACKING_VALUE);
			backingValue = binding == null ? null : binding
					.getValue(getFacesContext());
		}
		return backingValue;
	}

	/**
	 * Gets the type of the backing value attribute.
	 * 
	 * @return Class The backing value's type or null if the backing value isn't
	 *         defined.
	 */
	private Class getBackingValueType() {
		return getBindingType(BACKING_VALUE);
	}

	/**
	 * Gets the binding type given the attribute name.
	 * 
	 * @param name
	 *            the name of the component's attribute.
	 * @return type Class the binding type or null if the binding wasn't found.
	 */
	private Class getBindingType(final String name) {
		Class type = null;
		final ValueBinding binding = getValueBinding(name);
		if (binding != null) {
			final FacesContext context = getFacesContext();
			type = binding.getType(context);
		}
		return type;
	}

	/**
	 * Retrieves the identifier columns component attribute.
	 * 
	 * @return this.identifierColumns the identifier columns component
	 *         attribute.
	 */
	protected String getIdentifierColumns() {
		if (identifierColumns == null) {
			identifierColumns = (String) getAttributes()
					.get(IDENTIFIER_COLUMNS);
			if (identifierColumns == null) {
				final ValueBinding binding = getValueBinding(IDENTIFIER_COLUMNS);
				identifierColumns = binding == null ? null : ObjectUtils
						.toString(binding.getValue(getFacesContext()));
			}
		}
		return identifierColumns;
	}

	private Object getProperty(final Object bean, final String property) {
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (final Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	/**
	 * Overridden to provide population of the backingValue's items with the
	 * value's items (and updating the model afterwards).
	 * 
	 * @see javax.faces.component.UIData#getValue()
	 */
	@Override
	public Object getValue() {
		Object value = super.getValue();
		Object backingValue = getBackingValue();
		if (backingValue != null) {
			final Class valueType = getValueType();
			final Class backingValueType = getBackingValueType();
			if (valueType != backingValueType) {
				throw new IllegalArgumentException(
						"The 'value' and 'backingValue' must be of the same type");
			}
			final String identifierColumnsString = getIdentifierColumns();
			final String[] identifierColumns = StringUtils
					.isBlank(identifierColumnsString) ? new String[0]
					: StringUtils.trimToEmpty(identifierColumnsString).split(
							"\\s*,\\s*");

			// - handle collections
			if (backingValue instanceof Collection) {
				if (!(backingValue instanceof List)) {
					backingValue = new ArrayList((Collection) backingValue);
				}
				if (!(value instanceof List)) {
					value = new ArrayList((Collection) value);
				}
				final List backingValues = (List) backingValue;
				final List values = (List) value;
				for (final ListIterator iterator = backingValues.listIterator(); iterator
						.hasNext();) {
					final Object backingValueItem = iterator.next();
					for (final Iterator valueIterator = values.iterator(); valueIterator
							.hasNext();) {
						final Object valueItem = valueIterator.next();
						if (equal(backingValueItem, valueItem,
								identifierColumns)) {
							iterator.set(valueItem);
							break;
						}
					}
				}
				value = backingValues;
			}

			// - handle arrays
			else if (backingValue.getClass().isArray()) {
				final Object[] backingValues = (Object[]) backingValue;
				final Object[] values = value != null ? (Object[]) value
						: new Object[0];
				for (int backingValueCtr = 0; backingValueCtr < backingValues.length; backingValueCtr++) {
					final Object backingValueItem = backingValues[backingValueCtr];
					for (final Object valueItem : values) {
						if (equal(backingValueItem, valueItem,
								identifierColumns)) {
							backingValues[backingValueCtr] = valueItem;
							break;
						}
					}
				}
				value = backingValues;
			} else {
				throw new IllegalArgumentException(
						"The backing value must be a collection or array");
			}
		}
		updateModelValue(value);
		return value;
	}

	/**
	 * Gets the value's type attribute or null if value was not defined.
	 * 
	 * @return Class the value's type or null if undefined.
	 */
	public Class getValueType() {
		return getBindingType(VALUE);
	}

	/**
	 * Updates the model (i.e. underlying managed bean's value).
	 * 
	 * @param value
	 *            the value from which to update the model.
	 */
	public void updateModelValue(final Object value) {
		final ValueBinding binding = getValueBinding(VALUE);
		if (binding != null) {
			binding.setValue(getFacesContext(), value);
		}
	}
}
