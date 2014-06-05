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
     * Default private constructor to prevent instantiation by other classes.
     */
    private GameConstants() {

    }

}
