package org.apollo.game.model.pf;

import org.apollo.game.model.GameCharacter;
import org.apollo.game.model.Position;

/**
 * An abstract path finder.
 *
 * @author Ryley Kimmel <ryley.kmmel@live.com>
 * @author Hadyn Richard
 * @author Graham
 */
public abstract class PathFinder {

    /**
     * Attempts to find a path using some implementation of a path finding
     * algorithm.
     *
     * @param character The game character attempting to find a path.
     * @param destX The destination x coordinate.
     * @param destY The destination y coordinate.
     * @return The path from the characters position to the destination
     *         position.
     */
    public Path find(GameCharacter character, int destX, int destY) {
	Position position = character.getPosition();

	int baseLocalX = position.getBaseLocalX();
	int baseLocalY = position.getBaseLocalY();

	int destLocalX = destX - baseLocalX;
	int destLocalY = destY - baseLocalY;

	return find(new Position(baseLocalX, baseLocalY), position.getHeight(), 104, 104, position.getLocalX(), position.getLocalY(), destLocalX, destLocalY, character.size());
    }

    /**
     * Attempts to find a path between two positions, useful for line of sight
     * algorithms.
     *
     * @param source The source position.
     * @param destination The destination position.
     * @return The path between the two positions.
     */
    public Path find(Position source, Position destination) {
	return new Path();
    }

    /**
     * Attempts to find a path using some implementation of a path finding
     * algorithm.
     *
     * @param position The initial position.
     * @param height The height level of the map.
     * @param width The width of the map.
     * @param length The length of the map.
     * @param srcX The source x coordinate
     * @param srcY The source y coordinate.
     * @param destX The destination x coordinate.
     * @param destY The destination y coordinate.
     * @param size The size of the entity finding a path.
     * @return The path from the source position to the destination position.
     */
    public Path find(Position position, int height, int width, int length, int srcX, int srcY, int destX, int destY, int size) {
	return new Path();
    }

}