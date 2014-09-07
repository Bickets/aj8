package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * Sent whenever the camera angle is changed.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class CameraMovementMessage implements Message {

    /**
     * The roll of the camera.
     */
    private final int roll;

    /**
     * The pitch of the camera.
     */
    private final int pitch;

    /**
     * Constructs a new {@link CameraMovementMessage} with the specified roll
     * and pitch.
     *
     * @param roll The roll of the camera.
     * @param pitch The pitch of the camera.
     */
    public CameraMovementMessage(int roll, int pitch) {
	this.roll = roll;
	this.pitch = pitch;
    }

    /**
     * Returns the roll of the camera.
     */
    public int getRoll() {
	return roll;
    }

    /**
     * Returns the pitch of the camrea.
     */
    public int getPitch() {
	return pitch;
    }

}