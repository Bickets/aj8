package org.apollo.game.model.area;

import org.apollo.game.model.Position;

/**
 * Represents an area as specified by two positions, left and right.
 *
 * @author Hadyn Richard
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class QuadArea extends Area {

    /**
     * Represents the left, least, x coordinate.
     */
    private final int leftX;

    /**
     * Represents the left, least, y coordinate.
     */
    private final int leftY;

    /**
     * Represents the right, max, x coordinate.
     */
    private final int rightX;

    /**
     * Represents the right, max, y coordinate.
     */
    private final int rightY;

    /**
     * Constructs a new {@link QuadArea} with the specified left and right
     * positions.
     *
     * @param left The left, least, position.
     * @param right The right, max, position.
     */
    public QuadArea(Position left, Position right) {
	this(left.getX(), left.getY(), right.getX(), right.getY());
    }

    /**
     * Constructs a new {@link QuadArea} with the specified left and right
     * coordinates.
     *
     * @param leftX The left, least, x coordinate.
     * @param leftY The left, least, y coordinate.
     * @param rightX The right, max, x coordinate.
     * @param rightY The right, max, y coordinate.
     */
    public QuadArea(int leftX, int leftY, int rightX, int rightY) {
	this.leftX = leftX;
	this.leftY = leftY;
	this.rightX = rightX;
	this.rightY = rightY;
    }

    @Override
    public boolean withinArea(int x, int y, int padding) {
	return x >= (leftX - padding) && x <= (rightX + padding) && y >= (leftY - padding) && y <= (rightY + padding);
    }

    @Override
    public Position center() {
	return new Position(leftX + ((rightX - leftX) / 2), leftY + ((rightY - leftY) / 2));
    }

    @Override
    public Position randomPosition(int height) {
	int x = leftX + (int) (Math.random() * (rightX - leftX + 1));
	int y = leftY + (int) (Math.random() * (rightY - leftY + 1));
	return new Position(x, y, height);
    }

}