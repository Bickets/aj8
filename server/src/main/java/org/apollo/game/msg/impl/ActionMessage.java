package org.apollo.game.msg.impl;

import org.apollo.game.model.Interfaces.InterfaceOption;
import org.apollo.game.msg.Message;

/**
 * Represents an action message initiated by some interface option.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class ActionMessage implements Message {

	/**
	 * The interface option clicked which initiated this action.
	 */
	private final InterfaceOption option;

	/**
	 * Constructs a new {@link ActionMessage} with the specified option.
	 *
	 * @param option The interface option clicked which initiated this action.
	 */
	public ActionMessage(InterfaceOption option) {
		this.option = option;
	}

	/**
	 * Returns the interface option clicked.F
	 */
	public InterfaceOption getOption() {
		return option;
	}

}