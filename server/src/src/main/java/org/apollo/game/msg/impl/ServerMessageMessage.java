package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message which is sent to the client with a server-side message.
 *
 * @author Graham
 */
public final class ServerMessageMessage implements Message {

	/**
	 * The message.
	 */
	private final String message;

	/**
	 * Creates the {@link ServerMessageMessage}.
	 *
	 * @param message The message.
	 */
	public ServerMessageMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

}