package org.andromda.cartridges.jsf.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentBodyTag;

import org.andromda.cartridges.jsf.component.html.HtmlPopupFrame;

/**
 *
 */
public class PopupFrameTag extends UIComponentBodyTag {
	private String value;
	private String accesskey;
	private String dir;
	private String lang;
	private String tabindex;
	private String title;
	private String style;
	private String styleClass;
	private String actionOpen;
	private String actionClose;
	private String immediate;
	private String mouseHorizPos;
	private String mouseVertPos;
	private String styleFrame;
	private String styleClassFrame;
	private String absolute;
	private String center;
	private String height;
	private String width;
	private String scrolling;

	private MethodBinding createMethodBinding(final String valueIn,
			final Class[] args) {
		return FacesContext.getCurrentInstance().getApplication()
				.createMethodBinding(valueIn, args);
	}

	private ValueBinding createValueBinding(final String valueIn) {
		return FacesContext.getCurrentInstance().getApplication()
				.createValueBinding(valueIn);
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	@Override
	public String getComponentType() {
		return HtmlPopupFrame.COMPONENT_TYPE;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	@Override
	public String getRendererType() {
		return HtmlPopupFrame.RENDERER_TYPE;
	}

	/**
	 * @param absoluteIn
	 */
	public void setAbsolute(final String absoluteIn) {
		absolute = absoluteIn;
	}

	/**
	 * @param accesskeyIn
	 */
	public void setAccesskey(final String accesskeyIn) {
		accesskey = accesskeyIn;
	}

	/**
	 * @param actionCloseIn
	 */
	public void setActionClose(final String actionCloseIn) {
		actionClose = actionCloseIn;
	}

	/**
	 * @param actionOpenIn
	 */
	public void setActionOpen(final String actionOpenIn) {
		actionOpen = actionOpenIn;
	}

	/**
	 * @param centerIn
	 */
	public void setCenter(final String centerIn) {
		center = centerIn;
	}

	/**
	 * @param dirIn
	 */
	public void setDir(final String dirIn) {
		dir = dirIn;
	}

	/**
	 * @param heightIn
	 */
	public void setHeight(final String heightIn) {
		height = heightIn;
	}

	/**
	 * @param immediateIn
	 */
	public void setImmediate(final String immediateIn) {
		immediate = immediateIn;
	}

	/**
	 * @param langIn
	 */
	public void setLang(final String langIn) {
		lang = langIn;
	}

	/**
	 * @param mouseHorizPosIn
	 */
	public void setMouseHorizPos(final String mouseHorizPosIn) {
		mouseHorizPos = mouseHorizPosIn;
	}

	/**
	 * @param mouseVertPosIn
	 */
	public void setMouseVertPos(final String mouseVertPosIn) {
		mouseVertPos = mouseVertPosIn;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	@Override
	protected void setProperties(final UIComponent component) {
		super.setProperties(component);
		HtmlPopupFrame command = null;
		try {
			command = (HtmlPopupFrame) component;
		} catch (final ClassCastException cce) {
			throw new IllegalStateException(
					"Component "
							+ component.toString()
							+ " not expected type.  Expected: UICommand.  Perhaps you're missing a tag?");
		}

		if (value != null) {
			if (isValueReference(value)) {
				final ValueBinding binding = createValueBinding(value);
				command.setValueBinding("value", binding);
			} else {
				command.setValue(value);
			}
		}
		if (accesskey != null) {
			if (isValueReference(accesskey)) {
				final ValueBinding binding = createValueBinding(accesskey);
				command.setValueBinding("accesskey", binding);
			} else {
				command.getAttributes().put("accesskey", accesskey);
			}
		}
		if (dir != null) {
			if (isValueReference(dir)) {
				final ValueBinding binding = createValueBinding(dir);
				command.setValueBinding("dir", binding);
			} else {
				command.getAttributes().put("dir", dir);
			}
		}
		if (lang != null) {
			if (isValueReference(lang)) {
				final ValueBinding binding = createValueBinding(lang);
				command.setValueBinding("lang", binding);
			} else {
				command.getAttributes().put("lang", lang);
			}
		}
		if (tabindex != null) {
			if (isValueReference(tabindex)) {
				final ValueBinding binding = createValueBinding(tabindex);
				command.setValueBinding("tabindex", binding);
			} else {
				command.getAttributes().put("tabindex", tabindex);
			}
		}
		if (title != null) {
			if (isValueReference(title)) {
				final ValueBinding binding = createValueBinding(title);
				command.setValueBinding("title", binding);
			} else {
				command.getAttributes().put("title", title);
			}
		}
		if (style != null) {
			if (isValueReference(style)) {
				final ValueBinding binding = createValueBinding(style);
				command.setValueBinding("style", binding);
			} else {
				command.getAttributes().put("style", style);
			}
		}
		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				final ValueBinding binding = createValueBinding(styleClass);
				command.setValueBinding("styleClass", binding);
			} else {
				command.getAttributes().put("styleClass", styleClass);
			}
		}
		if (absolute != null) {
			if (isValueReference(absolute)) {
				final ValueBinding binding = createValueBinding(absolute);
				command.setValueBinding("absolute", binding);
			} else {
				command.getAttributes().put("absolute", absolute);
			}
		}
		if (actionClose != null) {
			if (isValueReference(actionClose)) {
				final MethodBinding binding = createMethodBinding(actionClose,
						new Class[0]);
				command.setActionClose(binding);
			} else {
				throw new IllegalStateException("Invalid actionClose."
						+ actionClose);
			}
		}
		if (actionOpen != null) {
			if (isValueReference(actionOpen)) {
				final MethodBinding binding = createMethodBinding(actionOpen,
						new Class[0]);
				command.setActionOpen(binding);
			} else {
				throw new IllegalStateException("Invalid actionOpen."
						+ actionOpen);
			}
		}
		if (center != null) {
			if (isValueReference(center)) {
				final ValueBinding binding = createValueBinding(center);
				command.setValueBinding("center", binding);
			} else {
				command.getAttributes().put("center", center);
			}
		}
		if (immediate != null) {
			if (isValueReference(immediate)) {
				final ValueBinding binding = createValueBinding(immediate);
				command.setValueBinding("immediate", binding);
			} else {
				command.getAttributes().put("immediate", immediate);
			}
		}
		if (height != null) {
			if (isValueReference(height)) {
				final ValueBinding binding = createValueBinding(height);
				command.setValueBinding("height", binding);
			} else {
				command.getAttributes().put("height", height);
			}
		}
		if (width != null) {
			if (isValueReference(width)) {
				final ValueBinding binding = createValueBinding(width);
				command.setValueBinding("width", binding);
			} else {
				command.getAttributes().put("width", width);
			}
		}
		if (mouseHorizPos != null) {
			if (isValueReference(mouseHorizPos)) {
				final ValueBinding binding = createValueBinding(mouseHorizPos);
				command.setValueBinding("mouseHorizPos", binding);
			} else {
				command.getAttributes().put("mouseHorizPos", mouseHorizPos);
			}
		}
		if (mouseVertPos != null) {
			if (isValueReference(mouseVertPos)) {
				final ValueBinding binding = createValueBinding(mouseVertPos);
				command.setValueBinding("mouseVertPos", binding);
			} else {
				command.getAttributes().put("mouseVertPos", mouseVertPos);
			}
		}
		if (styleClassFrame != null) {
			if (isValueReference(styleClassFrame)) {
				final ValueBinding binding = createValueBinding(styleClassFrame);
				command.setValueBinding("styleClassFrame", binding);
			} else {
				command.getAttributes().put("styleClassFrame", styleClassFrame);
			}
		}
		if (styleFrame != null) {
			if (isValueReference(styleFrame)) {
				final ValueBinding binding = createValueBinding(styleFrame);
				command.setValueBinding("styleFrame", binding);
			} else {
				command.getAttributes().put("styleFrame", styleFrame);
			}
		}
		if (scrolling != null) {
			if (isValueReference(scrolling)) {
				final ValueBinding binding = createValueBinding(scrolling);
				command.setValueBinding("scrolling", binding);
			} else {
				command.getAttributes().put("scrolling", scrolling);
			}
		}
	}

	/**
	 * @param scrollingIn
	 */
	public void setScrolling(final String scrollingIn) {
		scrolling = scrollingIn;
	}

	/**
	 * @param styleIn
	 */
	public void setStyle(final String styleIn) {
		style = styleIn;
	}

	/**
	 * @param styleClassIn
	 */
	public void setStyleClass(final String styleClassIn) {
		styleClass = styleClassIn;
	}

	/**
	 * @param styleClassFrameIn
	 */
	public void setStyleClassFrame(final String styleClassFrameIn) {
		styleClassFrame = styleClassFrameIn;
	}

	/**
	 * @param styleFrameIn
	 */
	public void setStyleFrame(final String styleFrameIn) {
		styleFrame = styleFrameIn;
	}

	/**
	 * @param tabindexIn
	 */
	public void setTabindex(final String tabindexIn) {
		tabindex = tabindexIn;
	}

	/**
	 * @param titleIn
	 */
	public void setTitle(final String titleIn) {
		title = titleIn;
	}

	/**
	 * @param valueIn
	 */
	public void setValue(final String valueIn) {
		value = valueIn;
	}

	/**
	 * @param widthIn
	 */
	public void setWidth(final String widthIn) {
		width = widthIn;
	}
}