package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;

/**
 * An event which manages button actions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ButtonActionEvent implements Event {

	/**
	 * The player to perform the button action for.
	 */
	private final Player player;

	/**
	 * The id of the button.
	 */
	private final int id;

	/**
	 * Constructs a new {@link ButtonActionEvent} with the specified player and
	 * button id.
	 *
	 * @param player The player.
	 * @param id The buttons id.
	 */
	public ButtonActionEvent(Player player, int id) {
		this.player = player;
		this.id = id;
	}

	/**
	 * Returns the player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the buttons id.
	 */
	public int getId() {
		return id;
	}

}