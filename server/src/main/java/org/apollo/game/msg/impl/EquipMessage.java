package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message sent by the client to request that an item is equipped.
 * 
 * @author Graham
 */
public final class EquipMessage extends Message {

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
     * Creates the equip message.
     * 
     * @param interfaceId The interface id.
     * @param id The id.
     * @param slot The slot.
     */
    public EquipMessage(int interfaceId, int id, int slot) {
	this.interfaceId = interfaceId;
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
