package org.apollo.util;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;

import org.apollo.net.NetworkConstants;

/**
 * A utility class which provides extra {@link ByteBuf}-related methods which
 * deal with data types used in the protocol.
 *
 * @author Graham
 */
public final class ByteBufUtil {

	/**
	 * Reads a string from the specified buffer.
	 *
	 * @param buffer The buffer.
	 * @return The string.
	 */
	public static String readString(ByteBuf buffer) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		for (;;) {
			int read = buffer.readUnsignedByte();
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
	 * @throws InstantiationError If this class is instantiated within itself.
	 */
	private ByteBufUtil() {
		throw new InstantiationError("static-utility classes may not be instantiated.");
	}

}