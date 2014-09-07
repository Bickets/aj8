package org.apollo.game.model;

import java.util.LinkedList;
import java.util.List;

import org.apollo.game.model.pf.TraversalMap;

/**
 * Represents a single movement direction.
 *
 * @author Graham
 */
public enum Direction {

	/**
	 * North movement.
	 */
	NORTH(1),

	/**
	 * North east movement.
	 */
	NORTH_EAST(2),

	/**
	 * East movement.
	 */
	EAST(4),

	/**
	 * South east movement.
	 */
	SOUTH_EAST(7),

	/**
	 * South movement.
	 */
	SOUTH(6),

	/**
	 * South west movement.
	 */
	SOUTH_WEST(5),

	/**
	 * West movement.
	 */
	WEST(3),

	/**
	 * North west movement.
	 */
	NORTH_WEST(0),

	/**
	 * No movement.
	 */
	NONE(-1);

	/**
	 * An empty direction array.
	 */
	public static final Direction[] EMPTY_DIRECTION_ARRAY = new Direction[0];

	/**
	 * Checks if the direction represented by the two delta values can connect
	 * two points together in a single direction.
	 *
	 * @param deltaX The difference in X coordinates.
	 * @param deltaY The difference in X coordinates.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public static boolean isConnectable(int deltaX, int deltaY) {
		return Math.abs(deltaX) == Math.abs(deltaY) || deltaX == 0 || deltaY == 0;
	}

	/**
	 * Creates a direction from the differences between X and Y.
	 *
	 * @param deltaX The difference between two X coordinates.
	 * @param deltaY The difference between two Y coordinates.
	 * @return The direction.
	 */
	public static Direction fromDeltas(int deltaX, int deltaY) {
		if (deltaY == 1) {
			if (deltaX == 1) {
				return Direction.NORTH_EAST;
			} else if (deltaX == 0) {
				return Direction.NORTH;
			} else {
				return Direction.NORTH_WEST;
			}
		} else if (deltaY == -1) {
			if (deltaX == 1) {
				return Direction.SOUTH_EAST;
			} else if (deltaX == 0) {
				return Direction.SOUTH;
			} else {
				return Direction.SOUTH_WEST;
			}
		} else {
			if (deltaX == 1) {
				return Direction.EAST;
			} else if (deltaX == -1) {
				return Direction.WEST;
			}
		}
		return Direction.NONE;
	}

	/**
	 * Tests whether or not a specified position is traversable in the specified
	 * direction.
	 *
	 * @param from The position.
	 * @param direction The direction to traverse.
	 * @param size The size of the entity attempting to traverse.
	 * @return <code>true</code> if the direction is traversable otherwise
	 *         <code>false</code>.
	 */
	public static boolean isTraversable(Position from, Direction direction, int size) {
		TraversalMap traversalMap = TraversalMap.getInstance();
		switch (direction) {
		case NORTH:
			return traversalMap.isTraversableNorth(from.getHeight(), from.getX(), from.getY(), size);
		case SOUTH:
			return traversalMap.isTraversableSouth(from.getHeight(), from.getX(), from.getY(), size);
		case EAST:
			return traversalMap.isTraversableEast(from.getHeight(), from.getX(), from.getY(), size);
		case WEST:
			return traversalMap.isTraversableWest(from.getHeight(), from.getX(), from.getY(), size);
		case NORTH_EAST:
			return traversalMap.isTraversableNorthEast(from.getHeight(), from.getX(), from.getY(), size);
		case NORTH_WEST:
			return traversalMap.isTraversableNorthWest(from.getHeight(), from.getX(), from.getY(), size);
		case SOUTH_EAST:
			return traversalMap.isTraversableSouthEast(from.getHeight(), from.getX(), from.getY(), size);
		case SOUTH_WEST:
			return traversalMap.isTraversableSouthWest(from.getHeight(), from.getX(), from.getY(), size);
		case NONE:
			return true;
		default:
			throw new IllegalArgumentException("direction: " + direction + " is not valid");
		}
	}

	/**
	 * Returns a {@link List} of positions that are traversable from the
	 * specified position.
	 *
	 * @param from The position.
	 * @param size The size of the mob attempting to traverse.
	 * @return A {@link List} of positions.
	 */
	public static List<Position> getNearbyTraversableTiles(Position from, int size) {
		TraversalMap traversalMap = TraversalMap.getInstance();
		List<Position> positions = new LinkedList<>();

		if (traversalMap.isTraversableNorth(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX(), from.getY() + 1, from.getHeight()));
		}

		if (traversalMap.isTraversableSouth(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX(), from.getY() - 1, from.getHeight()));
		}

		if (traversalMap.isTraversableEast(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() + 1, from.getY(), from.getHeight()));
		}

		if (traversalMap.isTraversableWest(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() - 1, from.getY(), from.getHeight()));
		}

		if (traversalMap.isTraversableNorthEast(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() + 1, from.getY() + 1, from.getHeight()));
		}

		if (traversalMap.isTraversableNorthWest(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() - 1, from.getY() + 1, from.getHeight()));
		}

		if (traversalMap.isTraversableSouthEast(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() + 1, from.getY() - 1, from.getHeight()));
		}

		if (traversalMap.isTraversableSouthWest(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() - 1, from.getY() - 1, from.getHeight()));
		}

		return positions;
	}

	/**
	 * The direction as an integer.
	 */
	private final int intValue;

	/**
	 * Creates the direction.
	 *
	 * @param intValue The direction as an integer.
	 */
	private Direction(int intValue) {
		this.intValue = intValue;
	}

	/**
	 * Gets the direction as an integer which the client can understand.
	 *
	 * @return The movement as an integer.
	 */
	public int toInteger() {
		return intValue;
	}

}