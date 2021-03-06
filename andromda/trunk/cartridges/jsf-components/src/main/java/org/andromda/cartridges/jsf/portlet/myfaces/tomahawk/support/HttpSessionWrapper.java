package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * This class is a dummy HttpSessionWrapper.
 * 
 * @author <a href="mailto:shinsuke@yahoo.co.jp">Shinsuke Sugaya</a>
 */
@SuppressWarnings("deprecation")
public class HttpSessionWrapper implements HttpSession {
	private final PortletSession portletSession;
	private final PortletContext portletContext;

	/**
	 * @param portletSession
	 * @param portletContext
	 */
	public HttpSessionWrapper(final PortletSession portletSession,
			final PortletContext portletContext) {
		this.portletSession = portletSession;
		this.portletContext = portletContext;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getAttribute(String)
	 */
	@Override
	public Object getAttribute(final String arg0) {
		return portletSession.getAttribute(arg0);
	}

	/**
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	@Override
	public Enumeration getAttributeNames() {
		return portletSession.getAttributeNames();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	@Override
	public long getCreationTime() {
		return portletSession.getCreationTime();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	@Override
	public String getId() {
		return portletSession.getId();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	@Override
	public long getLastAccessedTime() {
		return portletSession.getLastAccessedTime();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	@Override
	public int getMaxInactiveInterval() {
		return portletSession.getMaxInactiveInterval();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		return new ServletContextWrapper(portletContext);
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public HttpSessionContext getSessionContext() {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getValue(String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Object getValue(final String arg0) {
		return portletSession.getAttribute(arg0);
	}

	/**
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String[] getValueNames() {
		final List objs = new ArrayList();
		for (final Enumeration e = portletSession.getAttributeNames(); e
				.hasMoreElements();) {
			final String key = (String) e.nextElement();
			objs.add(key);
		}
		final String[] values = new String[objs.size()];
		for (int i = 0; i < objs.size(); i++) {
			values[i] = (String) objs.get(i);
		}
		return values;
	}

	/**
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	@Override
	public void invalidate() {
		portletSession.invalidate();
	}

	/**
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	@Override
	public boolean isNew() {
		return portletSession.isNew();
	}

	/**
	 * @see javax.servlet.http.HttpSession#putValue(String, Object)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public void putValue(final String arg0, final Object arg1) {
		portletSession.setAttribute(arg0, arg1);
	}

	/**
	 * @see javax.servlet.http.HttpSession#removeAttribute(String)
	 */
	@Override
	public void removeAttribute(final String arg0) {
		portletSession.removeAttribute(arg0);
	}

	/**
	 * @see javax.servlet.http.HttpSession#removeValue(String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public void removeValue(final String arg0) {
		portletSession.removeAttribute(arg0);
	}

	/**
	 * @see javax.servlet.http.HttpSession#setAttribute(String, Object)
	 */
	@Override
	public void setAttribute(final String arg0, final Object arg1) {
		portletSession.setAttribute(arg0, arg1);
	}

	/**
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	@Override
	public void setMaxInactiveInterval(final int arg0) {
		portletSession.setMaxInactiveInterval(arg0);
	}
}
