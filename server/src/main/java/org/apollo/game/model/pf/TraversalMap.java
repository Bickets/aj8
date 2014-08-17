package org.apollo.game.model.pf;

import static org.apollo.game.model.Position.MAXIMUM_HEIGHT_LEVELS;
import static org.apollo.game.model.obj.ObjectOrientation.EAST;
import static org.apollo.game.model.obj.ObjectOrientation.NORTH;
import static org.apollo.game.model.obj.ObjectOrientation.SOUTH;
import static org.apollo.game.model.obj.ObjectOrientation.WEST;
import static org.apollo.game.model.pf.TraversalConstants.BLOCKED;
import static org.apollo.game.model.pf.TraversalConstants.BRIDGE;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_OCCUPANT;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_EAST;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_NORTH;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_NORTH_EAST;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_NORTH_WEST;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_SOUTH;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_SOUTH_EAST;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_SOUTH_WEST;
import static org.apollo.game.model.pf.TraversalConstants.IMPENETRABLE_WALL_WEST;
import static org.apollo.game.model.pf.TraversalConstants.OCCUPANT;
import static org.apollo.game.model.pf.TraversalConstants.WALL_EAST;
import static org.apollo.game.model.pf.TraversalConstants.WALL_NORTH;
import static org.apollo.game.model.pf.TraversalConstants.WALL_NORTH_EAST;
import static org.apollo.game.model.pf.TraversalConstants.WALL_NORTH_WEST;
import static org.apollo.game.model.pf.TraversalConstants.WALL_SOUTH;
import static org.apollo.game.model.pf.TraversalConstants.WALL_SOUTH_EAST;
import static org.apollo.game.model.pf.TraversalConstants.WALL_SOUTH_WEST;
import static org.apollo.game.model.pf.TraversalConstants.WALL_WEST;

import org.apollo.game.model.obj.ObjectOrientation;
import org.apollo.game.model.obj.ObjectType;

/**
 * Contains traversal data for a set of regions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class TraversalMap {

    /**
     * A singleton instance of this class.
     */
    private static final TraversalMap INSTANCE = new TraversalMap();

    /**
     * The size of one side of the region array.
     */
    public static final int SIZE = 256;

    /**
     * The size of a region.
     */
    public static final int REGION_SIZE = 64;

    /**
     * The regions for the traversal data.
     */
    private final Region[] regions = new Region[SIZE * SIZE];

    /**
     * Represents a single region.
     *
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     * @author Hadyn Richard
     */
    private final class Region {

	/**
	 * The flags within the region.
	 */
	private Tile[][] tiles;

	/**
	 * Constructs a new {@link Region}.
	 */
	private Region() {
	    tiles = new Tile[MAXIMUM_HEIGHT_LEVELS][REGION_SIZE * REGION_SIZE];
	    for (int height = 0; height < MAXIMUM_HEIGHT_LEVELS; height++) {
		for (int regionId = 0; regionId < REGION_SIZE * REGION_SIZE; regionId++) {
		    tiles[height][regionId] = new Tile();
		}
	    }
	}

	/**
	 * Gets a single tile in this region from the specified height, x and y
	 * coordinates.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The tile in this region for the specified attributes.
	 */
	public Tile getTile(int height, int x, int y) {
	    return tiles[height][x + y * REGION_SIZE];
	}
    }

    /**
     * Initializes the region at the specified coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void initializeRegion(int x, int y) {
	int regionX = x >> 6;
	int regionY = y >> 6;

	regions[regionX + regionY * SIZE] = new Region();
    }

    /**
     * Gets if the set contains a region for the specified coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public boolean regionInitialized(int x, int y) {
	int regionX = x >> 6;
	int regionY = y >> 6;

	return regions[regionX + regionY * SIZE] != null;
    }

    /**
     * Informs the region of an existing wall.
     *
     * @param orientation The orientation of the wall.
     * @param height The walls height.
     * @param x The walls x coordinate.
     * @param y The walls y coordinate.
     * @param type The type of wall.
     * @param impenetrable Whether or not this wall can be passed through.
     */
    public void markWall(ObjectOrientation orientation, int height, int x, int y, ObjectType type, boolean impenetrable) {
	switch (type) {
	case STRAIGHT_WALL:
	    if (orientation == WEST) {
		set(height, x, y, WALL_WEST);
		set(height, x - 1, y, WALL_EAST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_WEST);
		    set(height, x - 1, y, IMPENETRABLE_WALL_EAST);
		}
	    }
	    if (orientation == NORTH) {
		set(height, x, y, WALL_NORTH);
		set(height, x, y + 1, WALL_SOUTH);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_NORTH);
		    set(height, x, y + 1, IMPENETRABLE_WALL_SOUTH);
		}
	    }
	    if (orientation == EAST) {
		set(height, x, y, WALL_EAST);
		set(height, x + 1, y, WALL_WEST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_EAST);
		    set(height, x + 1, y, IMPENETRABLE_WALL_WEST);
		}
	    }
	    if (orientation == SOUTH) {
		set(height, x, y, WALL_SOUTH);
		set(height, x, y - 1, WALL_NORTH);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_SOUTH);
		    set(height, x, y - 1, IMPENETRABLE_WALL_NORTH);
		}
	    }
	    break;

	case ENTIRE_WALL:
	    if (orientation == WEST) {
		set(height, x, y, WALL_WEST | WALL_NORTH);
		set(height, x - 1, y, WALL_EAST);
		set(height, x, y + 1, WALL_SOUTH);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_NORTH);
		    set(height, x - 1, y, IMPENETRABLE_WALL_EAST);
		    set(height, x, y + 1, IMPENETRABLE_WALL_SOUTH);
		}
	    }
	    if (orientation == NORTH) {
		set(height, x, y, WALL_EAST | WALL_NORTH);
		set(height, x, y + 1, WALL_SOUTH);
		set(height, x + 1, y, WALL_WEST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_NORTH);
		    set(height, x, y + 1, IMPENETRABLE_WALL_SOUTH);
		    set(height, x + 1, y, IMPENETRABLE_WALL_WEST);
		}
	    }
	    if (orientation == EAST) {
		set(height, x, y, WALL_EAST | WALL_SOUTH);
		set(height, x + 1, y, WALL_WEST);
		set(height, x, y - 1, WALL_NORTH);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_SOUTH);
		    set(height, x + 1, y, IMPENETRABLE_WALL_WEST);
		    set(height, x, y - 1, IMPENETRABLE_WALL_NORTH);
		}
	    }
	    if (orientation == SOUTH) {
		set(height, x, y, WALL_WEST | WALL_SOUTH);
		set(height, x - 1, y, WALL_EAST);
		set(height, x, y - 1, WALL_NORTH);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_SOUTH);
		    set(height, x - 1, y, IMPENETRABLE_WALL_EAST);
		    set(height, x, y - 1, IMPENETRABLE_WALL_NORTH);
		}
	    }
	    break;

	case DIAGONAL_CORNER_WALL:
	case WALL_CORNER:
	    if (orientation == WEST) {
		set(height, x, y, WALL_NORTH_WEST);
		set(height, x - 1, y + 1, WALL_SOUTH_EAST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_NORTH_WEST);
		    set(height, x - 1, y + 1, IMPENETRABLE_WALL_SOUTH_EAST);
		}
	    }
	    if (orientation == NORTH) {
		set(height, x, y, WALL_NORTH_EAST);
		set(height, x + 1, y + 1, WALL_SOUTH_WEST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_NORTH_EAST);
		    set(height, x + 1, y + 1, IMPENETRABLE_WALL_SOUTH_WEST);
		}
	    }
	    if (orientation == EAST) {
		set(height, x, y, WALL_SOUTH_EAST);
		set(height, x + 1, y - 1, WALL_NORTH_WEST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_SOUTH_EAST);
		    set(height, x + 1, y - 1, IMPENETRABLE_WALL_NORTH_WEST);
		}
	    }
	    if (orientation == SOUTH) {
		set(height, x, y, WALL_SOUTH_WEST);
		set(height, x - 1, y - 1, WALL_NORTH_EAST);
		if (impenetrable) {
		    set(height, x, y, IMPENETRABLE_WALL_SOUTH_WEST);
		    set(height, x - 1, y - 1, IMPENETRABLE_WALL_NORTH_EAST);
		}
	    }
	    break;
	default:
	    break;
	}
    }

    /**
     * Informs the region of an existing wall being removed.
     *
     * @param orientation The orientation of the wall.
     * @param height The walls height.
     * @param x The walls x coordinate.
     * @param y The walls y coordinate.
     * @param type The type of wall.
     * @param impenetrable Whether or not this wall can be passed through.
     */
    public void unmarkWall(ObjectOrientation orientation, int height, int x, int y, ObjectType type, boolean impenetrable) {
	switch (type) {
	case STRAIGHT_WALL:
	    if (orientation == WEST) {
		unset(height, x, y, WALL_WEST);
		unset(height, x - 1, y, WALL_EAST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_WEST);
		    unset(height, x - 1, y, IMPENETRABLE_WALL_EAST);
		}
	    }
	    if (orientation == NORTH) {
		unset(height, x, y, WALL_NORTH);
		unset(height, x, y + 1, WALL_SOUTH);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_NORTH);
		    unset(height, x, y + 1, IMPENETRABLE_WALL_SOUTH);
		}
	    }
	    if (orientation == EAST) {
		unset(height, x, y, WALL_EAST);
		unset(height, x + 1, y, WALL_WEST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_EAST);
		    unset(height, x + 1, y, IMPENETRABLE_WALL_WEST);
		}
	    }
	    if (orientation == SOUTH) {
		unset(height, x, y, WALL_SOUTH);
		unset(height, x, y - 1, WALL_NORTH);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_SOUTH);
		    unset(height, x, y - 1, IMPENETRABLE_WALL_NORTH);
		}
	    }
	    break;

	case ENTIRE_WALL:
	    if (orientation == WEST) {
		unset(height, x, y, WALL_WEST | WALL_NORTH);
		unset(height, x - 1, y, WALL_EAST);
		unset(height, x, y + 1, WALL_SOUTH);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_NORTH);
		    unset(height, x - 1, y, IMPENETRABLE_WALL_EAST);
		    unset(height, x, y + 1, IMPENETRABLE_WALL_SOUTH);
		}
	    }
	    if (orientation == NORTH) {
		unset(height, x, y, WALL_EAST | WALL_NORTH);
		unset(height, x, y + 1, WALL_SOUTH);
		unset(height, x + 1, y, WALL_WEST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_NORTH);
		    unset(height, x, y + 1, IMPENETRABLE_WALL_SOUTH);
		    unset(height, x + 1, y, IMPENETRABLE_WALL_WEST);
		}
	    }
	    if (orientation == EAST) {
		unset(height, x, y, WALL_EAST | WALL_SOUTH);
		unset(height, x + 1, y, WALL_WEST);
		unset(height, x, y - 1, WALL_NORTH);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_SOUTH);
		    unset(height, x + 1, y, IMPENETRABLE_WALL_WEST);
		    unset(height, x, y - 1, IMPENETRABLE_WALL_NORTH);
		}
	    }
	    if (orientation == SOUTH) {
		unset(height, x, y, WALL_EAST | WALL_SOUTH);
		unset(height, x, y - 1, WALL_WEST);
		unset(height, x - 1, y, WALL_NORTH);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_SOUTH);
		    unset(height, x, y - 1, IMPENETRABLE_WALL_WEST);
		    unset(height, x - 1, y, IMPENETRABLE_WALL_NORTH);
		}
	    }
	    break;

	case DIAGONAL_CORNER_WALL:
	case WALL_CORNER:
	    if (orientation == WEST) {
		unset(height, x, y, WALL_NORTH_WEST);
		unset(height, x - 1, y + 1, WALL_SOUTH_EAST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_NORTH_WEST);
		    unset(height, x - 1, y + 1, IMPENETRABLE_WALL_SOUTH_EAST);
		}
	    }
	    if (orientation == NORTH) {
		unset(height, x, y, WALL_NORTH_EAST);
		unset(height, x + 1, y + 1, WALL_SOUTH_WEST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_NORTH_EAST);
		    unset(height, x + 1, y + 1, IMPENETRABLE_WALL_SOUTH_WEST);
		}
	    }
	    if (orientation == EAST) {
		unset(height, x, y, WALL_SOUTH_EAST);
		unset(height, x + 1, y - 1, WALL_NORTH_WEST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_SOUTH_EAST);
		    unset(height, x + 1, y - 1, IMPENETRABLE_WALL_NORTH_WEST);
		}
	    }
	    if (orientation == SOUTH) {
		unset(height, x, y, WALL_SOUTH_WEST);
		unset(height, x - 1, y - 1, WALL_NORTH_EAST);
		if (impenetrable) {
		    unset(height, x, y, IMPENETRABLE_WALL_SOUTH_WEST);
		    unset(height, x - 1, y - 1, IMPENETRABLE_WALL_NORTH_EAST);
		}
	    }
	    break;
	default:
	    break;
	}
    }

    /**
     * Marks the specified set of coordinates blocked, unable to be passed
     * through.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void markBlocked(int height, int x, int y) {
	int regionX = x >> 6;
	int regionY = y >> 6;

	int localX = x & 0x3F;
	int localY = y & 0x3F;

	Region region = regions[regionX + regionY * SIZE];
	if (region == null) {
	    return;
	}

	int modifiedPlane = height;
	if ((region.getTile(1, localX, localY).flags() & BRIDGE) != 0) {
	    modifiedPlane = height - 1;
	}

	region.getTile(modifiedPlane, x & 0x3F, y & 0x3F).set(BLOCKED);
    }

    /**
     * Marks the specified coordinates occupied by some object.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width of the occupation.
     * @param length The length of the occupation.
     * @param impenetrable Whether or not this occupation can be passed through.
     */
    public void markOccupant(int height, int x, int y, int width, int length, boolean impenetrable) {
	for (int offsetX = 0; offsetX < width; offsetX++) {
	    for (int offsetY = 0; offsetY < length; offsetY++) {
		set(height, x + offsetX, y + offsetY, OCCUPANT);
		if (impenetrable) {
		    set(height, x + offsetX, y + offsetY, IMPENETRABLE_OCCUPANT);
		}
	    }
	}
    }

    /**
     * Marks the specified coordinates a bridge.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void markBridge(int height, int x, int y) {
	set(height, x, y, BRIDGE);
    }

    /**
     * Tests if the specified position can be traversed north.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse north.
     * @return <code>true</code> if it is possible to traverse north otherwise
     *         <code>false</code>
     */
    public boolean isTraversableNorth(int height, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableNorth(height, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed north.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse north otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableNorth(int plane, int x, int y) {
	return isTraversableNorth(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed north.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse north otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableNorth(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x, y + 1, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_SOUTH);
	}
	return isInactive(plane, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed south.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse south.
     * @return <code>true</code> if it is possible to traverse south otherwise
     *         <code>false</code>
     */
    public boolean isTraversableSouth(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableSouth(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed south.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse south otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableSouth(int plane, int x, int y) {
	return isTraversableSouth(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed south.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse south otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableSouth(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x, y - 1, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_NORTH);
	}
	return isInactive(plane, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed east.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse east.
     * @return <code>true</code> if it is possible to traverse east otherwise
     *         <code>false</code>
     */
    public boolean isTraversableEast(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableEast(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed east.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse east otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableEast(int plane, int x, int y) {
	return isTraversableEast(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed east.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse east otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableEast(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x + 1, y, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_WEST);
	}
	return isInactive(plane, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed west.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse west.
     * @return <code>true</code> if it is possible to traverse west otherwise
     *         <code>false</code>
     */
    public boolean isTraversableWest(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableWest(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed west.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse west otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableWest(int plane, int x, int y) {
	return isTraversableWest(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed west.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse west otherwise
     *         <code>false</code>.
     */
    public boolean isTraversableWest(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x - 1, y, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_EAST);
	}
	return isInactive(plane, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed north east.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse north east.
     * @return <code>true</code> if it is possible to traverse north east
     *         otherwise <code>false</code>
     */
    public boolean isTraversableNorthEast(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableNorthEast(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed north east.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse north east
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableNorthEast(int plane, int x, int y) {
	return isTraversableNorthEast(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed north east.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse north east
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableNorthEast(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x + 1, y + 1, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_WALL_SOUTH_WEST | OCCUPANT) && isInactive(plane, x + 1, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_OCCUPANT) && isInactive(plane, x, y + 1, IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_OCCUPANT);
	}
	return isInactive(plane, x + 1, y + 1, WALL_WEST | WALL_SOUTH | WALL_SOUTH_WEST | OCCUPANT | BLOCKED) && isInactive(plane, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED) && isInactive(plane, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed north west.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse north west.
     * @return <code>true</code> if it is possible to traverse north west
     *         otherwise <code>false</code>
     */
    public boolean isTraversableNorthWest(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableNorthWest(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed north west.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse north west
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableNorthWest(int plane, int x, int y) {
	return isTraversableNorthWest(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed north west.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse north west
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableNorthWest(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x - 1, y + 1, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_WALL_SOUTH_EAST | OCCUPANT) && isInactive(plane, x - 1, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_OCCUPANT) && isInactive(plane, x, y + 1, IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_OCCUPANT);
	}
	return isInactive(plane, x - 1, y + 1, WALL_EAST | WALL_SOUTH | WALL_SOUTH_EAST | OCCUPANT | BLOCKED) && isInactive(plane, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED) && isInactive(plane, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed south east.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse south east.
     * @return <code>true</code> if it is possible to traverse south east
     *         otherwise <code>false</code>
     */
    public boolean isTraversableSouthEast(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableSouthEast(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed south east.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse south east
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableSouthEast(int plane, int x, int y) {
	return isTraversableSouthEast(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed south east.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse south east
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableSouthEast(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x + 1, y - 1, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_NORTH | IMPENETRABLE_WALL_NORTH_WEST | OCCUPANT) && isInactive(plane, x + 1, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_OCCUPANT) && isInactive(plane, x, y - 1, IMPENETRABLE_WALL_NORTH | IMPENETRABLE_OCCUPANT);
	}
	return isInactive(plane, x + 1, y - 1, WALL_WEST | WALL_NORTH | WALL_NORTH_WEST | OCCUPANT | BLOCKED) && isInactive(plane, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED) && isInactive(plane, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
    }

    /**
     * Tests if the specified position can be traversed south west.
     *
     * @param height The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param size The size of the entity attempting to traverse south west.
     * @return <code>true</code> if it is possible to traverse south west
     *         otherwise <code>false</code>
     */
    public boolean isTraversableSouthWest(int plane, int x, int y, int size) {
	for (int offsetX = 0; offsetX < size; offsetX++) {
	    for (int offsetY = 0; offsetY < size; offsetY++) {
		if (!isTraversableSouthWest(plane, x + offsetX, y + offsetY)) {
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Tests if the specified position can be traversed south west.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return <code>true</code> if it is possible to traverse south west
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableSouthWest(int plane, int x, int y) {
	return isTraversableSouthWest(plane, x, y, false);
    }

    /**
     * Tests if the specified position can be traversed south west.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param impenetrable Whether or not this occupation can be traversed.
     * @return <code>true</code> if it is possible to traverse south west
     *         otherwise <code>false</code>.
     */
    public boolean isTraversableSouthWest(int plane, int x, int y, boolean impenetrable) {
	if (impenetrable) {
	    return isInactive(plane, x - 1, y - 1, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_NORTH | IMPENETRABLE_WALL_NORTH_EAST | OCCUPANT) && isInactive(plane, x - 1, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_OCCUPANT) && isInactive(plane, x, y - 1, IMPENETRABLE_WALL_NORTH | IMPENETRABLE_OCCUPANT);
	}
	return isInactive(plane, x - 1, y - 1, WALL_EAST | WALL_NORTH | WALL_NORTH_EAST | OCCUPANT | BLOCKED) && isInactive(plane, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED) && isInactive(plane, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
    }

    /**
     * Sets a flag on the specified position.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param flag The flag to put on this tile.
     */
    public void set(int plane, int x, int y, int flag) {
	int regionX = x >> 6;
	int regionY = y >> 6;

	Region region = regions[regionX + regionY * SIZE];
	if (region == null) {
	    return;
	}

	region.getTile(plane, x & 0x3F, y & 0x3F).set(flag);
    }

    /**
     * Checks whether or not the specified flag is not active on the specified
     * position.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param flag The flag to check.
     * @return <code>true</code> if the specified flag is not active on the
     *         specified position, otherwise <code>false</code>.
     */
    public boolean isInactive(int plane, int x, int y, int flag) {
	int regionX = x >> 6;
	int regionY = y >> 6;

	int localX = x & 0x3F;
	int localY = y & 0x3F;

	Region region = regions[regionX + regionY * SIZE];
	if (region == null) {
	    return false;
	}

	int modifiedPlane = plane;
	if (region.getTile(1, localX, localY).isActive(BRIDGE)) {
	    modifiedPlane = plane + 1;
	}

	return region.getTile(modifiedPlane, localX, localY).isInactive(flag);
    }

    /**
     * Unsets the specified flag from the specified position.
     *
     * @param plane The height.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param flag The flag to unset from the specified position.
     */
    public void unset(int plane, int x, int y, int flag) {
	int regionX = x >> 6;
	int regionY = y >> 6;

	Region region = regions[regionX + regionY * SIZE];
	if (region == null) {
	    return;
	}

	region.getTile(plane, x & 0x3F, y & 0x3F).unset(flag);
    }

    /**
     * Returns the singleton instance.
     */
    public static TraversalMap getInstance() {
	return INSTANCE;
    }

}