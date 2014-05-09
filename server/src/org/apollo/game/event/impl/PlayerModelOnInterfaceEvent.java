package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * Represents an event which your player model is displayed on an interface.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerModelOnInterfaceEvent extends Event {

    /**
     * The id of the interface to show the model on.
     */
    private final int interfaceId;

    /**
     * Constructs a new {@link PlayerModelOnInterfaceEvent}.
     * 
     * @param interfaceId The interface id.
     */
    public PlayerModelOnInterfaceEvent(int interfaceId) {
	this.interfaceId = interfaceId;
    }

    /**
     * Returns the interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

}