package com.runescape.tools;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Static utilities used for assisting with files.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class FileUtilities {

    /**
     * Reads the specified {@link File} and returns its contents represented as
     * a {@code byte[]}.
     *
     * @param file The file to read.
     * @return The {@code byte[]} representation of the file.
     * @throws IOException If some I/O exception occurs.
     */
    protected static byte[] getBytesFromFile(File file) throws IOException {
	byte[] buffer = new byte[(int) file.length()];
	try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
	    dis.readFully(buffer);
	    return buffer;
	}
    }

}