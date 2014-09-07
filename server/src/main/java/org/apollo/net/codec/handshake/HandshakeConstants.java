package org.apollo.net.codec.handshake;

/**
 * Holds handshake-related constants.
 *
 * @author Graham
 */
public final class HandshakeConstants {

	/**
	 * The id of the game service.
	 */
	public static final int SERVICE_GAME = 14;

	/**
	 * The id of the update service.
	 */
	public static final int SERVICE_UPDATE = 15;

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws InstantiationError If this class is instantiated within itself.
	 */
	private HandshakeConstants() {
		throw new InstantiationError("constant-container classes may not be instantiated.");
	}

}