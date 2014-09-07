package org.apollo.game.model.pf;

import static org.apollo.game.model.pf.TraversalConstants.NONE;

/**
 * Represents a single tile.
 *
 * @author Graham
 * @author Hadyn Richard
 */
public final class Tile {

	/**
	 * The flags for this tile.
	 */
	private int flags;

	/**
	 * Constructs a new, normal {@link Tile}
	 */
	public Tile() {
		this(NONE);
	}

	/**
	 * Constructs a new {@link Tile} with the specified flags.
	 *
	 * @param flags The flags this tile contains.
	 */
	public Tile(int flags) {
		this.flags = flags;
	}

	/**
	 * Sets the specified flag to this tile.
	 *
	 * @param flag The flag to set.
	 */
	public void set(int flag) {
		flags |= flag;
	}

	/**
	 * Checks whether or not a single flag is active on this tile.
	 *
	 * @param flag The flag to check.
	 * @return <code>true</code> if the specified flag is active on this tile
	 *         otherwise <code>false</code>.
	 */
	public boolean isActive(int flag) {
		return (flags & flag) != 0;
	}

	/**
	 * Checks whether or not a single flag is inactive on this tile.
	 *
	 * @param flag The flag to check.
	 * @return <code>true</code> if the specified flag is inactive on this tile
	 *         otherwise <code>false</code>.
	 */
	public boolean isInactive(int flag) {
		return (flags & flag) == 0;
	}

	/**
	 * Unsets the specified flag from this tile.
	 *
	 * @param flag The flag to unset.
	 */
	public void unset(int flag) {
		flags &= 0xFFFF - flag;
	}

	/**
	 * Returns the flags for this tile.
	 */
	public int flags() {
		return flags;
	}

}