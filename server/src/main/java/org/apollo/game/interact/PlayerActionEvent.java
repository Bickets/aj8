package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;

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
	 * The interface action clicked.
	 */
	private final InteractContextMenuAction action;

	/**
	 * Constructs a new {@link PlayerActionEvent}.
	 *
	 * @param other The player being interacted with.
	 * @param action The interface action clicked.
	 */
	public PlayerActionEvent(Player other, InteractContextMenuAction action) {
		this.other = other;
		this.action = action;
	}

	/**
	 * Returns the player being interacted with.
	 */
	public Player getOther() {
		return other;
	}

	/**
	 * Returns the clicked interface action.
	 */
	public InteractContextMenuAction getAction() {
		return action;
	}

}