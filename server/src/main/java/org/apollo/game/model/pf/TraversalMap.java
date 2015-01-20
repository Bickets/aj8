package org.apollo.game.model.pf;

import static org.apollo.game.model.obj.GameObjectOrientation.EAST;
import static org.apollo.game.model.obj.GameObjectOrientation.NORTH;
import static org.apollo.game.model.obj.GameObjectOrientation.SOUTH;
import static org.apollo.game.model.obj.GameObjectOrientation.WEST;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apollo.game.model.Direction;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.obj.GameObjectOrientation;
import org.apollo.game.model.obj.GameObjectType;
import org.apollo.game.model.region.Region;

/**
 * Contains traversal data for a set of regions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Hadyn Richard
 */
public final class TraversalMap {

	/**
	 * The world.
	 */
	private final World world;

	/**
	 * Constructs a new {@link TraversalMap} with the specified world.
	 *
	 * @param world The world.
	 */
	public TraversalMap(World world) {
		this.world = world;
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
	public void markWall(GameObjectOrientation orientation, int height, int x, int y, GameObjectType type, boolean impenetrable) {
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
	public void unmarkWall(GameObjectOrientation orientation, int height, int x, int y, GameObjectType type, boolean impenetrable) {
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
		int localX = x & 0x3F;
		int localY = y & 0x3F;

		Region region = world.getRegionRepository().getRegion(new Position(x, y));
		if (region == null) {
			return;
		}

		int modifiedHeight = height;
		if (region.getTile(1, localX, localY).isActive(BRIDGE)) {
			modifiedHeight = height - 1;
		}

		region.getTile(modifiedHeight, x & 0x3F, y & 0x3F).set(BLOCKED);
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
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse north otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableNorth(int height, int x, int y) {
		return isTraversableNorth(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed north.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse north otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableNorth(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x, y + 1, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_SOUTH);
		}
		return isInactive(height, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
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
	public boolean isTraversableSouth(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableSouth(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed south.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse south otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableSouth(int height, int x, int y) {
		return isTraversableSouth(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed south.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse south otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableSouth(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x, y - 1, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_NORTH);
		}
		return isInactive(height, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
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
	public boolean isTraversableEast(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableEast(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed east.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse east otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableEast(int height, int x, int y) {
		return isTraversableEast(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed east.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse east otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableEast(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x + 1, y, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_WEST);
		}
		return isInactive(height, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED);
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
	public boolean isTraversableWest(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableWest(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed west.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse west otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableWest(int height, int x, int y) {
		return isTraversableWest(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed west.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse west otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversableWest(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x - 1, y, IMPENETRABLE_OCCUPANT | IMPENETRABLE_WALL_EAST);
		}
		return isInactive(height, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED);
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
	public boolean isTraversableNorthEast(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableNorthEast(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed north east.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse north east
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableNorthEast(int height, int x, int y) {
		return isTraversableNorthEast(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed north east.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse north east
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableNorthEast(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x + 1, y + 1, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_WALL_SOUTH_WEST | OCCUPANT) && isInactive(height, x + 1, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_OCCUPANT) && isInactive(height, x, y + 1, IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_OCCUPANT);
		}
		return isInactive(height, x + 1, y + 1, WALL_WEST | WALL_SOUTH | WALL_SOUTH_WEST | OCCUPANT | BLOCKED) && isInactive(height, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED) && isInactive(height, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
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
	public boolean isTraversableNorthWest(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableNorthWest(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed north west.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse north west
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableNorthWest(int height, int x, int y) {
		return isTraversableNorthWest(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed north west.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse north west
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableNorthWest(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x - 1, y + 1, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_WALL_SOUTH_EAST | OCCUPANT) && isInactive(height, x - 1, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_OCCUPANT) && isInactive(height, x, y + 1, IMPENETRABLE_WALL_SOUTH | IMPENETRABLE_OCCUPANT);
		}
		return isInactive(height, x - 1, y + 1, WALL_EAST | WALL_SOUTH | WALL_SOUTH_EAST | OCCUPANT | BLOCKED) && isInactive(height, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED) && isInactive(height, x, y + 1, WALL_SOUTH | OCCUPANT | BLOCKED);
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
	public boolean isTraversableSouthEast(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableSouthEast(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed south east.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse south east
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableSouthEast(int height, int x, int y) {
		return isTraversableSouthEast(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed south east.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse south east
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableSouthEast(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x + 1, y - 1, IMPENETRABLE_WALL_WEST | IMPENETRABLE_WALL_NORTH | IMPENETRABLE_WALL_NORTH_WEST | OCCUPANT) && isInactive(height, x + 1, y, IMPENETRABLE_WALL_WEST | IMPENETRABLE_OCCUPANT) && isInactive(height, x, y - 1, IMPENETRABLE_WALL_NORTH | IMPENETRABLE_OCCUPANT);
		}
		return isInactive(height, x + 1, y - 1, WALL_WEST | WALL_NORTH | WALL_NORTH_WEST | OCCUPANT | BLOCKED) && isInactive(height, x + 1, y, WALL_WEST | OCCUPANT | BLOCKED) && isInactive(height, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
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
	public boolean isTraversableSouthWest(int height, int x, int y, int size) {
		for (int offsetX = 0; offsetX < size; offsetX++) {
			for (int offsetY = 0; offsetY < size; offsetY++) {
				if (!isTraversableSouthWest(height, x + offsetX, y + offsetY)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Tests if the specified position can be traversed south west.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <code>true</code> if it is possible to traverse south west
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableSouthWest(int height, int x, int y) {
		return isTraversableSouthWest(height, x, y, false);
	}

	/**
	 * Tests if the specified position can be traversed south west.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param impenetrable Whether or not this occupation can be traversed.
	 * @return <code>true</code> if it is possible to traverse south west
	 *         otherwise <code>false</code>.
	 */
	public boolean isTraversableSouthWest(int height, int x, int y, boolean impenetrable) {
		if (impenetrable) {
			return isInactive(height, x - 1, y - 1, IMPENETRABLE_WALL_EAST | IMPENETRABLE_WALL_NORTH | IMPENETRABLE_WALL_NORTH_EAST | OCCUPANT) && isInactive(height, x - 1, y, IMPENETRABLE_WALL_EAST | IMPENETRABLE_OCCUPANT) && isInactive(height, x, y - 1, IMPENETRABLE_WALL_NORTH | IMPENETRABLE_OCCUPANT);
		}
		return isInactive(height, x - 1, y - 1, WALL_EAST | WALL_NORTH | WALL_NORTH_EAST | OCCUPANT | BLOCKED) && isInactive(height, x - 1, y, WALL_EAST | OCCUPANT | BLOCKED) && isInactive(height, x, y - 1, WALL_NORTH | OCCUPANT | BLOCKED);
	}

	/**
	 * Sets a flag on the specified position.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param flag The flag to put on this tile.
	 */
	public void set(int height, int x, int y, int flag) {
		Region region = world.getRegionRepository().getRegion(new Position(x, y));
		if (region == null) {
			return;
		}

		region.getTile(height, x & 0x3F, y & 0x3F).set(flag);
	}

	/**
	 * Checks whether or not the specified flag is not active on the specified
	 * position.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param flag The flag to check.
	 * @return <code>true</code> if the specified flag is not active on the
	 *         specified position, otherwise <code>false</code>.
	 */
	public boolean isInactive(int height, int x, int y, int flag) {
		int localX = x & 0x3F;
		int localY = y & 0x3F;

		Region region = world.getRegionRepository().getRegion(new Position(x, y));
		if (region == null) {
			return false;
		}

		int modifiedHeight = height;
		if (region.getTile(1, localX, localY).isActive(BRIDGE)) {
			modifiedHeight = height + 1;
		}

		return region.getTile(modifiedHeight, localX, localY).isInactive(flag);
	}

	/**
	 * Unsets the specified flag from the specified position.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param flag The flag to unset from the specified position.
	 */
	public void unset(int height, int x, int y, int flag) {
		Region region = world.getRegionRepository().getRegion(new Position(x, y));
		if (region == null) {
			return;
		}

		region.getTile(height, x & 0x3F, y & 0x3F).unset(flag);
	}

	/**
	 * Tests whether or not a specified position is traversable in the specified
	 * direction.
	 *
	 * @param from The position.
	 * @param direction The direction to traverse.
	 * @param size The size of the entity attempting to traverse.
	 * @return <code>true</code> if the direction is traversable otherwise
	 *         <code>false</code>.
	 */
	public boolean isTraversable(Position from, Direction direction, int size) {
		switch (direction) {
		case NORTH:
			return isTraversableNorth(from.getHeight(), from.getX(), from.getY(), size);
		case SOUTH:
			return isTraversableSouth(from.getHeight(), from.getX(), from.getY(), size);
		case EAST:
			return isTraversableEast(from.getHeight(), from.getX(), from.getY(), size);
		case WEST:
			return isTraversableWest(from.getHeight(), from.getX(), from.getY(), size);
		case NORTH_EAST:
			return isTraversableNorthEast(from.getHeight(), from.getX(), from.getY(), size);
		case NORTH_WEST:
			return isTraversableNorthWest(from.getHeight(), from.getX(), from.getY(), size);
		case SOUTH_EAST:
			return isTraversableSouthEast(from.getHeight(), from.getX(), from.getY(), size);
		case SOUTH_WEST:
			return isTraversableSouthWest(from.getHeight(), from.getX(), from.getY(), size);
		case NONE:
			return true;
		default:
			throw new IllegalArgumentException("direction: " + direction + " is not valid");
		}
	}

	/**
	 * Returns a {@link List} of positions that are traversable from the
	 * specified position.
	 *
	 * @param from The position.
	 * @param size The size of the mob attempting to traverse.
	 * @return A {@link List} of positions.
	 */
	public Set<Position> getNearbyTraversableTiles(Position from, int size) {
		Set<Position> positions = new HashSet<>();

		if (isTraversableNorth(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX(), from.getY() + 1, from.getHeight()));
		}

		if (isTraversableSouth(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX(), from.getY() - 1, from.getHeight()));
		}

		if (isTraversableEast(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() + 1, from.getY(), from.getHeight()));
		}

		if (isTraversableWest(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() - 1, from.getY(), from.getHeight()));
		}

		if (isTraversableNorthEast(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() + 1, from.getY() + 1, from.getHeight()));
		}

		if (isTraversableNorthWest(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() - 1, from.getY() + 1, from.getHeight()));
		}

		if (isTraversableSouthEast(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() + 1, from.getY() - 1, from.getHeight()));
		}

		if (isTraversableSouthWest(from.getHeight(), from.getX(), from.getY(), size)) {
			positions.add(new Position(from.getX() - 1, from.getY() - 1, from.getHeight()));
		}

		return positions;
	}

}