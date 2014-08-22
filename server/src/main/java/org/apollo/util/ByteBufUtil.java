package org.apollo.util;

import io.netty.buffer.ByteBuf;

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
     * @param in The buffer.
     * @return The string.
     */
    public static String readString(ByteBuf in) {
	StringBuilder bldr = new StringBuilder();
	int character;
	while (in.isReadable() && (character = in.readUnsignedByte()) != NetworkConstants.STRING_TERMINATOR) {
	    bldr.append((char) character);
	}
	return bldr.toString();
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