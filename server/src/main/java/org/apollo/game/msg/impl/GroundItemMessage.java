package org.apollo.game.msg.impl;

import org.apollo.game.model.grounditem.GroundItem;
import org.apollo.game.msg.Message;

/**
 * Defines a message related to a {@link GroundItem} for either the removal or
 * addition.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
abstract class GroundItemMessage implements Message {

	/**
	 * The ground item.
	 */
	private final GroundItem groundItem;

	/**
	 * The position offset.
	 */
	private final int positionOffset;

	/**
	 * Constructs a new {@link GroundItemMessage} with the specified ground item
	 * and position offset.
	 *
	 * @param groundItem The ground item.
	 * @param positionOffset The position offset.
	 */
	protected GroundItemMessage(GroundItem groundItem, int positionOffset) {
		this.groundItem = groundItem;
		this.positionOffset = positionOffset;
	}

	/**
	 * Get the ground item.
	 *
	 * @return The ground item.
	 */
	public final GroundItem getGroundItem() {
		return groundItem;
	}

	/**
	 * Gets the position offset.
	 *
	 * @return The position offset.
	 */
	public final int getPositionOffset() {
		return positionOffset;
	}

}