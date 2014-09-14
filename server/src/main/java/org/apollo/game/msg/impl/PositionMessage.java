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
	 * The base position.
	 */
	private final Position base;

	/**
	 * The position.
	 */
	private final Position position;

	/**
	 * Constructs a new {@link PositionMessage} with the specified position.
	 *
	 * @param base The base position.
	 * @param position The position.
	 */
	public PositionMessage(Position base, Position position) {
		this.base = base;
		this.position = position;
	}

	/**
	 * Constructs a new {@link PositionMessage} with the specified position.
	 *
	 * @param position The position.
	 */
	public PositionMessage(Position position) {
		this(position, position);
	}

	/**
	 * Returns the base position.
	 */
	public Position getBase() {
		return base;
	}

	/**
	 * Returns this messages position.
	 */
	public Position getPosition() {
		return position;
	}

}