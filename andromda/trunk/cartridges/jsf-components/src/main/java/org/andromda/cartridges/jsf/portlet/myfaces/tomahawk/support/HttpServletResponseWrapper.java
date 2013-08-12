package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is a dummy HttpServletResponse.
 * 
 * @author <a href="mailto:shinsuke@yahoo.co.jp">Shinsuke Sugaya</a>
 */
public class HttpServletResponseWrapper implements HttpServletResponse,
		RenderResponse {
	private final RenderResponse renderResponse;

	/**
	 * @param renderResponse
	 */
	public HttpServletResponseWrapper(final RenderResponse renderResponse) {
		this.renderResponse = renderResponse;
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
	 */
	@Override
	public void addCookie(final Cookie arg0) {

	}

	/**
	 * Returns writer to which MyFaces' AddResource stores elements.
	 * 
	 * @return writer which has elements, such as &lt;script&gt; and
	 *         &lt;link&gt; public StringWriter getStringWriter() { return
	 *         (StringWriter) writer; }
	 */

	/**
	 * @see javax.servlet.http.HttpServletResponse#addDateHeader(String, long)
	 */
	@Override
	public void addDateHeader(final String arg0, final long arg1) {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#addHeader(String, String)
	 */
	@Override
	public void addHeader(final String arg0, final String arg1) {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#addIntHeader(String, int)
	 */
	@Override
	public void addIntHeader(final String arg0, final int arg1) {

	}

	/**
	 * @see javax.portlet.PortletResponse#addProperty(String, String)
	 */
	@Override
	public void addProperty(final String arg0, final String arg1) {
		renderResponse.addProperty(arg0, arg1);
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#containsHeader(String)
	 */
	@Override
	public boolean containsHeader(final String arg0) {
		return false;
	}

	/**
	 * @see javax.portlet.RenderResponse#createActionURL()
	 */
	@Override
	public PortletURL createActionURL() {
		return renderResponse.createActionURL();
	}

	/**
	 * @see javax.portlet.RenderResponse#createRenderURL()
	 */
	@Override
	public PortletURL createRenderURL() {
		return renderResponse.createRenderURL();
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String encodeRedirectUrl(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(String)
	 */
	@Override
	public String encodeRedirectURL(final String arg0) {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#encodeUrl(String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String encodeUrl(final String arg0) {
		if (renderResponse != null) {
			return renderResponse.encodeURL(arg0);
		}
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#encodeURL(String)
	 */
	@Override
	public String encodeURL(final String arg0) {
		return renderResponse.encodeURL(arg0);
	}

	/**
	 * @see javax.servlet.ServletResponse#flushBuffer()
	 */
	@Override
	public void flushBuffer() throws IOException {
		if (renderResponse != null) {
			renderResponse.flushBuffer();
		}
	}

	/**
	 * @see javax.servlet.ServletResponse#getBufferSize()
	 */
	@Override
	public int getBufferSize() {
		if (renderResponse != null) {
			return renderResponse.getBufferSize();
		}
		return 0;
	}

	/**
	 * @see javax.servlet.ServletResponse#getCharacterEncoding()
	 */
	@Override
	public String getCharacterEncoding() {
		if (renderResponse != null) {
			return renderResponse.getCharacterEncoding();
		}
		return null;
	}

	/**
	 * @see javax.servlet.ServletResponse#getContentType()
	 */
	@Override
	public String getContentType() {
		if (renderResponse != null) {
			return renderResponse.getContentType();
		}
		return null;
	}

	/**
	 * @see javax.servlet.ServletResponse#getLocale()
	 */
	@Override
	public Locale getLocale() {
		if (renderResponse != null) {
			return renderResponse.getLocale();
		}
		return null;
	}

	/**
	 * @see javax.portlet.RenderResponse#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return renderResponse.getNamespace();
	}

	/**
	 * @see javax.servlet.ServletResponse#getOutputStream()
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (renderResponse != null) {
			return new ServletOutputStreamWrapper(
					renderResponse.getPortletOutputStream());
		}
		return null;
	}

	/**
	 * @see javax.portlet.RenderResponse#getPortletOutputStream()
	 */
	@Override
	public OutputStream getPortletOutputStream() throws IOException {
		return renderResponse.getPortletOutputStream();
	}

	/**
	 * @return renderResponse
	 */
	public RenderResponse getResponse() {
		return renderResponse;
	}

	/**
	 * @return renderResponse.getWriter()
	 * @throws IOException
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return renderResponse.getWriter();
	}

	/**
	 * @see javax.servlet.ServletResponse#isCommitted()
	 */
	@Override
	public boolean isCommitted() {
		if (renderResponse != null) {
			return renderResponse.isCommitted();
		}
		return false;
	}

	/**
	 * @see javax.servlet.ServletResponse#reset()
	 */
	@Override
	public void reset() {
		if (renderResponse != null) {
			renderResponse.reset();
		}
	}

	/**
	 * @see javax.servlet.ServletResponse#resetBuffer()
	 */
	@Override
	public void resetBuffer() {
		if (renderResponse != null) {
			renderResponse.resetBuffer();
		}

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#sendError(int)
	 */
	@Override
	public void sendError(final int arg0) throws IOException {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#sendError(int, String)
	 */
	@Override
	public void sendError(final int arg0, final String arg1) throws IOException {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#sendRedirect(String)
	 */
	@Override
	public void sendRedirect(final String arg0) throws IOException {

	}

	/**
	 * @see javax.servlet.ServletResponse#setBufferSize(int)
	 */
	@Override
	public void setBufferSize(final int arg0) {
		renderResponse.setBufferSize(arg0);
	}

	/**
	 * @see javax.servlet.ServletResponse#setCharacterEncoding(String)
	 */
	@Override
	public void setCharacterEncoding(final String arg0) {
	}

	/**
	 * @see javax.servlet.ServletResponse#setContentLength(int)
	 */
	@Override
	public void setContentLength(final int arg0) {

	}

	/**
	 * @see javax.servlet.ServletResponse#setContentType(String)
	 */
	@Override
	public void setContentType(final String arg0) {
		if (renderResponse != null) {
			renderResponse.setContentType(arg0);
		}
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#setDateHeader(String, long)
	 */
	@Override
	public void setDateHeader(final String arg0, final long arg1) {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#setHeader(String, String)
	 */
	@Override
	public void setHeader(final String arg0, final String arg1) {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#setIntHeader(String, int)
	 */
	@Override
	public void setIntHeader(final String arg0, final int arg1) {

	}

	/**
	 * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
	 */
	@Override
	public void setLocale(final Locale arg0) {

	}

	/**
	 * @see javax.portlet.PortletResponse#setProperty(String, String)
	 */
	@Override
	public void setProperty(final String arg0, final String arg1) {
		renderResponse.setProperty(arg0, arg1);
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int)
	 */
	@Override
	public void setStatus(final int arg0) {

	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int, String)
	 * @deprecated
	 */
	@Override
	@Deprecated
	public void setStatus(final int arg0, final String arg1) {

	}

	/**
	 * @see javax.portlet.RenderResponse#setTitle(String)
	 */
	@Override
	public void setTitle(final String title) {
		renderResponse.setTitle(title);
	}
}
