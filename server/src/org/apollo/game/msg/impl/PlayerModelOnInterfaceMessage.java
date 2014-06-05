package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * Represents a message which your player model is displayed on an interface.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerModelOnInterfaceMessage extends Message {

    /**
     * The id of the interface to show the model on.
     */
    private final int interfaceId;

    /**
     * Constructs a new {@link PlayerModelOnInterfaceMessage}.
     * 
     * @param interfaceId The interface id.
     */
    public PlayerModelOnInterfaceMessage(int interfaceId) {
	this.interfaceId = interfaceId;
    }

    /**
     * Returns the interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

}