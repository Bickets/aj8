package org.apollo.game.model;

/**
 * Contains constants related to {@link Player}'s
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerConstants {

	/**
	 * The default spawn position.
	 */
	public static final Position SPAWN_POSITION = new Position(3222, 3222);

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private PlayerConstants() {
		throw new UnsupportedOperationException("constant-container classes may not be instantiated.");
	}

}