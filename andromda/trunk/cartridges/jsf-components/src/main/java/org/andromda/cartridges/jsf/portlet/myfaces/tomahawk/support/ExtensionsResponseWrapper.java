package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.xml.sax.InputSource;

/**
 * @author Sylvain Vieujot (latest modification by $Author: cwbrandon $)
 * @author Chad Brandon
 */
public class ExtensionsResponseWrapper extends HttpServletResponseWrapper
		implements RenderResponse {
	/**
	 * Used in the <code>getOutputStream()</code> method.
	 */
	private class MyServletOutputStream extends ServletOutputStream {
		private final OutputStream outputStream;

		public MyServletOutputStream(final OutputStream outputStream) {
			this.outputStream = outputStream;
		}

		@Override
		public void write(final byte[] bytes) throws IOException {
			outputStream.write(bytes);
		}

		@Override
		public void write(final byte[] bytes, final int off, final int len)
				throws IOException {
			outputStream.write(bytes, off, len);
		}

		@Override
		public void write(final int b) throws IOException {
			outputStream.write(b);
		}
	}

	private ByteArrayOutputStream stream = null;
	private PrintWriter printWriter = null;

	private String contentType;

	private final RenderResponse response;

	/**
	 * @param servletResponse
	 * @param response
	 */
	public ExtensionsResponseWrapper(final HttpServletResponse servletResponse,
			final RenderResponse response) {
		super(servletResponse);
		this.response = response;
		stream = new ByteArrayOutputStream();
	}

	/**
	 * @see javax.portlet.PortletResponse#addProperty(String, String)
	 */
	@Override
	public void addProperty(final String arg0, final String arg1) {
		response.addProperty(arg0, arg1);
	}

	/**
	 * @see javax.portlet.RenderResponse#createActionURL()
	 */
	@Override
	public PortletURL createActionURL() {
		return response.createActionURL();
	}

	/**
	 * @see javax.portlet.RenderResponse#createRenderURL()
	 */
	@Override
	public PortletURL createRenderURL() {
		return response.createRenderURL();
	}

	/**
	 * @see javax.servlet.http.HttpServletResponseWrapper#encodeURL(String)
	 */
	@Override
	public String encodeURL(final String arg0) {
		return response.encodeURL(arg0);
	}

	/**
     *
     */
	public void finishResponse() {
		try {
			if (printWriter != null) {
				printWriter.close();
			} else {
				if (stream != null) {
					stream.close();
				}
			}
		} catch (final IOException e) {
			// Ignore
		}
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#flushBuffer()
	 */
	@Override
	public void flushBuffer() throws IOException {
		stream.flush();
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getBufferSize()
	 */
	@Override
	public int getBufferSize() {
		return response.getBufferSize();
	}

	/**
	 * @return stream.toByteArray()
	 */
	public byte[] getBytes() {
		return stream.toByteArray();
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getCharacterEncoding()
	 */
	@Override
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getContentType()
	 */
	@Override
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return inputSource
	 */
	public InputSource getInputSource() {
		final ByteArrayInputStream bais = new ByteArrayInputStream(
				stream.toByteArray());
		return new InputSource(bais);
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#getLocale()
	 */
	@Override
	public Locale getLocale() {
		return response.getLocale();
	}

	/**
	 * @see javax.portlet.RenderResponse#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return response.getNamespace();
	}

	/**
	 * This method is used by Jetty.
	 * 
	 * @throws IOException
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new MyServletOutputStream(stream);
	}

	/**
	 * @see javax.portlet.RenderResponse#getPortletOutputStream()
	 */
	@Override
	public OutputStream getPortletOutputStream() throws IOException {
		return stream;
	}

	/**
	 * This method is used by Tomcat.
	 */
	@Override
	public PrintWriter getWriter() {
		if (printWriter == null) {
			final OutputStreamWriter streamWriter = new OutputStreamWriter(
					stream, Charset.forName(getCharacterEncoding()));
			printWriter = new PrintWriter(streamWriter, true);
		}
		return printWriter;
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#isCommitted()
	 */
	@Override
	public boolean isCommitted() {
		return response.isCommitted();
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#reset()
	 */
	@Override
	public void reset() {
		response.reset();
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#resetBuffer()
	 */
	@Override
	public void resetBuffer() {
		response.resetBuffer();
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#setBufferSize(int)
	 */
	@Override
	public void setBufferSize(final int bufferSize) {
		response.setBufferSize(bufferSize);
	}

	/**
	 * Prevent content-length being set as the page might be modified.
	 */
	@Override
	public void setContentLength(final int contentLength) {
	}

	/**
	 * @see javax.servlet.ServletResponseWrapper#setContentType(String)
	 */
	@Override
	public void setContentType(final String contentType) {
		super.setContentType(contentType);
		this.contentType = contentType;
	}

	/**
	 * @see javax.portlet.PortletResponse#setProperty(String, String)
	 */
	@Override
	public void setProperty(final String arg0, final String arg1) {
		response.setProperty(arg0, arg1);
	}

	/**
	 * @see javax.portlet.RenderResponse#setTitle(String)
	 */
	@Override
	public void setTitle(final String title) {
		response.setTitle(title);
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return stream.toString(getCharacterEncoding());
		} catch (final UnsupportedEncodingException e) {
			// an attempt to set an invalid character encoding would have caused
			// this exception before
			throw new RuntimeException(
					"Response accepted invalid character encoding "
							+ getCharacterEncoding());
		}
	}
}
