package org.apollo.game.msg.impl;

import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;

/**
 * An action message which represents some action at a player.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PlayerActionMessage extends ActionMessage {

	/**
	 * The index of the player that was clicked.
	 */
	private final int index;

	/**
	 * Constructs a new {@link PlayerActionMessage} with the specified option
	 * and index.
	 *
	 * @param action The interface action clicked.
	 * @param index The index of the mob to interact with.
	 */
	public PlayerActionMessage(InteractContextMenuAction action, int index) {
		super(action);
		this.index = index;
	}

	/**
	 * Returns the index of this player.
	 */
	public int getIndex() {
		return index;
	}

}