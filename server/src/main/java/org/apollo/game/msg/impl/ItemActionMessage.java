package org.apollo.game.msg.impl;

import org.apollo.game.model.inter.Interfaces.InterfaceOption;
import org.apollo.game.msg.Message;

/**
 * A {@link Message} which represents some sort of action on an item.
 *
 * @author Graham
 */
public class ItemActionMessage implements Message {

	/**
	 * The interface option.
	 */
	private final InterfaceOption option;

	/**
	 * The interface id.
	 */
	private final int interfaceId;

	/**
	 * The item id.
	 */
	private final int id;

	/**
	 * The item's slot.
	 */
	private final int slot;

	/**
	 * Creates the item action message.
	 *
	 * @param option The interface option.
	 * @param interfaceId The interface id.
	 * @param id The id.
	 * @param slot The slot.
	 */
	public ItemActionMessage(InterfaceOption option, int interfaceId, int id, int slot) {
		this.option = option;
		this.interfaceId = interfaceId;
		this.id = id;
		this.slot = slot;
	}

	/**
	 * Gets the interface option.
	 *
	 * @return The interface option.
	 */
	public InterfaceOption getOption() {
		return option;
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
	 * Gets the item id.
	 *
	 * @return The item id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the slot.
	 *
	 * @return The slot.
	 */
	public int getSlot() {
		return slot;
	}

}