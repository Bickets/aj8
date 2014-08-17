package org.apollo.game.model.obj;

/**
 * Represents the group a object belongs to.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum ObjectGroup {

    /**
     * The wall object group.
     */
    WALL(0),

    /**
     * The wall decoration group.
     */
    WALL_DECORATION(1),

    /**
     * Unknown group 2.
     */
    GROUP_2(2),

    /**
     * Unknown group 3.
     */
    GROUP_3(3);

    /**
     * Represents the object groups for every type of object based on its index.
     */
    public static final int[] OBJECT_GROUPS = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2,
	    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };

    /**
     * The id of this object group.
     */
    private final int id;

    /**
     * Constructs a new {@link ObjectGroup} with the specified id.
     *
     * @param id The if of the object group.
     */
    private ObjectGroup(int id) {
	this.id = id;
    }

    /**
     * Returns an object group for the specified object type
     *
     * @param type The type of object.
     * @return The object group associated with the object type.
     */
    public static ObjectGroup forType(ObjectType type) {
	int id = OBJECT_GROUPS[type.getId()];
	for (ObjectGroup group : values()) {
	    if (group.id == id) {
		return group;
	    }
	}
	throw new IllegalArgumentException("type: " + type + " is not a valid object type, no group found.");
    }

}