package org.apollo.game;

/**
 * Contains game-related constants.
 *
 * @author Graham
 */
public final class GameConstants {

    /**
     * The version number of Apollo.
     */
    public static final int VERSION = 317;

    /**
     * The delay between consecutive pulses, in milliseconds.
     */
    public static final int PULSE_DELAY = 600;

    /**
     * The maximum message per pulse per session.
     */
    public static final int MESSAGES_PER_PULSE = 10;

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private GameConstants() {
	throw new InstantiationError("constant-container classes may not be instantiated.");
    }

}