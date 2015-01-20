package org.apollo.game.msg.impl;

import org.apollo.game.model.SlottedItem;
import org.apollo.game.msg.Message;

/**
 * A message which updates a single item in an interface.
 *
 * @author Graham
 */
public final class UpdateSlottedItemsMessage implements Message {

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * The slotted items.
	 */
	private final SlottedItem[] items;

	/**
	 * Creates the update item in interface message.
	 *
	 * @param interfaceId The interface id.
	 * @param items The slotted items.
	 */
	public UpdateSlottedItemsMessage(int interfaceId, SlottedItem... items) {
		this.interfaceId = interfaceId;
		this.items = items;
	}

	/**
	 * Gets the interface id.
	 *
	 * @return The interface id.
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * Gets an array of slotted items.
	 *
	 * @return The slotted items.
	 */
	public SlottedItem[] getSlottedItems() {
		return items;
	}

}