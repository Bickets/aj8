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