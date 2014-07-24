package org.apollo.game.model.obj;

/**
 * Represents the orientation of an object.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum ObjectOrientation {

    /**
     * The north orientation.
     */
    NORTH(1),

    /**
     * The south orientation.
     */
    SOUTH(3),

    /**
     * The east orientation.
     */
    EAST(2),

    /**
     * The west orientation.
     */
    WEST(0);

    /**
     * The id of the orientation.
     */
    private final int id;

    /**
     * Constructs a new {@link ObjectOrientation} with the specified orientation
     * id.
     * 
     * @param id The orientation id.
     */
    private ObjectOrientation(int id) {
	this.id = id;
    }

    /**
     * Returns the id of this orientation.
     */
    public final int getId() {
	return id;
    }

    /**
     * Returns a single object orientation for the specified id.
     * 
     * @param id The orientations id.
     * @return The objects orientation if possible.
     */
    public static ObjectOrientation forId(int id) {
	for (ObjectOrientation orientation : values()) {
	    if (orientation.getId() == id) {
		return orientation;
	    }
	}
	throw new IllegalArgumentException("no orientation found for id: " + id);
    }

}