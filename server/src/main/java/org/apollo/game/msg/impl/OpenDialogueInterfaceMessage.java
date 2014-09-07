package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * This message is used to open an overlay over the chatbox.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class OpenDialogueInterfaceMessage implements Message {

	/**
	 * Represents the id of the interface.
	 */
	private final int interfaceId;

	/**
	 * Constructs a new {@link OpenDialogueInterfaceMessage}.
	 *
	 * @param interfaceId The id of the interface.
	 */
	public OpenDialogueInterfaceMessage(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * Returns the interface id.
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

}