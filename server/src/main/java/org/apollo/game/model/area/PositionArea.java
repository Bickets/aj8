package org.apollo.game.model.area;

import org.apollo.game.model.Position;

/**
 * Represents a simple {@link Position} area which uses a padding as the areas
 * offset.
 *
 * @author David Insley
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PositionArea extends Area {

    /**
     * Represents the position of this area.
     */
    private final Position position;

    /**
     * Constructs a new {@link PositionArea} with the specified position.
     *
     * @param position The position.
     */
    public PositionArea(Position position) {
	this.position = position;
    }

    @Override
    public boolean withinArea(int x, int y, int padding) {
	return x >= (position.getX() - padding) && x <= (position.getX() + padding) && y >= (position.getY() - padding) && y <= (position.getY() + padding);
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