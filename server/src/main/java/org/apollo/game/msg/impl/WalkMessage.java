package org.apollo.game.msg.impl;

import org.apollo.game.model.Position;
import org.apollo.game.msg.Message;

/**
 * A message which the client sends to request that the player walks somewhere.
 *
 * @author Graham
 */
public final class WalkMessage extends Message {

    /**
     * The steps.
     */
    private final Position[] steps;

    /**
     * The running flag.
     */
    private final boolean running;

    /**
     * Creates the message.
     *
     * @param steps The steps array.
     * @param running The running flag.
     */
    public WalkMessage(Position[] steps, boolean running) {
	if (steps.length < 0) {
	    throw new IllegalArgumentException("number of steps must not be negative");
	}
	this.steps = steps;
	this.running = running;
    }

    /**
     * Gets the steps array.
     *
     * @return An array of steps.
     */
    public Position[] getSteps() {
	return steps;
    }

    /**
     * Checks if the steps should be running (ctrl+click).
     *
     * @return {@code true} if so, {@code false} otherwise.
     */
    public boolean isRunning() {
	return running;
    }

}