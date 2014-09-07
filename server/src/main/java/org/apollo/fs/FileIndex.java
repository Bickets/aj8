package org.apollo.fs;

import java.io.IOException;

/**
 * Represents a file within an archive in this file system.
 *
 * <p>
 * This is a functional interface whose functional method is {@link #get(int)}
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
@FunctionalInterface
public interface FileIndex {

	/**
	 * Gets the data within this file index.
	 *
	 * @param file The file to get the data from.
	 * @return The data from the specified file.
	 * @throws IOException If some I/O exception occurs.
	 */
	byte[] get(int file) throws IOException;

}