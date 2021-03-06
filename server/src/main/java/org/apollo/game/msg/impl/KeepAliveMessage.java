package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message which is periodically sent by the client to keep a connection
 * alive.
 *
 * @author Graham
 */
public final class KeepAliveMessage implements Message {

	/**
	 * The time this message was created.
	 */
	private final long createdAt;

	/**
	 * Creates the keep alive message.
	 */
	public KeepAliveMessage() {
		createdAt = System.currentTimeMillis();
	}

	/**
	 * Gets the time when this message was created.
	 *
	 * @return The time when this message was created.
	 */
	public long getCreatedAt() {
		return createdAt;
	}

}