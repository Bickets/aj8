package org.apollo.game.msg.impl;

import org.apollo.game.model.Position;

/**
 * A hint icon displayed over a position.
 *
 * @author James Monger
 */
public final class PositionHintIconMessage extends HintIconMessage {

	/**
	 * The target position.
	 */
	private final Position position;

	/**
	 * The draw height.
	 */
	private final int drawHeight;

	/**
	 * Creates the {@link PositionHintIconMessage}.
	 *
	 * @param type The type.
	 * @param position The position to display the icon over.
	 * @param drawHeight The icon draw height.
	 */
	public PositionHintIconMessage(HintIconType type, Position position, int drawHeight) {
		super(type);
		this.position = position;
		this.drawHeight = drawHeight;
	}

	/**
	 * Gets the target position.
	 *
	 * @return The target position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the draw height.
	 *
	 * @return The draw height.
	 */
	public int getDrawHeight() {
		return drawHeight;
	}

}