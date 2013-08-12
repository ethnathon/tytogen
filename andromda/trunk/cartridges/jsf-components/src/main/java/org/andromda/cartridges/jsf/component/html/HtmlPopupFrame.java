package org.andromda.cartridges.jsf.component.html;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 *
 */
public class HtmlPopupFrame extends UICommand {
	/**
	 * org.andromda.cartridges.jsf.HtmlPopupFrame
	 */
	public static final String COMPONENT_TYPE = "org.andromda.cartridges.jsf.HtmlPopupFrame";
	/**
	 * org.andromda.cartridges.jsf.Popup
	 */
	public static final String RENDERER_TYPE = "org.andromda.cartridges.jsf.Popup";
	private MethodBinding actionOpen;
	private MethodBinding actionClose;
	private String immediate;
	private String accesskey;
	private String dir;
	private String lang;
	private String tabindex;
	private String title;
	private String mouseHorizPos;
	private String mouseVertPos;
	private String style;
	private String styleClass;
	private String styleFrame;
	private String styleClassFrame;
	private String absolute;
	private String center;
	private String height;
	private String width;
	private String scrolling;

	/**
     *
     */
	public HtmlPopupFrame() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	/**
	 * @return absolute
	 */
	public String getAbsolute() {
		if (null != absolute) {
			return absolute;
		}
		final ValueBinding binding = getValueBinding("absolute");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return accesskey
	 */
	public String getAccesskey() {
		if (null != accesskey) {
			return accesskey;
		}
		final ValueBinding binding = getValueBinding("accesskey");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return actionClose
	 */
	public MethodBinding getActionClose() {
		if (actionClose == null) {
			final ValueBinding binding = getValueBinding("actionClose");
			if (binding != null) {
				actionClose = FacesContext
						.getCurrentInstance()
						.getApplication()
						.createMethodBinding(binding.getExpressionString(),
								new Class[0]);
			}
		}
		return actionClose;
	}

	/**
	 * @return actionOpen
	 */
	public MethodBinding getActionOpen() {
		if (actionOpen == null) {
			final ValueBinding binding = getValueBinding("actionOpen");
			if (binding != null) {
				actionOpen = FacesContext
						.getCurrentInstance()
						.getApplication()
						.createMethodBinding(binding.getExpressionString(),
								new Class[0]);
			}
		}
		return actionOpen;
	}

	/**
	 * @return center
	 */
	public String getCenter() {
		if (null != center) {
			return center;
		}
		final ValueBinding binding = getValueBinding("center");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return getValueBinding("dir").getValue(getFacesContext()
	 */
	public String getDir() {
		if (null != dir) {
			return dir;
		}
		final ValueBinding binding = getValueBinding("dir");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * Returns the component's family. In this case, the component is not
	 * associated with a family, so this method returns null.
	 * 
	 * @return RENDERER_TYPE
	 */
	@Override
	public String getFamily() {
		return RENDERER_TYPE;
	}

	/**
	 * @return height
	 */
	public String getHeight() {
		if (null != height) {
			return height;
		}
		final ValueBinding binding = getValueBinding("height");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}

		return null;
	}

	/**
	 * @return immediate
	 */
	public String getImmediate() {
		if (null != immediate) {
			return immediate;
		}
		final ValueBinding binding = getValueBinding("immediate");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return lang
	 */
	public String getLang() {
		if (null != lang) {
			return lang;
		}
		final ValueBinding binding = getValueBinding("lang");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return mouseHorizPos
	 */
	public String getMouseHorizPos() {
		if (null != mouseHorizPos) {
			return mouseHorizPos;
		}
		final ValueBinding binding = getValueBinding("mouseRelHorizPos");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return mouseVertPos
	 */
	public String getMouseVertPos() {
		if (null != mouseVertPos) {
			return mouseVertPos;
		}
		final ValueBinding binding = getValueBinding("mouseRelVertPos");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return scrolling
	 */
	public String getScrolling() {
		if (null != scrolling) {
			return scrolling;
		}
		final ValueBinding binding = getValueBinding("scrolling");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return style
	 */
	public String getStyle() {
		if (null != style) {
			return style;
		}
		final ValueBinding binding = getValueBinding("style");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return styleClass
	 */
	public String getStyleClass() {
		if (null != styleClass) {
			return styleClass;
		}
		final ValueBinding binding = getValueBinding("styleClass");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return styleClassFrame
	 */
	public String getStyleClassFrame() {
		if (null != styleClassFrame) {
			return styleClassFrame;
		}
		final ValueBinding binding = getValueBinding("styleClassFrame");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return styleFrame
	 */
	public String getStyleFrame() {
		if (null != styleFrame) {
			return styleFrame;
		}
		final ValueBinding binding = getValueBinding("styleFrame");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return tabindex
	 */
	public String getTabindex() {
		if (null != tabindex) {
			return tabindex;
		}
		final ValueBinding binding = getValueBinding("tabindex");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}

		return null;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		if (null != title) {
			return title;
		}
		final ValueBinding binding = getValueBinding("title");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @return width
	 */
	public String getWidth() {
		if (null != width) {
			return width;
		}
		final ValueBinding binding = getValueBinding("width");
		if (binding != null) {
			return (String) binding.getValue(getFacesContext());
		}
		return null;
	}

	/**
	 * @param event
	 */
	public void queueEventImmediate(final FacesEvent event) {
		if (event instanceof ActionEvent) {
			event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
		}
		if (event == null) {
			throw new NullPointerException();
		}
		final UIComponent parent = getParent();
		if (parent == null) {
			throw new IllegalStateException();
		}
		parent.queueEvent(event);
	}

	/**
	 * @param event
	 */
	public void queueEventNormal(final FacesEvent event) {
		if (event instanceof ActionEvent) {
			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
		}
		if (event == null) {
			throw new NullPointerException();
		}
		final UIComponent parent = getParent();
		if (parent == null) {
			throw new IllegalStateException();
		}
		parent.queueEvent(event);
	}

	/**
	 * @see javax.faces.component.UICommand#restoreState(javax.faces.context.FacesContext,
	 *      Object)
	 */
	@Override
	public void restoreState(final FacesContext _context, final Object _state) {
		final Object[] _values = (Object[]) _state;
		super.restoreState(_context, _values[0]);
		accesskey = (String) _values[1];
		lang = (String) _values[2];
		dir = (String) _values[3];
		tabindex = (String) _values[4];
		title = (String) _values[5];
		mouseHorizPos = (String) _values[6];
		mouseVertPos = (String) _values[7];
		style = (String) _values[8];
		styleClass = (String) _values[9];
		styleFrame = (String) _values[10];
		styleClassFrame = (String) _values[11];
		actionOpen = (MethodBinding) restoreAttachedState(_context, _values[12]);
		actionClose = (MethodBinding) restoreAttachedState(_context,
				_values[13]);
		immediate = (String) _values[14];
		absolute = (String) _values[15];
		center = (String) _values[16];
		height = (String) _values[17];
		width = (String) _values[18];
		scrolling = (String) _values[19];
	}

	/**
	 * @see javax.faces.component.UICommand#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(final FacesContext _context) {
		final Object[] _values = new Object[20];
		_values[0] = super.saveState(_context);
		_values[1] = accesskey;
		_values[2] = lang;
		_values[3] = dir;
		_values[4] = tabindex;
		_values[5] = title;
		_values[6] = mouseHorizPos;
		_values[7] = mouseVertPos;
		_values[8] = style;
		_values[9] = styleClass;
		_values[10] = styleFrame;
		_values[11] = styleClassFrame;
		_values[12] = saveAttachedState(_context, actionOpen);
		_values[13] = saveAttachedState(_context, actionClose);
		_values[14] = immediate;
		_values[15] = absolute;
		_values[16] = center;
		_values[17] = height;
		_values[18] = width;
		_values[19] = scrolling;
		return _values;
	}

	/**
	 * @param absolute
	 */
	public void setAbsolute(final String absolute) {
		this.absolute = absolute;
	}

	/**
	 * @param accesskey
	 */
	public void setAccesskey(final String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * @param actionClose
	 */
	public void setActionClose(final MethodBinding actionClose) {
		this.actionClose = actionClose;
	}

	/**
	 * @param actionOpen
	 */
	public void setActionOpen(final MethodBinding actionOpen) {
		this.actionOpen = actionOpen;
	}

	/**
	 * @param center
	 */
	public void setCenter(final String center) {
		this.center = center;
	}

	/**
	 * @param dir
	 */
	public void setDir(final String dir) {
		this.dir = dir;
	}

	/**
	 * @param height
	 */
	public void setHeight(final String height) {
		this.height = height;
	}

	/**
	 * @param immediate
	 */
	public void setImmediate(final String immediate) {
		this.immediate = immediate;
	}

	/**
	 * @param lang
	 */
	public void setLang(final String lang) {
		this.lang = lang;
	}

	/**
	 * @param mouseHorizPos
	 */
	public void setMouseHorizPos(final String mouseHorizPos) {
		this.mouseHorizPos = mouseHorizPos;
	}

	/**
	 * @param mouseVertPos
	 */
	public void setMouseVertPos(final String mouseVertPos) {
		this.mouseVertPos = mouseVertPos;
	}

	/**
	 * @param disableScroll
	 */
	public void setScrolling(final String disableScroll) {
		scrolling = disableScroll;
	}

	/**
	 * @param style
	 */
	public void setStyle(final String style) {
		this.style = style;
	}

	/**
	 * @param styleClass
	 */
	public void setStyleClass(final String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @param styleClassFrame
	 */
	public void setStyleClassFrame(final String styleClassFrame) {
		this.styleClassFrame = styleClassFrame;
	}

	/**
	 * @param styleFrame
	 */
	public void setStyleFrame(final String styleFrame) {
		this.styleFrame = styleFrame;
	}

	/**
	 * @param tabindex
	 */
	public void setTabindex(final String tabindex) {
		this.tabindex = tabindex;
	}

	/**
	 * @param title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @param width
	 */
	public void setWidth(final String width) {
		this.width = width;
	}
}