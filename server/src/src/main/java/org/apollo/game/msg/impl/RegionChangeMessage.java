package org.apollo.game.msg.impl;

import org.apollo.game.model.Position;
import org.apollo.game.msg.Message;

/**
 * A message which indicates that the client should load the specified region.
 *
 * @author Graham
 */
public final class RegionChangeMessage implements Message {

	/**
	 * The position of the region to load.
	 */
	private final Position position;

	/**
	 * Creates the region changed message.
	 *
	 * @param position The position of the region.
	 */
	public RegionChangeMessage(Position position) {
		this.position = position;
	}

	/**
	 * Gets the position of the region to load.
	 *
	 * @return The position of the region to load.
	 */
	public Position getPosition() {
		return position;
	}

}