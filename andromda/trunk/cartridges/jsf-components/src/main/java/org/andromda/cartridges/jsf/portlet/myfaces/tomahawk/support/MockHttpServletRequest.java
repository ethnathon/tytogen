package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
public class MockHttpServletRequest implements HttpServletRequest {
	/**
	 * @see javax.servlet.ServletRequest#getAttribute(String)
	 */
	@Override
	public Object getAttribute(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getAttributeNames()
	 */
	@Override
	public Enumeration getAttributeNames() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getAuthType()
	 */
	@Override
	public String getAuthType() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getCharacterEncoding()
	 */
	@Override
	public String getCharacterEncoding() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getContentLength()
	 */
	@Override
	public int getContentLength() {
		return 0;
	}

	/**
	 * @see javax.servlet.ServletRequest#getContentType()
	 */
	@Override
	public String getContentType() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getContextPath()
	 */
	@Override
	public String getContextPath() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getCookies()
	 */
	@Override
	public Cookie[] getCookies() {
		return new Cookie[0];
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getDateHeader(String)
	 */
	@Override
	public long getDateHeader(final String arg0) {
		return 0;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeader(String)
	 */
	@Override
	public String getHeader(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
	 */
	@Override
	public Enumeration getHeaderNames() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeaders(String)
	 */
	@Override
	public Enumeration getHeaders(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getIntHeader(String)
	 */
	@Override
	public int getIntHeader(final String arg0) {
		return 0;
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocalAddr()
	 */
	@Override
	public String getLocalAddr() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocales()
	 */
	@Override
	public Enumeration getLocales() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getLocalPort()
	 */
	@Override
	public int getLocalPort() {
		return 0;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getMethod()
	 */
	@Override
	public String getMethod() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameter(String)
	 */
	@Override
	public String getParameter(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameterMap()
	 */
	@Override
	public Map getParameterMap() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameterNames()
	 */
	@Override
	public Enumeration getParameterNames() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getParameterValues(String)
	 */
	@Override
	public String[] getParameterValues(final String arg0) {
		return new String[0];
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getPathInfo()
	 */
	@Override
	public String getPathInfo() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getPathTranslated()
	 */
	@Override
	public String getPathTranslated() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getProtocol()
	 */
	@Override
	public String getProtocol() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getQueryString()
	 */
	@Override
	public String getQueryString() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getReader()
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getRealPath(String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String getRealPath(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getRemoteAddr()
	 */
	@Override
	public String getRemoteAddr() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getRemoteHost()
	 */
	@Override
	public String getRemoteHost() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getRemotePort()
	 */
	@Override
	public int getRemotePort() {
		return 0;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getRequestDispatcher(String)
	 */
	@Override
	public RequestDispatcher getRequestDispatcher(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
	 */
	@Override
	public String getRequestedSessionId() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRequestURI()
	 */
	@Override
	public String getRequestURI() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getRequestURL()
	 */
	@Override
	public StringBuffer getRequestURL() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getScheme()
	 */
	@Override
	public String getScheme() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getServerName()
	 */
	@Override
	public String getServerName() {
		return null;
	}

	/**
	 * @see javax.servlet.ServletRequest#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return 0;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getServletPath()
	 */
	@Override
	public String getServletPath() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getSession()
	 */
	@Override
	public HttpSession getSession() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
	 */
	@Override
	public HttpSession getSession(final boolean arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromCookie()
	 */
	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
	 * @deprecated
	 */
	@Override
	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromURL()
	 */
	@Override
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
	 */
	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}

	/**
	 * @see javax.servlet.ServletRequest#isSecure()
	 */
	@Override
	public boolean isSecure() {
		return false;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#isUserInRole(String)
	 */
	@Override
	public boolean isUserInRole(final String arg0) {
		return false;
	}

	/**
	 * @see javax.servlet.ServletRequest#removeAttribute(String)
	 */
	@Override
	public void removeAttribute(final String arg0) {
	}

	/**
	 * @see javax.servlet.ServletRequest#setAttribute(String, Object)
	 */
	@Override
	public void setAttribute(final String arg0, final Object arg1) {
	}

	/**
	 * @see javax.servlet.ServletRequest#setCharacterEncoding(String)
	 */
	@Override
	public void setCharacterEncoding(final String arg0)
			throws UnsupportedEncodingException {
	}
}
