package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * This class defines the message for when the player uses an item on another
 * item, in an inventory.
 * 
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public class ItemOnItemMessage extends Message {

    /**
     * The slot of the item getting interacted with.
     */
    private int receiverSlot;

    /**
     * The slot of the item creating the interaction.
     */
    private int senderSlot;

    /**
     * Constructs a new instance of this {@link Message}.
     * 
     * @param receiverSlot The slot of the item getting interacted with.
     * @param senderSlot The slot of the item creating the interaction.
     */
    public ItemOnItemMessage(int receiverSlot, int senderSlot) {
	this.receiverSlot = receiverSlot;
	this.senderSlot = senderSlot;
    }

    /**
     * Gets the slot of the receiver item.
     * 
     * @return The slot of the item getting interacted with.
     */
    public int getReceiverSlot() {
	return receiverSlot;
    }

    /**
     * Gets the slot of the sender item.
     * 
     * @return The slot of the item creating the interaction.
     */
    public int getSenderSlot() {
	return senderSlot;
    }
}