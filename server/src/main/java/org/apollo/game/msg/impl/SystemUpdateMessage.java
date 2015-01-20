package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * Represents the system update message.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class SystemUpdateMessage implements Message {

	/**
	 * The amount of seconds the specified system update is.
	 */
	private final int seconds;

	/**
	 * Constructs a new {@link SystemUpdateMessage} witht he specified amount of
	 * seconds.
	 *
	 * @param seconds The amount of seconds the specified system update is.
	 */
	public SystemUpdateMessage(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * Returns the amount of seconds the specified system update is.
	 */
	public int getSeconds() {
		return seconds;
	}

}