package org.apollo.game.msg.impl;

import org.apollo.game.model.Appearance;
import org.apollo.game.msg.Message;

/**
 * A message sent by the client when the player modifies their character's
 * design.
 *
 * @author Graham
 */
public final class CharacterDesignMessage extends Message {

    /**
     * The appearance.
     */
    private final Appearance appearance;

    /**
     * Creates the character design message.
     *
     * @param appearance The appearance.
     */
    public CharacterDesignMessage(Appearance appearance) {
	this.appearance = appearance;
    }

    /**
     * Gets the appearance.
     *
     * @return The appearance.
     */
    public Appearance getAppearance() {
	return appearance;
    }

}