package org.apollo.net.codec.login;

import org.apollo.security.IsaacRandomPair;
import org.apollo.security.PlayerCredentials;

/**
 * Represents a login request.
 * @author Graham
 */
public final class LoginRequest {

    /**
     * The players credentials.
     */
    private final PlayerCredentials credentials;

    /**
     * The pair of random number generators.
     */
    private final IsaacRandomPair randomPair;

    /**
     * The low memory flag.
     */
    private final boolean lowMemory;

    /**
     * The reconnecting flag.
     */
    private final boolean reconnecting;

    /**
     * The current version.
     */
    private final int currentVersion;

    /**
     * The archive CRCs.
     */
    private final int[] archiveCrcs;

    /**
     * Creates a login request.
     * @param credentials The player credentials.
     * @param randomPair The pair of random number generators.
     * @param lowMemory The low memory flag.
     * @param reconnecting The reconnecting flag.
     * @param currentVersion The current version number.
     * @param archiveCrcs The archive CRCs.
     */
    public LoginRequest(PlayerCredentials credentials, IsaacRandomPair randomPair, boolean lowMemory, boolean reconnecting, int currentVersion, int[] archiveCrcs) {
        this.credentials = credentials;
        this.randomPair = randomPair;
        this.lowMemory = lowMemory;
        this.reconnecting = reconnecting;
        this.currentVersion = currentVersion;
        this.archiveCrcs = archiveCrcs;
    }

    /**
     * Gets the players credentials.
     * @return The players credentials.
     */
    public PlayerCredentials getCredentials() {
        return credentials;
    }

    /**
     * Gets the pair of random number generators.
     * @return The pair of random number generators.
     */
    public IsaacRandomPair getRandomPair() {
        return randomPair;
    }

    /**
     * Checks if this client is in low memory mode.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isLowMemory() {
        return lowMemory;
    }

    /**
     * Checks if this client is reconnecting.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isReconnecting() {
        return reconnecting;
    }

    /**
     * Gets the current version.
     * @return The current version.
     */
    public int getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Gets the archive CRCs.
     * @return The array of archive CRCs.
     */
    public int[] getArchiveCrcs() {
        return archiveCrcs;
    }

}
