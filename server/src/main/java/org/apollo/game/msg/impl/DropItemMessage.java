package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * This defines the message send by the client when the "Drop" option is pressed
 * on an item.
 * 
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public final class DropItemMessage implements Message {

	private final int itemId;

	private final int interfaceId;

	private final int slotId;

	public DropItemMessage(int itemId, int interfaceId, int slotId) {
		this.itemId = itemId;
		this.interfaceId = interfaceId;
		this.slotId = slotId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getInterfaceId() {
		return interfaceId;
	}

	public int getSlotId() {
		return slotId;
	}
}