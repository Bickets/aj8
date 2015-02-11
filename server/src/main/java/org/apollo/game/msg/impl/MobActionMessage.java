package org.apollo.game.msg.impl;

import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;

/**
 * An action message which represents some action at a mob.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobActionMessage extends ActionMessage {

	/**
	 * The index of the mob that was clicked.
	 */
	private final int index;

	/**
	 * Constructs a new {@link MobActionMessage} with the specified action and
	 * index.
	 *
	 * @param action The interface action clicked.
	 * @param index The index of the mob to interact with.
	 */
	public MobActionMessage(InteractContextMenuAction action, int index) {
		super(action);
		this.index = index;
	}

	/**
	 * Returns the index of this mob.
	 */
	public int getIndex() {
		return index;
	}

}