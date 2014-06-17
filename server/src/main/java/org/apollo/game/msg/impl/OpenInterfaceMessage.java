package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message which opens an interface.
 *
 * @author Graham
 */
public final class OpenInterfaceMessage extends Message {

    /**
     * The interface id.
     */
    private final int id;

    /**
     * Creates the message with the specified interface id.
     *
     * @param id The interface id.
     */
    public OpenInterfaceMessage(int id) {
	this.id = id;
    }

    /**
     * Gets the interface id.
     *
     * @return The interface id.
     */
    public int getId() {
	return id;
    }

}
