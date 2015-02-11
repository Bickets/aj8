package org.apollo.game.msg.impl;

import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;
import org.apollo.game.msg.Message;

/**
 * Represents an action message initiated by some interface option.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class ActionMessage implements Message {

	/**
	 * The interface action clicked which initiated this action.
	 */
	private final InteractContextMenuAction action;

	/**
	 * Constructs a new {@link ActionMessage} with the specified action.
	 *
	 * @param action The interface action clicked which initiated this action.
	 */
	protected ActionMessage(InteractContextMenuAction action) {
		this.action = action;
	}

	/**
	 * Returns the interface action clicked.
	 */
	public final InteractContextMenuAction getAction() {
		return action;
	}

}