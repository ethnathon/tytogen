package org.andromda.cartridges.jsf.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.andromda.cartridges.jsf.component.BinaryFile;
import org.andromda.cartridges.jsf.utils.ComponentUtils;

/**
 * The tag class for the <code>s:validatorScript</code> tag.
 */
public class BinaryFileTag extends UIComponentTag {
	/**
	 * The component type.
	 */
	private static final String COMPONENT_TYPE = BinaryFile.COMPONENT_TYPE;

	private String value;

	private String fileName;

	private String contentType;

	private String encoding;

	private String prompt;

	/**
	 * Returns the component type.
	 */
	@Override
	public String getComponentType() {
		return COMPONENT_TYPE;
	}

	/**
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return Returns the encoding.
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return Returns the prompt.
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	@Override
	public String getRendererType() {
		return BinaryFile.RENDERER_TYPE;
	}

	/**
	 * Sets the value.
	 * 
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param contentType
	 *            The contentType to set.
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @param encoding
	 *            The encoding to set.
	 */
	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param prompt
	 *            The prompt to set.
	 */
	public void setPrompt(final String prompt) {
		this.prompt = prompt;
	}

	/**
	 * Sets properties for the component.
	 * 
	 * @param component
	 *            The component whose properties we're setting
	 */
	@Override
	public void setProperties(final UIComponent component) {
		final FacesContext context = getFacesContext();
		ComponentUtils.setValueProperty(context, component, getValue());
		ComponentUtils.setStringProperty(BinaryFile.FILE_NAME_ATTRIBUTE,
				context, component, getFileName());
		ComponentUtils.setStringProperty(BinaryFile.CONTENT_TYPE_ATTRIBUTE,
				context, component, getContentType());
		ComponentUtils.setBooleanProperty(BinaryFile.PROMPT_ATTRIBUTE, context,
				component, getPrompt());
		super.setProperties(component);
	}

	/**
	 * Gets the value.
	 * 
	 * @param value
	 *            The value to set.
	 */
	public void setValue(final String value) {
		this.value = value;
	}
}
