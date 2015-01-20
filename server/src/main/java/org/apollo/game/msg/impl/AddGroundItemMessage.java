package org.apollo.game.msg.impl;

import org.apollo.game.model.grounditem.GroundItem;

/**
 * An implementation of a {@link GroundItemMessage} which sends an addition
 * request for the specified {@link GroundItem}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class AddGroundItemMessage extends GroundItemMessage {

	/**
	 * Constructs a new {@link AddGroundItemMessage} with the specified ground
	 * item and position offset.
	 *
	 * @param groundItem The ground item.
	 * @param positionOffset The position offset.
	 */
	public AddGroundItemMessage(GroundItem groundItem, int positionOffset) {
		super(groundItem, positionOffset);
	}

}