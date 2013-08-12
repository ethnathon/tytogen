package org.andromda.cartridges.jsf.taglib;

import javax.faces.component.UIComponent;

import org.andromda.cartridges.jsf.component.html.HtmlExtendedDataTable;
import org.apache.myfaces.taglib.html.ext.HtmlDataTableTag;

/**
 * Extends dataTable to provide the ability to submit tables of data and render
 * the data back into the table using a backing value.
 * 
 * @author Chad Brandon
 */
public class HtmlExtendedDataTableTag extends HtmlDataTableTag {
	/**
	 * A comma separated list of the columns that uniquely identify the row for
	 * this data table.
	 */
	private String identifierColumns;

	/**
	 * The backing value for this table.
	 */
	private String backingValue;

	/**
	 * The component type for this tag.
	 */
	private static final String COMPONENT_TYPE = HtmlExtendedDataTable.class
			.getName();

	/**
	 * @return Returns the backingValue.
	 */
	public String getBackingValue() {
		return backingValue;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	@Override
	public String getComponentType() {
		return COMPONENT_TYPE;
	}

	/**
	 * @return Returns the identifierColumns.
	 */
	public String getIdentifierColumns() {
		return identifierColumns;
	}

	/**
	 * @param backingValueIn
	 *            The backingValue to set.
	 */
	public void setBackingValue(final String backingValueIn) {
		backingValue = backingValueIn;
	}

	/**
	 * @param identifierColumnsIn
	 *            The identifierColumns to set.
	 */
	public void setIdentifierColumns(final String identifierColumnsIn) {
		identifierColumns = identifierColumnsIn;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	@Override
	protected void setProperties(final UIComponent component) {
		super.setProperties(component);
		setStringProperty(component, HtmlExtendedDataTable.IDENTIFIER_COLUMNS,
				identifierColumns);
		setStringProperty(component, HtmlExtendedDataTable.BACKING_VALUE,
				backingValue);
	}
}
