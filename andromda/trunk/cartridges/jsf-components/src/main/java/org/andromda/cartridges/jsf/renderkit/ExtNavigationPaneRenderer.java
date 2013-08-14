package org.andromda.cartridges.jsf.renderkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.TableUtils;
import org.apache.myfaces.trinidad.component.UIXCommand;
import org.apache.myfaces.trinidad.component.UIXHierarchy;
import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.logging.TrinidadLogger;
import org.apache.myfaces.trinidad.render.RenderUtils;
import org.apache.myfaces.trinidadinternal.agent.TrinidadAgent;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.AutoSubmitUtils;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.NavigationPaneRenderer;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.SkinSelectors;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.TrinidadRenderingConstants;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.XhtmlUtils;

public class ExtNavigationPaneRenderer extends NavigationPaneRenderer {

	protected PropertyKey hintKey;

	private static final TrinidadLogger _LOG = TrinidadLogger
			.createTrinidadLogger(ExtNavigationPaneRenderer.class);

	static private final Object _EXTRA_SUBMIT_PARAMS_KEY = new Object();

	private void _appendIcon(final FacesContext context,
			final ResponseWriter rw, final String iconUri, final boolean isRtl,
			final RenderingContext arc) throws IOException {
		rw.startElement("img", null);
		rw.writeAttribute("border", "0", null);
		rw.writeAttribute("align", "absmiddle", null);

		if (isPDA(arc)) {
			if (isRtl) {
				rw.writeAttribute("style", "padding-left: 5px;", null);
			} else {
				rw.writeAttribute("style", "padding-right: 5px;", null);
			}
		} else {
			if (isRtl) {
				rw.writeAttribute("style", "padding-left: 5px; float: right;",
						null);
			} else {
				rw.writeAttribute("style", "padding-right: 5px; float: left;",
						null);
			}
		}

		final Application application = context.getApplication();
		final ViewHandler handler = application.getViewHandler();
		final String resolvedIconUri = handler.getResourceURL(context, iconUri);
		renderEncodedResourceURI(context, "src", resolvedIconUri);
		rw.endElement("img");
	}

	protected Map<String, Object> _collectNavItemData(
			final UIXCommand commandChild, final int rowIndex,
			final UIXHierarchy component) {

		final HashMap<String, Object> itemDataMap = new HashMap<String, Object>();
		itemDataMap.put("accessKey",
				_getCommandChildProperty(commandChild, "accessKey"));
		itemDataMap.put("component", commandChild);
		itemDataMap.put("destination",
				_getCommandChildProperty(commandChild, "destination"));
		itemDataMap.put("icon", _getCommandChildProperty(commandChild, "icon"));
		itemDataMap.put("immediate",
				_getCommandChildProperty(commandChild, "immediate"));
		itemDataMap.put("inlineStyle",
				_getCommandChildProperty(commandChild, "inlineStyle"));
		itemDataMap.put("isDisabled",
				_getCommandChildProperty(commandChild, "disabled"));
		itemDataMap.put("partialSubmit",
				_getCommandChildProperty(commandChild, "partialSubmit"));
		itemDataMap.put("shortDesc",
				_getCommandChildProperty(commandChild, "shortDesc"));
		itemDataMap.put("styleClass",
				_getCommandChildProperty(commandChild, "styleClass"));
		itemDataMap.put("targetFrame",
				_getCommandChildProperty(commandChild, "targetFrame"));
		itemDataMap.put("text", _getCommandChildProperty(commandChild, "text"));
		// Store the row index for this iteration so it can be re-set
		// when rendering
		if (rowIndex >= 0) {
			itemDataMap.put("rowIndex", rowIndex);
		}

		return itemDataMap;
	}

	private String _getAutoSubmitScript(final RenderingContext arc,
			final boolean immediate, final boolean partialSubmit) {
		final String id = arc.getCurrentClientId();

		final String extraParams = (String) arc.getProperties().get(
				_EXTRA_SUBMIT_PARAMS_KEY);

		String script;
		if (partialSubmit) {
			script = AutoSubmitUtils.getSubmitScript(arc, id, immediate, false,
					null/* no event */, extraParams, false);
		} else {
			script = AutoSubmitUtils.getFullPageSubmitScript(arc, id,
					immediate, null/* no event */, extraParams, false/*
																	 * return
																	 * false
																	 */);
		}
		return script;
	}

	protected Object _getCommandChildProperty(final UIXCommand commandChild,
			final String propertyName) {
		final FacesBean childFacesBean = commandChild.getFacesBean();
		final FacesBean.Type type = childFacesBean.getType();
		final PropertyKey propertyKey = type.findKey(propertyName);
		if (propertyKey == null) {
			if (_LOG.isSevere()) {
				_LOG.severe("NAVIGATIONLEVELRENDERER_NOT_FOUND_CHILD_PROPERTY",
						propertyName);
			}
			return null;
		} else {
			return childFacesBean.getProperty(propertyKey);
		}
	}

	private void _renderCommandChildren(final FacesContext context,
			final UIXCommand uiComp) throws IOException {
		for (final UIComponent child : uiComp.getChildren()) {
			RenderUtils.encodeRecursive(context, child);
		}
	}

	private void _renderText(final ResponseWriter rw,
			final Map<String, Object> itemData) throws IOException {
		final String text = toString(itemData.get("text"));
		if (text != null) {
			rw.write(text);
		}
	}

	private void _writeCommandChildProperty(final ResponseWriter rw,
			final UIXCommand commandChild, final String propertyName)
			throws IOException {
		rw.writeAttribute(propertyName,
				_getCommandChildProperty(commandChild, propertyName),
				propertyName);
	}

	private void _writeOnclickProperty(final RenderingContext arc,
			final ResponseWriter rw, final UIXCommand commandChild,
			final boolean actionSpecialCase, final boolean immediate,
			final boolean partialSubmit) throws IOException {
		if (actionSpecialCase) {
			final String onclick = (String) _getCommandChildProperty(
					commandChild, "onclick");
			final String script = _getAutoSubmitScript(arc, immediate,
					partialSubmit);
			rw.writeAttribute("onclick",
					XhtmlUtils.getChainedJS(onclick, script, true), "onclick");
		} else // simple case, e.g. (destination != null)
		{
			_writeCommandChildProperty(rw, commandChild, "onclick");
		}
	}

	protected void appendIconAndText2(final FacesContext context,
			final RenderingContext arc, final ResponseWriter rw,
			final String iconUri, final Map<String, Object> itemData,
			final boolean isDisabled, final boolean isRtl, final int level)
			throws IOException {
		if ((iconUri != null) && !isRtl) {
			_appendIcon(context, rw, iconUri, isRtl, arc);
		}
		writeItemLink(context, arc, rw, itemData, isDisabled, level);
		if ((iconUri != null) && isRtl) {
			_appendIcon(context, rw, iconUri, isRtl, arc);
		}
	}

	@Override
	protected void findTypeConstants(final FacesBean.Type type) {
		super.findTypeConstants(type);
		hintKey = type.findKey("hint");
	}

	protected String getHint(final FacesBean bean) {
		final String renderingHint = toString(bean.getProperty(hintKey));
		return renderingHint;
	}

	protected UIComponent getStamp(final UIXHierarchy component) {
		final UIComponent stamp = component.getFacet("nodeStamp");
		return stamp;
	}

	/**
	 * Renders the client ID as both "id" and "name"
	 */
	protected void renderCommandChildId(final FacesContext context,
			final UIXCommand component) throws IOException {
		final String clientId = getClientId(context, component);
		// For links, these are actually URI attributes
		context.getResponseWriter().writeURIAttribute("id", clientId, "id");
		context.getResponseWriter().writeURIAttribute("name", clientId, "id");
	}

	@Override
	protected void renderContent(final FacesContext context,
			final RenderingContext arc, final UIXHierarchy component,
			final FacesBean bean) throws IOException {
		final String renderingHint = getHint(bean);
		if ("list2".equalsIgnoreCase(renderingHint)) {
			renderNewList(context, arc, component, bean);
		} else {
			super.renderContent(context, arc, component, bean);
		}
	}

	protected void renderItem(final FacesContext context,
			final RenderingContext arc, final ResponseWriter rw,
			final Map<String, Object> itemData, final boolean isRtl,
			final int level) throws IOException {

		String inlineStyle = toString(itemData.get("inlineStyle"));
		if(StringUtils.isNotBlank(inlineStyle))
		writeInlineStyles(rw, inlineStyle, "");
		rw.writeAttribute("title", itemData.get("shortDesc"), null);
		final StringBuilder itemStyleClass = new StringBuilder();
		final String userStyleClass = toString(itemData.get("styleClass"));
		if (userStyleClass != null) {
			itemStyleClass.append(userStyleClass);
			itemStyleClass.append(" "); // more style classes are appended below
		}

		// Assign the event handlers:
		final boolean isDisabled = getBooleanFromProperty(itemData
				.get("isDisabled"));

		if (isDisabled) {
			itemStyleClass
					.append(SkinSelectors.AF_NAVIGATION_LEVEL_LIST_INACTIVE_DISABLED_STYLE_CLASS);
		} else {
			itemStyleClass
					.append(SkinSelectors.AF_NAVIGATION_LEVEL_BAR_INACTIVE_ENABLED_STYLE_CLASS);

		}
		renderStyleClass(context, arc, itemStyleClass.toString());

		appendIconAndText2(context, arc, rw, toString(itemData.get("icon")),
				itemData, isDisabled, isRtl, level);

	}

	protected void renderLevel(final FacesContext context,
			final RenderingContext arc, final UIXHierarchy component,
			final UIXCommand navStamp, final ResponseWriter rw, final int level)
			throws IOException {
		final boolean isRtl = arc.getLocaleContext().isRightToLeft();
		final int componentRowCount = component.getRowCount();
		if (componentRowCount != 0) {
			rw.startElement("ul", null);
			rw.writeAttribute("class", "menu_level_" + level, null);
			final int startIndex = component.getFirst();
			final int endIndex = TableUtils.getLast(component, startIndex);

			for (int i = startIndex; i <= endIndex; i++) {
				component.setRowIndex(i);

				if (navStamp.isRendered()) {
					// collect the information needed to render this nav
					// item:
					final Map<String, Object> itemData = _collectNavItemData(
							navStamp, i, component);
					rw.startElement("li", null);
					renderItem(context, arc, rw, itemData, isRtl, level);
					if (component.isContainer()) {
						component.enterContainer();
						ValueBinding vb=component.getValueBinding("item.action");
						System.out.println(vb.getValue(FacesContext.getCurrentInstance()));
						renderLevel(context, arc, component, navStamp, rw,
								level + 1);
						component.exitContainer();
					}
					rw.endElement("li");
				}
			}
			rw.endElement("ul");
		}
	}

	protected void renderLinkStyleClass(final FacesContext context,
			final RenderingContext arc, final int level, Map<String, Object> itemData) throws IOException {
		renderStyleClass(context, arc,
				"menu_element_level_"
						+ level);
	}

	protected void renderNewList(final FacesContext context,
			final RenderingContext arc, final UIXHierarchy component,
			final FacesBean bean) {

		final UIComponent nodeStamp = getStamp(component);
		if (nodeStamp == null) {
			_LOG.severe("ILLEGAL_COMPONENT_HIERARCHY_UIXCOMMAND_EXPECTED");
		} else {
			if (!(nodeStamp instanceof UIXCommand)) {
				_LOG.severe("ILLEGAL_COMPONENT_HIERARCHY_UIXCOMMAND_EXPECTED");
				return;

			}

			final UIXCommand navStamp = (UIXCommand) nodeStamp;

			final Object oldPath = component.getRowKey();

			final ResponseWriter rw = context.getResponseWriter();
			try {
				renderLevel(context, arc, component, navStamp, rw, 0);
			} catch (final IOException e) {
				_LOG.severe("IO Error", e);
			}
			// Restore the old path
			component.setRowKey(oldPath);
		}

	}

	protected void writeItemLink(final FacesContext context,
			final RenderingContext arc, final ResponseWriter rw,
			final Map<String, Object> itemData, final boolean isDisabled,
			final int level) throws IOException {

		final UIXCommand commandChild = (UIXCommand) itemData.get("component");
		if (isDisabled) {
			_renderText(rw, itemData);
			_renderCommandChildren(context, commandChild);
			return;
		}

		final String destination = toResourceUri(context,
				(itemData.get("destination")));
		boolean immediate = false;
		boolean partialSubmit = false;
		if (destination == null) {
			immediate = getBooleanFromProperty(itemData.get("immediate"));
			partialSubmit = getBooleanFromProperty(itemData
					.get("partialSubmit"));
			if (partialSubmit) {
				AutoSubmitUtils.writeDependencies(context, arc);
			}
			final String clientId = commandChild.getClientId(context);
			// Make sure we don't have anything to save
			assert (arc.getCurrentClientId() == null);
			arc.setCurrentClientId(clientId);

			// Find the params up front, and save them off -
			// getOnClick() doesn't have access to the UIComponent
			final String extraParams = AutoSubmitUtils
					.getParameters(commandChild);
			arc.getProperties().put(_EXTRA_SUBMIT_PARAMS_KEY, extraParams);
		}

		final boolean isActive = getBooleanFromProperty(itemData
				.get("isActive"));
		final boolean isDesktop = (arc.getAgent().getType()
				.equals(Agent.TYPE_DESKTOP));

		final boolean nonJavaScriptSubmit = (!supportsScripting(arc))
				&& (destination == null);

		// For non-javascript browsers, we need to render a submit element
		// instead of an anchor tag if the anchor tag doesn't have a destination

		if (nonJavaScriptSubmit) {

			rw.startElement("input", commandChild);

			rw.writeAttribute("type", "submit", null);
			rw.writeAttribute("value", toString(itemData.get("text")), "text");

			final String clientId = getClientId(context, commandChild);
			rw.writeAttribute("id", clientId, "id");

			// For Non-JavaScript browsers, encode the name attribute with the
			// parameter name and value thus it would enable the browsers to
			// include the name of this element in its payLoad if it submits the
			// page.

			rw.writeAttribute(
					"name",
					XhtmlUtils
							.getEncodedParameter(TrinidadRenderingConstants.SOURCE_PARAM)
							+ clientId, null);
			renderLinkStyleClass(context, arc, level, itemData);
			String linkConverter = "border:none;background:inherit;text-decoration:underline;";

			// Few mobile browsers couldn't apply css property for active
			// elements
			// so making it inline
			if (isActive && !isDesktop) {
				linkConverter = linkConverter + "font-weight: bold;";
			}

			writeInlineStyles(rw, null, linkConverter);

		} else {
			rw.startElement("a", commandChild); // linkElement

			// Few mobile browsers couldn't apply css property for active
			// elements
			// so making it inline
			if (isActive && !isDesktop) {
				writeInlineStyles(rw, null, "font-weight: bold;");
			}
			renderCommandChildId(context, commandChild);
			renderLinkStyleClass(context, arc, level, null);
			if (destination == null) {
				rw.writeURIAttribute("href", "#", null); // required for IE to
															// support ":hover"
															// styles
			} else {
				renderEncodedActionURI(context, "href", destination);
				final String targetFrame = toString(itemData.get("targetFrame"));
				if ((targetFrame != null)
						&& !Boolean.FALSE.equals(arc.getAgent()
								.getCapabilities()
								.get(TrinidadAgent.CAP_TARGET))) {
					rw.writeAttribute("target", targetFrame, null);
				}
			}

			// Cannot use super.renderEventHandlers(context, bean); because the
			// wrong
			// property keys would be in use so must do it this way:
			// Also render the events only if the browser supports JavaScript
			if (supportsScripting(arc)) {
				_writeOnclickProperty(arc, rw, commandChild,
						(destination == null), immediate, partialSubmit); // special
																			// for
																			// actions!
				_writeCommandChildProperty(rw, commandChild, "ondblclick");
				_writeCommandChildProperty(rw, commandChild, "onkeydown");
				_writeCommandChildProperty(rw, commandChild, "onkeyup");
				_writeCommandChildProperty(rw, commandChild, "onkeypress");
				_writeCommandChildProperty(rw, commandChild, "onmousedown");
				_writeCommandChildProperty(rw, commandChild, "onmousemove");
				_writeCommandChildProperty(rw, commandChild, "onmouseout");
				_writeCommandChildProperty(rw, commandChild, "onmouseover");
				_writeCommandChildProperty(rw, commandChild, "onmouseup");
			}
		}

		final String accessKey = toString(itemData.get("accessKey"));
		if (!isDisabled && (accessKey != null)) {
			rw.writeAttribute("accessKey", accessKey, null);
		}

		// In the case of HTML basic browsers, we render an input element.
		// Hence,
		// we cannot render any children, so skip calling _renderCommandChildren
		if (nonJavaScriptSubmit) {
			rw.endElement("input");
		} else {
			_renderText(rw, itemData);
			_renderCommandChildren(context, commandChild);
			rw.endElement("a"); // linkElement
		}

		if (destination == null) {
			arc.setCurrentClientId(null);
			arc.getProperties().remove(_EXTRA_SUBMIT_PARAMS_KEY);
		}
	}

}
