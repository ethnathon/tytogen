package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 */
public class ServletContextWrapper implements ServletContext {
	private final PortletContext portletContext;

	/**
	 * @param portletContext
	 */
	public ServletContextWrapper(final PortletContext portletContext) {
		this.portletContext = portletContext;
	}

	/**
	 * @see javax.servlet.ServletContext#getAttribute(String)
	 */
	@Override
	public Object getAttribute(final String arg0) {
		return portletContext.getAttribute(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getAttributeNames()
	 */
	@Override
	public Enumeration getAttributeNames() {
		return portletContext.getAttributeNames();
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.ServletContext#getContext(String)
	 */
	@Override
	public ServletContext getContext(final String arg0) {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#getContextPath()
	 */
	@Override
	public String getContextPath() {
		return portletContext.getPortletContextName();
	}

	/**
	 * @see javax.servlet.ServletContext#getInitParameter(String)
	 */
	@Override
	public String getInitParameter(final String arg0) {
		return portletContext.getInitParameter(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getInitParameterNames()
	 */
	@Override
	public Enumeration getInitParameterNames() {
		return portletContext.getInitParameterNames();
	}

	/**
	 * @see javax.servlet.ServletContext#getMajorVersion()
	 */
	@Override
	public int getMajorVersion() {
		return portletContext.getMajorVersion();
	}

	/**
	 * @see javax.servlet.ServletContext#getMimeType(String)
	 */
	@Override
	public String getMimeType(final String arg0) {
		return portletContext.getMimeType(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getMinorVersion()
	 */
	@Override
	public int getMinorVersion() {
		return portletContext.getMinorVersion();
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.ServletContext#getNamedDispatcher(String)
	 */
	@Override
	public RequestDispatcher getNamedDispatcher(final String arg0) {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#getRealPath(String)
	 */
	@Override
	public String getRealPath(final String arg0) {
		return portletContext.getRealPath(arg0);
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.ServletContext#getRequestDispatcher(String)
	 */
	@Override
	public RequestDispatcher getRequestDispatcher(final String arg0) {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#getResource(String)
	 */
	@Override
	public URL getResource(final String arg0) throws MalformedURLException {
		return portletContext.getResource(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getResourceAsStream(String)
	 */
	@Override
	public InputStream getResourceAsStream(final String arg0) {
		return portletContext.getResourceAsStream(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getResourcePaths(String)
	 */
	@Override
	public Set getResourcePaths(final String arg0) {
		return portletContext.getResourcePaths(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getServerInfo()
	 */
	@Override
	public String getServerInfo() {
		return portletContext.getServerInfo();
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.ServletContext#getServlet(String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Servlet getServlet(final String arg0) throws ServletException {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#getServletContextName()
	 */
	@Override
	public String getServletContextName() {
		return portletContext.getPortletContextName();
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.ServletContext#getServletNames()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Enumeration getServletNames() {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * return null
	 * 
	 * @see javax.servlet.ServletContext#getServlets()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public Enumeration getServlets() {
		// TODO Portlet API does not have this method
		return null;
	}

	/**
	 * @see javax.servlet.ServletContext#log(Exception, String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public void log(final Exception arg0, final String arg1) {
		portletContext.log(arg1, new Exception(arg0));
	}

	/**
	 * @see javax.servlet.ServletContext#log(String)
	 */
	@Override
	public void log(final String arg0) {
		portletContext.log(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#log(String, Throwable)
	 */
	@Override
	public void log(final String arg0, final Throwable arg1) {
		portletContext.log(arg0, arg1);
	}

	/**
	 * @see javax.servlet.ServletContext#removeAttribute(String)
	 */
	@Override
	public void removeAttribute(final String arg0) {
		portletContext.removeAttribute(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#setAttribute(String, Object)
	 */
	@Override
	public void setAttribute(final String arg0, final Object arg1) {
		portletContext.setAttribute(arg0, arg1);
	}
}
