package org.andromda.cartridges.jsf.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;

import org.apache.myfaces.custom.calendar.HtmlCalendarRenderer.DateConverter;

/**
 * Overrides the default DateTimeConverter to include conversion of Calendar
 * instances as well as Date instances.
 * 
 * <p>
 * Unfortunately because of poor design in myfaces's calendar component, we have
 * to implement DateConverter so that we can correctly convert to a date in the
 * inputCalendar implementation.
 * </p>
 * 
 * @author Chad Brandon
 */
public class JSFDateTimeConverter extends javax.faces.convert.DateTimeConverter
		implements DateConverter {
	private TimeZone timeZone;

	/**
	 * Keeps track of whether or not the time zone was explicitly set.
	 */
	private boolean timeZoneIsSet;

	/**
	 * andromda.faces.DateTime
	 */
	public static final String CONVERTER_ID = "andromda.faces.DateTime";

	/**
	 * @see org.apache.myfaces.custom.calendar.HtmlCalendarRenderer.DateConverter
	 *      #getAsDate(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent)
	 */
	@Override
	public Date getAsDate(final FacesContext context,
			final UIComponent component) {
		Object value = getComponentValue(context, component);
		if (value instanceof Calendar) {
			value = ((Calendar) value).getTime();
		}
		return (Date) value;
	}

	/**
	 * @see javax.faces.convert.DateTimeConverter#getAsObject(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, String)
	 */
	@Override
	public Object getAsObject(final FacesContext context,
			final UIComponent component, final String value)
			throws ConverterException {
		Object asObject = null;
		final Class componentType = getComponentType(context, component);
		if (componentType != null) {
			asObject = super.getAsObject(context, component, value);
			if (Calendar.class.isAssignableFrom(componentType)
					&& asObject instanceof Date) {
				final Calendar calendar = Calendar.getInstance();
				calendar.setTimeZone(getTimeZone());
				calendar.setTime((Date) asObject);
				asObject = calendar;
			}
		}
		return asObject;
	}

	/**
	 * @see javax.faces.convert.DateTimeConverter#getAsString(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 * @throws ConverterException
	 */
	@Override
	public String getAsString(final FacesContext context,
			final UIComponent component, Object value) {
		if (value instanceof Calendar) {
			// - if the time zone is not explicitly set, use the one from the
			// calendar
			if (!timeZoneIsSet) {
				// - use the time zone from the calendar
				setTimeZone(((Calendar) value).getTimeZone());
			}
			value = ((Calendar) value).getTime();
		}
		final String pattern = getPattern();
		String result = null;
		if (value != null && pattern != null && pattern.trim().length() > 0) {
			final DateFormat format = new SimpleDateFormat(pattern);
			format.setTimeZone(getTimeZone());
			result = format.format((Date) value);
		}
		return result;
	}

	/**
	 * Gets the component type for the given <code>component</code>.
	 * 
	 * @param context
	 *            the current faces context.
	 * @param component
	 *            the component from which to retrieve the type.
	 * @return true/false
	 */
	protected Class getComponentType(final FacesContext context,
			final UIComponent component) {
		Class type = null;
		final ValueBinding binding = component.getValueBinding("value");
		if (binding != null) {
			type = binding.getType(context);
		}
		return type;
	}

	/**
	 * Gets the component Value for the given <code>component</code>.
	 * 
	 * @param context
	 *            the current faces context.
	 * @param component
	 *            the component from which to retrieve the value.
	 * @return true/false
	 */
	protected Object getComponentValue(final FacesContext context,
			final UIComponent component) {
		Object value = null;
		final ValueBinding binding = component.getValueBinding("value");
		if (binding != null) {
			value = binding.getValue(context);
		}
		return value;
	}

	/**
	 * Overridden because the default timeZone is set as GMT, whereas it should
	 * be the default for the operating system.
	 */
	@Override
	public TimeZone getTimeZone() {
		return timeZone == null ? TimeZone.getDefault() : timeZone;
	}

	/**
	 * @see #getTimeZone()
	 */
	@Override
	public void restoreState(final FacesContext facesContext, final Object state) {
		super.restoreState(facesContext, state);
		final Object[] values = (Object[]) state;
		timeZone = (TimeZone) values[4];
	}

	/**
	 * @see #getTimeZone()
	 */
	@Override
	public Object saveState(final FacesContext facesContext) {
		final Object[] values = (Object[]) super.saveState(facesContext);
		values[4] = timeZone;
		return values;
	}

	/**
	 * @see #getTimeZone()
	 */
	@Override
	public void setTimeZone(final TimeZone timeZone) {
		this.timeZone = timeZone;
		timeZoneIsSet = true;
	}
}
