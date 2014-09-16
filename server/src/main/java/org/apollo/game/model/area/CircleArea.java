package org.apollo.game.model.area;

import org.apollo.game.model.Position;

/**
 * Represents a circular area as specified by a single, center {@link Position}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author lare96
 */
public final class CircleArea extends Area {

	/**
	 * The center position.
	 */
	private final Position position;

	/**
	 * Constructs a new {@link CircleArea} with the specified center
	 * {@link Position}.
	 *
	 * @param position The center position.
	 */
	public CircleArea(Position position) {
		this.position = position;
	}

	@Override
	public boolean withinArea(int x, int y, int padding) {
		return Math.pow((position.getX() - x), 2) + Math.pow((position.getY() - y), 2) <= Math.pow(padding, 2);
	}

	@Override
	public Position center() {
		return position;
	}

	@Override
	public Position randomPosition(int height) {
		return new Position(position.getX(), position.getY(), height);
	}

}