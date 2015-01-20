package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message sent by the client when the player has entered an amount.
 *
 * @author Graham
 */
public final class EnteredAmountMessage implements Message {

	/**
	 * The amount.
	 */
	private final int amount;

	/**
	 * Creates the entered amount message.
	 *
	 * @param amount The amount.
	 */
	public EnteredAmountMessage(int amount) {
		this.amount = amount;
	}

	/**
	 * Gets the amount.
	 *
	 * @return The amount.
	 */
	public int getAmount() {
		return amount;
	}

}