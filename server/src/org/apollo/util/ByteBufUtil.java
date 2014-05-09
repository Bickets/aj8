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
	while (in.isReadable() && ((character = in.readUnsignedByte()) != NetworkConstants.STRING_TERMINATOR)) {
	    bldr.append((char) character);
	}
	return bldr.toString();
    }

    /**
     * Default private constructor to prevent instantiation by other classes.
     */
    private ByteBufUtil() {

    }

}
