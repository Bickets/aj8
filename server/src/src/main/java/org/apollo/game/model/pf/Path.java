package org.apollo.game.model.pf;

import java.util.Deque;
import java.util.LinkedList;

import org.apollo.game.model.Position;

/**
 * Represents a queue of positions which represent a path.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Graham
 * @author Hadyn Richard
 */
public final class Path {

	/**
	 * The queue of positions.
	 */
	private final Deque<Position> positions = new LinkedList<>();

	/**
	 * Adds a Position onto the queue.
	 *
	 * @param p The Position to add.
	 */
	public void addFirst(Position p) {
		positions.addFirst(p);
	}

	/**
	 * Adds a position onto the queue.
	 *
	 * @param p The position to add.
	 */
	public void addLast(Position p) {
		positions.addLast(p);
	}

	/**
	 * Peeks at the next tile in the path.
	 *
	 * @return The next tile.
	 */
	public Position peek() {
		return positions.peek();
	}

	/**
	 * Polls a position from the path.
	 *
	 * @return The polled position.
	 */
	public Position poll() {
		return positions.poll();
	}

	/**
	 * Gets if the path is empty.
	 *
	 * @return If the tile deque is empty.
	 */
	public boolean isEmpty() {
		return positions.isEmpty();
	}

	/**
	 * Gets the deque backing this path.
	 *
	 * @return The deque backing this path.
	 */
	public Deque<Position> getPositions() {
		return positions;
	}

}