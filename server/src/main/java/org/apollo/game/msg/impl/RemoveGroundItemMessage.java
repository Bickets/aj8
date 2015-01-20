package org.apollo.game.msg.impl;

import org.apollo.game.model.grounditem.GroundItem;

/**
 * An implementation of a {@link GroundItemMessage} which sends a removal
 * request for the specified {@link GroundItem}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class RemoveGroundItemMessage extends GroundItemMessage {

	/**
	 * Constructs a new {@link RemoveGroundItemMessage} with the specified
	 * ground item and position offset.
	 *
	 * @param groundItem The ground item.
	 * @param positionOffset The position offset.
	 */
	public RemoveGroundItemMessage(GroundItem groundItem, int positionOffset) {
		super(groundItem, positionOffset);
	}

}