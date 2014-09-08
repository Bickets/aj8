package org.apollo.game.msg.impl;

import org.apollo.game.model.Position;
import org.apollo.game.msg.Message;

/**
 * A message which sends a normalized {@link Position}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PositionMessage implements Message {

	/**
	 * The position.
	 */
	private final Position position;

	/**
	 * Constructs a new {@link PositionMessage} with the specified position.
	 *
	 * @param position The position.
	 */
	public PositionMessage(Position position) {
		this.position = position;
	}

	/**
	 * Returns this messages position.
	 */
	public Position getPosition() {
		return position;
	}

}