package org.apollo.game.msg.impl;

import org.apollo.game.model.inter.Interfaces.InterfaceOption;

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
	 * @param option The interface option clicked.
	 * @param index The index of the mob to interact with.
	 */
	public PlayerActionMessage(InterfaceOption option, int index) {
		super(option);
		this.index = index;
	}

	/**
	 * Returns the index of this player.
	 */
	public int getIndex() {
		return index;
	}

}