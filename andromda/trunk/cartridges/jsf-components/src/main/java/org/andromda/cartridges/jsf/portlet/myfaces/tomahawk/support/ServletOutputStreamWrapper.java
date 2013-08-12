package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.servlet.ServletOutputStream;

/**
 * This class is a dummy ServletOutputStream.
 * 
 * @author <a href="mailto:shinsuke@yahoo.co.jp">Shinsuke Sugaya</a>
 * 
 */
public class ServletOutputStreamWrapper extends ServletOutputStream {
	private final OutputStream outputStream;

	/**
	 * @param outputStream
	 */
	public ServletOutputStreamWrapper(final OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		outputStream.close();
	}

	/**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		return outputStream.equals(obj);
	}

	/**
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		outputStream.flush();
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return outputStream.hashCode();
	}

	/**
	 * Writes a <code>boolean</code> value to the client, with no carriage
	 * return-line feed (CRLF) character at the end.
	 * 
	 * @param b
	 *            the <code>boolean</code> value to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void print(final boolean b) throws IOException {
		String msg;
		if (b) {
			msg = "true";
		} else {
			msg = "false";
		}
		print(msg);
	}

	/**
	 * Writes a character to the client, with no carriage return-line feed
	 * (CRLF) at the end.
	 * 
	 * @param c
	 *            the character to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void print(final char c) throws IOException {
		print(String.valueOf(c));
	}

	/**
	 * 
	 * Writes a <code>double</code> value to the client, with no carriage
	 * return-line feed (CRLF) at the end.
	 * 
	 * @param d
	 *            the <code>double</code> value to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 */
	@Override
	public void print(final double d) throws IOException {
		print(String.valueOf(d));
	}

	/**
	 * 
	 * Writes a <code>float</code> value to the client, with no carriage
	 * return-line feed (CRLF) at the end.
	 * 
	 * @param f
	 *            the <code>float</code> value to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 */
	@Override
	public void print(final float f) throws IOException {
		print(String.valueOf(f));
	}

	/**
	 * 
	 * Writes an int to the client, with no carriage return-line feed (CRLF) at
	 * the end.
	 * 
	 * @param i
	 *            the int to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void print(final int i) throws IOException {
		print(String.valueOf(i));
	}

	/**
	 * 
	 * Writes a <code>long</code> value to the client, with no carriage
	 * return-line feed (CRLF) at the end.
	 * 
	 * @param l
	 *            the <code>long</code> value to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void print(final long l) throws IOException {
		print(String.valueOf(l));
	}

	/**
	 * Writes a <code>String</code> to the client, without a carriage
	 * return-line feed (CRLF) character at the end.
	 * 
	 * 
	 * @param s
	 *            the <code>String</code> to send to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void print(String s) throws IOException {
		if (s == null) {
			s = "null";
		}
		final int len = s.length();
		for (int i = 0; i < len; i++) {
			final char c = s.charAt(i);

			//
			// XXX NOTE: This is clearly incorrect for many strings,
			// but is the only consistent approach within the current
			// servlet framework. It must suffice until servlet output
			// streams properly encode their output.
			//
			if ((c & 0xff00) != 0) { // high order byte must be zero
				String errMsg = "Not an ISO 8859-1 character: {0}";
				final Object[] errArgs = new Object[1];
				errArgs[0] = new Character(c);
				errMsg = MessageFormat.format(errMsg, errArgs);
				throw new CharConversionException(errMsg);
			}
			write(c);
		}
	}

	/**
	 * Writes a carriage return-line feed (CRLF) to the client.
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 */
	@Override
	public void println() throws IOException {
		print("\r\n");
	}

	/**
	 * 
	 * Writes a <code>boolean</code> value to the client, followed by a carriage
	 * return-line feed (CRLF).
	 * 
	 * @param b
	 *            the <code>boolean</code> value to write to the client
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final boolean b) throws IOException {
		print(b);
		println();
	}

	/**
	 * 
	 * Writes a character to the client, followed by a carriage return-line feed
	 * (CRLF).
	 * 
	 * @param c
	 *            the character to write to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final char c) throws IOException {
		print(c);
		println();
	}

	/**
	 * 
	 * Writes a <code>double</code> value to the client, followed by a carriage
	 * return-line feed (CRLF).
	 * 
	 * 
	 * @param d
	 *            the <code>double</code> value to write to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final double d) throws IOException {
		print(d);
		println();
	}

	/**
	 * 
	 * Writes a <code>float</code> value to the client, followed by a carriage
	 * return-line feed (CRLF).
	 * 
	 * @param f
	 *            the <code>float</code> value to write to the client
	 * 
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final float f) throws IOException {
		print(f);
		println();
	}

	/**
	 * 
	 * Writes an int to the client, followed by a carriage return-line feed
	 * (CRLF) character.
	 * 
	 * 
	 * @param i
	 *            the int to write to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final int i) throws IOException {
		print(i);
		println();
	}

	/**
	 * 
	 * Writes a <code>long</code> value to the client, followed by a carriage
	 * return-line feed (CRLF).
	 * 
	 * 
	 * @param l
	 *            the <code>long</code> value to write to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final long l) throws IOException {
		print(l);
		println();
	}

	/**
	 * Writes a <code>String</code> to the client, followed by a carriage
	 * return-line feed (CRLF).
	 * 
	 * 
	 * @param s
	 *            the <code>String</code> to write to the client
	 * 
	 * @exception IOException
	 *                if an input or output exception occurred
	 * 
	 */
	@Override
	public void println(final String s) throws IOException {
		print(s);
		println();
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return outputStream.toString();
	}

	/**
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(final byte[] b) throws IOException {
		outputStream.write(b);
	}

	/**
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(final byte[] b, final int off, final int len)
			throws IOException {
		outputStream.write(b, off, len);
	}

	/**
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(final int b) throws IOException {
		outputStream.write(b);
	}
}
