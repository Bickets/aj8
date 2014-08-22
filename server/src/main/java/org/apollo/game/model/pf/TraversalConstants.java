package org.apollo.game.model.pf;

/**
 * Represents flags for each of the traversals.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TraversalConstants {

    /**
     * The flag for a north facing wall.
     */
    protected static final int WALL_NORTH = 0x1;

    /**
     * The flag for a south facing wall.
     */
    protected static final int WALL_SOUTH = 0x2;

    /**
     * The flag for a east facing wall.
     */
    protected static final int WALL_EAST = 0x4;

    /**
     * The flag for a west facing wall.
     */
    protected static final int WALL_WEST = 0x8;

    /**
     * The flag for a north east facing wall.
     */
    protected static final int WALL_NORTH_EAST = 0x10;

    /**
     * The flag for a north west facing wall.
     */
    protected static final int WALL_NORTH_WEST = 0x20;

    /**
     * The flag for a south east facing wall.
     */
    protected static final int WALL_SOUTH_EAST = 0x40;

    /**
     * The flag for a south west facing wall.
     */
    protected static final int WALL_SOUTH_WEST = 0x80;

    /**
     * The flag for an object occupant.
     */
    protected static final int OCCUPANT = 0x8000;

    /**
     * The flag for an object occupant, which is impenetrable.
     */
    protected static final int IMPENETRABLE_OCCUPANT = 0x10000;

    /**
     * The flag for a impenetrable north facing wall.
     */
    protected static final int IMPENETRABLE_WALL_NORTH = 0x100;

    /**
     * The flag for a impenetrable south facing wall.
     */
    protected static final int IMPENETRABLE_WALL_SOUTH = 0x200;

    /**
     * The flag for a impenetrable east facing wall.
     */
    protected static final int IMPENETRABLE_WALL_EAST = 0x400;

    /**
     * The flag for a impenetrable west facing wall.
     */
    protected static final int IMPENETRABLE_WALL_WEST = 0x800;

    /**
     * The flag for a impenetrable north east facing wall.
     */
    protected static final int IMPENETRABLE_WALL_NORTH_EAST = 0x800;

    /**
     * The flag for a impenetrable north west facing wall.
     */
    protected static final int IMPENETRABLE_WALL_NORTH_WEST = 0x1000;

    /**
     * The flag for a impenetrable south east facing wall.
     */
    protected static final int IMPENETRABLE_WALL_SOUTH_EAST = 0x2000;

    /**
     * The flag for a impenetrable south west facing wall.
     */
    protected static final int IMPENETRABLE_WALL_SOUTH_WEST = 0x4000;

    /**
     * The flag which denotes a blocked tile.
     */
    protected static final int BLOCKED = 0x20000;

    /**
     * The flag which denotes a bridge tile.
     */
    protected static final int BRIDGE = 0x40000;

    /**
     * The flag which denotes a normal tile, no flag.
     */
    protected static final int NONE = 0x0;

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private TraversalConstants() {
	throw new InstantiationError("constant-container classes may not be instantiated.");
    }

}