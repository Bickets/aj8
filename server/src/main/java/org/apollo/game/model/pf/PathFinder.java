package org.apollo.game.model.pf;

import java.util.Deque;

import org.apollo.game.model.GameCharacter;
import org.apollo.game.model.Position;

/**
 * Represents a path finder, used to find a route to a {@link Position} for some
 * {@link GameCharacter}.
 * 
 * <p>
 * This class is a functional interface whoes functional method is
 * {@link #find(Position, Position, Position, int)}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@FunctionalInterface
public interface PathFinder {

	/**
	 * A default method to find a path for the specified {@link GameCharacter}.
	 * 
	 * @param character The character to find the path for.
	 * @param destX The destination's x coordinate.
	 * @param destY The destination's y coordinate.
	 * @return A {@link Deque} of {@link Position steps} to the specified
	 *         destination.
	 */
	default Deque<Position> find(GameCharacter character, int destX, int destY) {
		Position position = character.getPosition();

		int baseLocalX = position.getBaseLocalX();
		int baseLocalY = position.getBaseLocalY();

		int destLocalX = destX - baseLocalX;
		int destLocalY = destY - baseLocalY;

		return find(new Position(baseLocalX, baseLocalY, position.getHeight()), new Position(position.getLocalX(), position.getLocalY()), new Position(destLocalX, destLocalY), character.getSize());
	}

	/**
	 * Attempts to find a path to the specified {@link Position destination}.
	 * 
	 * @param base The local base position.
	 * @param origin The local origin position.
	 * @param destination The local destination position.
	 * @param size The amount of positions the traversee takes up.
	 * @return A {@link Deque} of {@link Position steps} to the specified
	 *         destination.
	 */
	Deque<Position> find(Position base, Position origin, Position destination, int size);

}