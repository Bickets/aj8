
package org.apollo.game;

/**
 * Contains game-related constants.
 * @author Graham
 */
public final class GameConstants
{

	/**
	 * The version number of this event translator.
	 */
	public static final int VERSION = 317; // XXX: Better place?

	/**
	 * The delay between consecutive pulses, in milliseconds.
	 */
	public static final int PULSE_DELAY = 600;

	/**
	 * The maximum events per pulse per session.
	 */
	public static final int EVENTS_PER_PULSE = 10;


	/**
	 * Default private constructor to prevent instantiation by other classes.
	 */
	private GameConstants()
	{

	}

}
