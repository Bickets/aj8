package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Interfaces.InterfaceOption;
import org.apollo.game.model.Player;

/**
 * An event which is invoked when interacting with some {@link Player}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerActionEvent implements Event {

	/**
	 * The player interacting with the other player.
	 */
	private final Player player;

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
	 * @param player The player interacting with the other player.
	 * @param other The player being interacted with.
	 * @param option The interface option clicked.
	 */
	public PlayerActionEvent(Player player, Player other, InterfaceOption option) {
		this.player = player;
		this.other = other;
		this.option = option;
	}

	/**
	 * Returns the player who is interacting with the other player.
	 */
	public Player getPlayer() {
		return player;
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