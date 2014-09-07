package org.apollo.game.model;

/**
 * Holds world-related constants.
 *
 * @author Graham
 */
public final class WorldConstants {

	/**
	 * The maximum number of mobs.
	 */
	public static final int MAXIMUM_MOBS = 2048;

	/**
	 * The maximum number of players.
	 */
	public static final int MAXIMUM_PLAYERS = 2000;

	/**
	 * The maximum number of game objects.
	 */
	public static final int MAXIMUM_GAME_OBJECTS = 2000;

	/**
	 * The maximum number of ground items.
	 */
	public static final int MAXIMUM_GROUND_ITEMS = 2000;

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws InstantiationError If this class is instantiated within itself.
	 */
	private WorldConstants() {
		throw new InstantiationError("constant-container classes may not be instantiated.");
	}

}