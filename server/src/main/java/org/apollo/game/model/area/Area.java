package org.apollo.game.model.area;

import org.apollo.game.model.Position;

/**
 * Represents an area of {@link Position}s within the game world.
 *
 * @author Hadyn Richard
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class Area {

    /**
     * Tests whether or not all of the defined coordinates (bottom left corner
     * defined by position, length and width by offset) are within this
     * {@link Area}.
     *
     * @param position The bottom left corner of the area to check.
     * @param offset The area length and width.
     * @return {@code true} if the square area is entirely within the specified
     *         area otherwise {@code false}.
     */
    public boolean allWithinArea(Position position, int offset, int padding) {
	for (int xOffset = 0; xOffset < offset; xOffset++) {
	    for (int yOffset = 0; yOffset < offset; yOffset++) {
		if (!withinArea(position.getX() + xOffset, position.getY() + yOffset, padding)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests whether or not any of the defined coordinates (bottom left corner
     * defined by position, length and width by offset) are within this
     * {@link Area}.
     *
     * @param position The bottom left corner of the area to check.
     * @param offset The area length and width.
     * @return {@code true} if the square area is at least partially within the
     *         specified area otherwise {@code false}.
     */
    public boolean anyWithinArea(Position position, int offset, int padding) {
	for (int xOffset = 0; xOffset < offset; xOffset++) {
	    for (int yOffset = 0; yOffset < offset; yOffset++) {
		if (withinArea(position.getX() + xOffset, position.getY() + yOffset, padding)) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Tests whether or not the specified coordinates are within this
     * {@link Area}.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param padding The padding around this area.
     * @return {@code true} if and only if the specified coordinates lie within
     *         this {@link Area} otherwise {@code false}.
     */
    public abstract boolean withinArea(int x, int y, int padding);

    /**
     * Returns the center {@link Position} within this {@link Area}.
     */
    public abstract Position center();

    /**
     * Returns a pseudo-random {@link Position} within this {@link Area}.
     *
     * @param height The height of this pseudo-random position.
     * @return The pseudo-random position.
     */
    public abstract Position randomPosition(int height);

}