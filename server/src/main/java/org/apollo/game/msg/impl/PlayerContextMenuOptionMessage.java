package org.apollo.game.msg.impl;

import org.apollo.game.model.Player;
import org.apollo.game.model.PlayerContextMenuOption;
import org.apollo.game.msg.Message;

/**
 * Represents a message which sends an option on some {@link Player}s context
 * menu.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerContextMenuOptionMessage implements Message {

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
	public PlayerContextMenuOptionMessage(int position, boolean top, String name) {
		this.position = position;
		this.top = top;
		this.name = name;
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

}