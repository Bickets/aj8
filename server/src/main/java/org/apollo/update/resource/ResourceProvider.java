package org.apollo.update.resource;

import java.io.IOException;

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
     * Gets a resource by its path.
     *
     * @param path The path.
     * @return The resource, or {@code null} if it doesn't exist.
     * @throws IOException if an I/O error occurs.
     */
    byte[] get(String path) throws IOException;

}