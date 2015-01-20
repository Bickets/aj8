package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message which is sent by the client when the player has clicked the
 * "Click here to continue" button on a dialogue interface.
 *
 * @author Chris Fletcher
 */
public final class DialogueContinueMessage implements Message {

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * Creates a new dialogue continue message.
	 *
	 * @param interfaceId The interface id.
	 */
	public DialogueContinueMessage(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * Gets the interface id of the button.
	 *
	 * @return The interface id.
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

}