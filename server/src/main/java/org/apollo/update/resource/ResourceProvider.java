package org.apollo.update.resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * A class which provides resources.
 *
 * @author Graham
 */
public interface ResourceProvider {

	/**
	 * Checks that this provider can fulfill a request to the specified
	 * resource.
	 *
	 * @param path The path to the resource, e.g. {@code /crc}.
	 * @return {@code true} if the provider can fulfill a request to the
	 *         resource, {@code false} otherwise.
	 * @throws IOException if an I/O error occurs.
	 */
	boolean accept(String path) throws IOException;

	/**
	 * Returns a {@link ByteBuffer} resource wrapped within an {@link Optional}.
	 *
	 * @param path The path.
	 * @return The resource or {@link Optional#empty Optional#empty} if it does
	 *         not exist.
	 * @throws IOException If some I/O exception occurs.
	 */
	Optional<ByteBuffer> get(String path) throws IOException;

}