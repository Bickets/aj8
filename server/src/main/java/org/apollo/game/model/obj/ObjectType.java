package org.apollo.game.model.obj;

/**
 * Represents an objects type.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Maxi <http://www.rune-server.org/members/maxi/>
 */
public enum ObjectType {

    /**
     * Represents straight walls, fences etc.
     */
    STRAIGHT_WALL(0),

    /**
     * Represents diagonal walls corner, fences etc connectors.
     */
    DIAGONAL_CORNER_WALL(1),

    /**
     * Represents entire walls, fences etc corners.
     */
    ENTIRE_WALL(2),

    /**
     * Represents straight wall corners, fences etc connectors.
     */
    WALL_CORNER(3),

    /**
     * Represents straight inside wall decorations.
     */
    STRAIGHT_INSIDE_WALL_DECORATION(4),

    /**
     * Represents straight outside wall decorations.
     */
    STRAIGHT_OUTSIDE_WALL_DECORATION(5),

    /**
     * Represents diagonal outside wall decorations.
     */
    DIAGONAL_OUTSIDE_WALL_DECORATION(6),

    /**
     * Represents diagonal inside wall decorations.
     */
    DIAGONAL_INSIDE_WALL_DECORATION(7),

    /**
     * Represents diagonal in wall decorations.
     */
    DIAGONAL_INTERIOR_WALL_DECORATION(8),

    /**
     * Represents diagonal walls, fences etc.
     */
    DIAGONAL_WALL(9),

    /**
     * Represents all kinds of objects, trees, statues, signs, fountains etc.
     */
    GENERAL_PROP(10),

    /**
     * Represents ground objects like daisies etc.
     */
    WALKABLE_PROP(11),

    /**
     * Represents straight sloped roofs.
     */
    STRAIGHT_SLOPED_ROOF(12),

    /**
     * Represents diagonal sloped roofs.
     */
    DIAGONAL_SLOPED_ROOF(13),

    /**
     * Represents diagonal slope connecting roofs.
     */
    DIAGONAL_SLOPED_CONNECTING_ROOF(14),

    /**
     * Represents straight sloped corner connecting roofs.
     */
    STRAIGHT_SLOPED_CORNER_CONNECTING_ROOF(15),

    /**
     * Represents straight sloped corner roofs.
     */
    STRAIGHT_SLOPED_CORNER_ROOF(16),

    /**
     * Represents straight flat top roofs.
     */
    STRAIGHT_FLAT_TOP_ROOF(17),

    /**
     * Represents straight bottom edge roofs.
     */
    STRAIGHT_BOTTOM_EDGE_ROOF(18),

    /**
     * Represents diagonal bottom edge connecting roofs.
     */
    DIAGONAL_BOTTOM_EDGE_CONNECTING_ROOF(19),

    /**
     * Represents straight bottom edge connecting roofs.
     */
    STRAIGHT_BOTTOM_EDGE_CONNECTING_ROOF(20),

    /**
     * Represents straight bottom edge connecting corner roofs.
     */
    STRAIGHT_BOTTOM_EDGE_CONNECTING_CORNER_ROOF(21),

    /**
     * Represents ground decoration + map signs (quests, water fountains, shops.
     * etc)
     */
    GROUND_PROP(22);

    /**
     * The id of this object type.
     */
    private final int id;

    /**
     * Constructs a new {@link ObjectType} with the specified id.
     * 
     * @param id The id of this object type.
     */
    private ObjectType(int id) {
	this.id = id;
    }

    /**
     * Returns this object types value.
     */
    public final int getId() {
	return id;
    }

    /**
     * Returns a single object type for the specified id.
     * 
     * @param id The types id.
     * @return The objects type if possible.
     */
    public static ObjectType forId(int id) {
	for (ObjectType type : values()) {
	    if (type.getId() == id) {
		return type;
	    }
	}
	throw new IllegalArgumentException("no type foiund for id: " + id);
    }

}