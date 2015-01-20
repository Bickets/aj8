package org.apollo.game.model.region;

import static org.apollo.game.model.pf.TraversalConstants.NONE;

/**
 * Represents a single tile.
 *
 * @author Graham
 * @author Hadyn Richard
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Tile {

	/**
	 * The map flag which represents a clipped tile.
	 */
	public static final int FLAG_BLOCKED = 0x1;

	/**
	 * The map flag which represents a bridge tile.
	 */
	public static final int FLAG_BRIDGE = 0x2;

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
	public int getFlags() {
		return flags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flags;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tile other = (Tile) obj;
		if (flags != other.flags) {
			return false;
		}
		return true;
	}

}