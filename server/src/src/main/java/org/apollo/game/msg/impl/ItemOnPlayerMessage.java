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
	private final int interfaceId;

	/**
	 * The victim player's index.
	 */
	private final int victimIndex;

	/**
	 * The item id.
	 */
	private final int id;

	/**
	 * The slot.
	 */
	private final int slot;

	/**
	 * Constructs a new instance of this message.
	 *
	 * @param interfaceId The interface id.
	 * @param victimIndex The victim player's index.
	 * @param id The item id.
	 * @param slot The slot.
	 */
	public ItemOnPlayerMessage(int interfaceId, int victimIndex, int id, int slot) {
		this.interfaceId = interfaceId;
		this.victimIndex = victimIndex;
		this.id = id;
		this.slot = slot;
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
	 * Gets the victim player's index.
	 *
	 * @return The victim player's index.
	 */
	public int getVictimIndex() {
		return victimIndex;
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