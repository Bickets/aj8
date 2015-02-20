package org.apollo.game.model;

import java.util.Objects;

/**
 * Represents an option on some {@link Player}s context menu.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerContextMenuOption {

	/**
	 * Represents the 'Attack #username' option on some {@link Player}.
	 */
	public static final PlayerContextMenuOption ATTACK = new PlayerContextMenuOption(3, true, "Attack");

	/**
	 * Represents the 'Challenge #username' option on some {@link Player}.
	 */
	public static final PlayerContextMenuOption CHALLENGE = new PlayerContextMenuOption(3, true, "Challenge");

	/**
	 * Represents the 'Trade with #username' option on some {@link Player}.
	 */
	public static final PlayerContextMenuOption TRADE = new PlayerContextMenuOption(4, true, "Trade with");

	/**
	 * Represents the 'Follow #username' option on some {@link Player}.
	 */
	public static final PlayerContextMenuOption FOLLOW = new PlayerContextMenuOption(5, true, "Follow");

	/**
	 * The position the option is displayed at.
	 */
	private final int position;

	/**
	 * A flag denoting whether or not the option is displayed at the first
	 * position, regardless of the set {@code #position}.
	 */
	private final boolean top;

	/**
	 * The name of the option.
	 */
	private final String name;

	/**
	 * Constructs a new {@link PlayerContextMenuOption} with the specified
	 * position, top flag and name.
	 * 
	 * @param position The position the option is displayed at.
	 * @param top A flag denoting whether or not the option is displayed at the
	 *            first position, regardless of the set {@code #position}.
	 * @param name The name of the option.
	 */
	public PlayerContextMenuOption(int position, boolean top, String name) {
		this.position = position;
		this.top = top;
		this.name = Objects.requireNonNull(name);
	}

	/**
	 * Returns the position this context menu option is located at.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Returns a flag denoting whether or not the option is displayed at the
	 * first position, regardless of the set {@code #position}.
	 */
	public boolean onTop() {
		return top;
	}

	/**
	 * Returns the name of this context menu option.
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return (int) name.hashCode() * position * Boolean.hashCode(top);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlayerContextMenuOption) {
			PlayerContextMenuOption other = (PlayerContextMenuOption) obj;
			return name.equals(other.name) && top == other.top && position == other.position;
		}

		return false;
	}

}