package org.andromda.cartridges.jsf.component;

import java.io.InputStream;
import java.util.Properties;

import javax.faces.component.UIComponentBase;
import javax.faces.el.ValueBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class BinaryFile extends UIComponentBase {
	private static final Log LOGGER = LogFactory.getLog(BinaryFile.class);
	/**
	 * BinaryFile.class.getName()
	 */
	public static final String COMPONENT_TYPE = BinaryFile.class.getName();
	/**
	 * org.andromda.cartridges.jsf.BinaryFile
	 */
	public static final String RENDERER_TYPE = "org.andromda.cartridges.jsf.BinaryFile";

	/**
	 * Stores the name of the attribute that holds the value.
	 */
	public static final String VALUE_ATTRIBUTE = "value";

	/**
	 * Stores the value of this binary file.
	 */
	private Object value;

	/**
	 * Stores the name of the attribute that holds the fileName.
	 */
	public static final String FILE_NAME_ATTRIBUTE = "fileName";

	/**
	 * The name of the file to render.
	 */
	private String fileName;

	/**
	 * The name of the attribute that stores the content type.
	 */
	public static final String CONTENT_TYPE_ATTRIBUTE = "contentType";

	/**
	 * The content type to use when rendering the file.
	 */
	private String contentType;

	/**
	 * The name of the attribute that stores the encoding.
	 */
	public static final String ENCODING_TYPE_ATTRIBUTE = "encoding";

	/**
	 * The encoding to use when rendering the file.
	 */
	private String encoding;

	/**
	 * Whether or not we should be prompted to save the file when its rendered.
	 */
	public static final String PROMPT_ATTRIBUTE = "prompt";

	/**
	 * Stores the 'prompt' value.
	 */
	private Boolean prompt;

	/**
	 * Stores the default content types.
	 */
	static final Properties contentTypes = new Properties();

	/**
	 * Load up the default content types
	 */
	static {
		final String fileName = "contenttypes.properties";
		final InputStream stream = BinaryFile.class
				.getResourceAsStream(fileName);
		if (stream == null) {
			LOGGER.error("Could not load file from '" + fileName + '\'');
		}
		try {
			contentTypes.load(stream);
		} catch (final Throwable throwable) {
			LOGGER.error(throwable);
		}
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (final Throwable throwable) {
			// - ignore
		}
	}

	/**
     *
     */
	public BinaryFile() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * Gets the explicit content type to render the file in.
	 * 
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		if (contentType == null) {
			final ValueBinding binding = getValueBinding(CONTENT_TYPE_ATTRIBUTE);
			if (binding != null) {
				contentType = (String) binding.getValue(getFacesContext());
			}

			// - if the content type is still null, lets guess
			if (contentType == null) {
				final String fileName = getFileName();
				if (StringUtils.isNotBlank(fileName)) {
					final int lastDotIndex = fileName.lastIndexOf('.');
					if (lastDotIndex != -1) {
						final String extension = fileName.substring(
								lastDotIndex, fileName.length());
						contentType = contentTypes.getProperty(extension);
					}
				}
			}
		}
		return contentType;
	}

	/**
	 * Gets the encoding to render the file in.
	 * 
	 * @return Returns the encoding.
	 */
	public String getEncoding() {
		if (encoding == null) {
			final ValueBinding binding = getValueBinding(ENCODING_TYPE_ATTRIBUTE);
			if (binding != null) {
				encoding = (String) binding.getValue(getFacesContext());
			}
		}
		return encoding;
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return RENDERER_TYPE;
	}

	/**
	 * Gets the file name for rending the binary file.
	 * 
	 * @return the name of the file to render.
	 */
	public String getFileName() {
		if (fileName == null) {
			final ValueBinding binding = getValueBinding(FILE_NAME_ATTRIBUTE);
			if (binding != null) {
				fileName = (String) binding.getValue(getFacesContext());
			}
		}
		return fileName;
	}

	/**
	 * Gets the current value of this binary file.
	 * 
	 * @return the value of this binary file.
	 */
	public Object getValue() {
		if (value == null) {
			final ValueBinding binding = getValueBinding(VALUE_ATTRIBUTE);
			if (binding != null) {
				value = binding.getValue(getFacesContext());
			}
		}
		return value;
	}

	/**
	 * Gets whether or not the prompt should be rendered.
	 * 
	 * @return Returns the prompt.
	 */
	public boolean isPrompt() {
		if (prompt == null) {
			final ValueBinding binding = getValueBinding(CONTENT_TYPE_ATTRIBUTE);
			if (binding != null) {
				prompt = (Boolean) binding.getValue(getFacesContext());
			}
		}
		return prompt != null ? prompt.booleanValue() : false;
	}

	/**
	 * Sets the explicit content type in which to render the file.
	 * 
	 * @param contentType
	 *            The contentType to set.
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Sets the explicit encoding used to render the file.
	 * 
	 * @param encoding
	 *            The encoding to set.
	 */
	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Sets the file name for this component.
	 * 
	 * @param fileName
	 *            the name of the binary file to be rendered.
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Sets whether or not the prompt should be rendered.
	 * 
	 * @param prompt
	 *            The prompt to set.
	 */
	public void setPrompt(final boolean prompt) {
		this.prompt = Boolean.valueOf(prompt);
	}
}
