package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * Defines the message that handles using an item on another Player.
 * 
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public final class ItemOnPlayerMessage implements Message {

	/**
	 * The interface id.
	 */
	private int interfaceId;

	/**
	 * The victim player's id.
	 */
	private int victimId;

	/**
	 * The item id.
	 */
	private int itemId;

	/**
	 * The slot id.
	 */
	private int slotId;

	/**
	 * Constructs a new instance of this message.
	 * 
	 * @param interfaceId The interface id.
	 * @param victimId The victim player's id.
	 * @param itemId The item id.
	 * @param slotId The slot id.
	 */
	public ItemOnPlayerMessage(int interfaceId, int victimId, int itemId, int slotId) {
		this.interfaceId = interfaceId;
		this.victimId = victimId;
		this.itemId = itemId;
		this.slotId = slotId;
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
	 * Gets the victim player's id.
	 * 
	 * @return The victim player's id.
	 */
	public int getVictimId() {
		return victimId;
	}

	/**
	 * Gets the item id.
	 * 
	 * @return The item id.
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * Gets the slot id.
	 * 
	 * @return The slot id.
	 */
	public int getSlotId() {
		return slotId;
	}

}