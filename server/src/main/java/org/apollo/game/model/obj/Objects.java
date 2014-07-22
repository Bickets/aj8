package org.apollo.game.model.obj;

import org.apollo.game.model.Position;
import org.apollo.game.model.def.GameObjectDefinition;

/**
 * A static utility class containing helper methods for game objects.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Objects {

    /**
     * Returns a flag for denoting whether or not an object actually exists on
     * the map.
     * 
     * @param position The position of the object.
     * @return {@code true} if and only if the object exists on the map
     *         otherwise {@code false}.
     */
    public static boolean objectExists(GameObject obj, Position position) {
	return obj.getDefinition().objectExists(position);
    }

    /**
     * Returns the amount of tiles away from the specified object is needed in
     * order to interact with it.
     * 
     * @param id The id of the object.
     * @param position The starting position
     * @param obj The game object.
     * @return The amount of tiles away needed to interact with the specified
     *         object.
     */
    public static int getTileOffset(Position position, GameObject obj) {
	GameObjectDefinition def = obj.getDefinition();
	if (def.getSize() <= 1) {
	    return 1;
	}

	int distanceX = Math.abs(position.getX() - obj.getPosition().getX());
	int distanceY = Math.abs(position.getY() - obj.getPosition().getY());
	int total = distanceX > distanceY ? def.getSizeX() : def.getSizeY();
	return total;
    }

    /**
     * Returns the center position of the specified object.
     * 
     * @param obj The object.
     * @return The center position of the object.
     */
    public Position getCenterPosition(GameObject obj) {
	GameObjectDefinition def = obj.getDefinition();

	int width = def.getSizeX();
	int length = def.getSizeY();
	if (obj.getOrientation() == ObjectOrientation.NORTH || obj.getOrientation() == ObjectOrientation.SOUTH) {
	    width = length;
	    length = width;
	}
	int centerX = obj.getPosition().getX() + (width / 2);
	int centerY = obj.getPosition().getY() + (length / 2);
	return new Position(centerX, centerY);
    }

    /**
     * Returns the position nearest to the front of the object, this is the face
     * position.
     * 
     * @param obj The object.
     * @param from The starting position.
     * @return The position which faces the object.
     */
    public Position getTurnToPosition(GameObject obj, Position from) {
	GameObjectDefinition def = obj.getDefinition();
	Position position = obj.getPosition();

	int width = def.getSizeX();
	int length = def.getSizeY();
	if (obj.getOrientation() == ObjectOrientation.NORTH || obj.getOrientation() == ObjectOrientation.SOUTH) {
	    width = length;
	    length = width;
	}

	int turnToX = from.getX(), turnToY = from.getY();

	/* Within the width of the object */
	if (from.getX() >= position.getX() && from.getX() < position.getX() + width) {
	    turnToY = position.getY();
	}

	/* Within the length of the object */
	if (from.getY() >= position.getY() && from.getY() < position.getY() + width) {
	    turnToX = position.getX();
	}

	/* Upper left corner */
	if (from.getX() < position.getX() && from.getY() >= position.getY() + length) {
	    turnToX = position.getX();
	    turnToY = position.getY() + length - 1;
	}

	/* Upper right corner */
	if (from.getX() >= position.getX() + width && from.getY() >= position.getY() + length) {
	    turnToX = position.getX() + width - 1;
	    turnToY = position.getY() + length - 1;
	}

	/* Lower left corner */
	if (from.getX() < position.getX() + width && from.getY() < position.getY()) {
	    turnToX = position.getX();
	    turnToY = position.getY();
	}

	/* Lower right corner */
	if (from.getX() >= position.getX() + width && from.getY() < position.getY()) {
	    turnToX = position.getX() + width - 1;
	    turnToY = position.getY();
	}

	return new Position(turnToX, turnToY);
    }

    /**
     * Suppresses default-public constructor preventing this class from being
     * instantiated by other classes.
     * 
     * @throws InstantiationError If this class was instantiated from within
     *             itself.
     */
    private Objects() {
	throw new InstantiationError("static-utility classes may not be instantiated");
    }

}