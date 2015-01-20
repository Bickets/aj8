package org.apollo.game.msg.impl;

import org.apollo.game.model.GroundItem;
import org.apollo.game.msg.Message;

/**
 * Defines a message related to a {@link GroundItem} for either the removal or
 * addition.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public final class GroundItemMessage implements Message {

	/**
	 * The ground item.
	 */
	private final GroundItem groundItem;

	/**
	 * The position offset.
	 */
	private final int positionOffset;

	/**
	 * This constructs a new ground item message.
	 *
	 * @param groundItem The ground item.
	 * @param positionOffset The position offset.
	 */
	public GroundItemMessage(GroundItem groundItem, int positionOffset) {
		this.groundItem = groundItem;
		this.positionOffset = positionOffset;
	}

	/**
	 * Get the ground item.
	 *
	 * @return The ground item.
	 */
	public GroundItem getGroundItem() {
		return groundItem;
	}

	/**
	 * Gets the position offset.
	 *
	 * @return The position offset.
	 */
	public int getPositionOffset() {
		return positionOffset;
	}

}