package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.Interfaces.InterfaceOption;

/**
 * An event which is invoked when interacting with some {@link Player}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerActionEvent implements Event {

	/**
	 * The player being interacted with.
	 */
	private final Player other;

	/**
	 * The interface option clicked.
	 */
	private final InterfaceOption option;

	/**
	 * Constructs a new {@link PlayerActionEvent}.
	 *
	 * @param other The player being interacted with.
	 * @param option The interface option clicked.
	 */
	public PlayerActionEvent(Player other, InterfaceOption option) {
		this.other = other;
		this.option = option;
	}

	/**
	 * Returns the player being interacted with.
	 */
	public Player getOther() {
		return other;
	}

	/**
	 * Returns the clicked interface option.
	 */
	public InterfaceOption getOption() {
		return option;
	}

}