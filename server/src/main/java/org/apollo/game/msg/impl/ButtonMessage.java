package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message sent when the client clicks a button.
 * 
 * @author Graham
 */
public final class ButtonMessage extends Message {

    /**
     * The interface id.
     */
    private final int interfaceId;

    /**
     * Creates the button message.
     * 
     * @param interfaceId The interface id.
     */
    public ButtonMessage(int interfaceId) {
	this.interfaceId = interfaceId;
    }

    /**
     * Gets the interface id.
     * 
     * @return The interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

}
