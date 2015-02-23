package org.apollo.util;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.apollo.net.NetworkConstants;

/**
 * A utility class which contains {@link ByteBuffer}-related methods.
 *
 * @author Graham
 */
public final class ByteBufferUtil {

	/**
	 * Reads a 'smart' (either a {@code byte} or {@code short} depending on the
	 * value) from the specified buffer.
	 *
	 * @param buffer The buffer.
	 * @return The 'smart'.
	 */
	public static int readSmart(ByteBuffer buffer) {
		int peek = buffer.get(buffer.position()) & 0xFF;
		if (peek < 128) {
			return buffer.get() & 0xFF;
		}
		return (buffer.getShort() & 0xFFFF) - 32768;
	}

	/**
	 * Reads an unsigned medium from the specified buffer.
	 *
	 * @param buffer The buffer.
	 * @return The medium.
	 */
	public static int readMedium(ByteBuffer buffer) {
		return (buffer.getShort() & 0xFFFF) << 8 | buffer.get() & 0xFF;
	}

	/**
	 * Reads a string from the specified buffer.
	 *
	 * @param buffer The buffer.
	 * @return The string.
	 */
	public static String readString(ByteBuffer buffer) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		for (;;) {
			int read = buffer.get() & 0xFF;
			if (read == NetworkConstants.STRING_TERMINATOR) {
				break;
			}
			os.write(read);
		}

		return new String(os.toByteArray());
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private ByteBufferUtil() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}