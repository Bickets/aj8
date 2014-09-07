package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message which is received whenever the client windows comes in or out of
 * focus.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ClientFocusedMessage implements Message {

    /**
     * A {@code boolean} representing the focus state.
     */
    private final boolean clientWindowFocused;

    /**
     * Constructs a new {@link ClientFocusedMessage} with the specified flag.
     * 
     * @param clientWindowFocused A flag denoting whether or not the client
     *            windows is within focus.
     */
    public ClientFocusedMessage(boolean clientWindowFocused) {
	this.clientWindowFocused = clientWindowFocused;
    }

    /**
     * Returns whether or not the client window is in focus.
     */
    public boolean isClientWindowFocused() {
	return clientWindowFocused;
    }

}