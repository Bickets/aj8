package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;

/**
 * This defines the type of event that is called when an item interacts with
 * another.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public class ItemOnItemEvent implements Event {

    /**
     * The player causing the two items to interact.
     */
    private Player player;

    /**
     * The slot of the item getting interacted with.
     */
    private int receiverSlot;

    /**
     * The slot of the item creating the interaction.
     */
    private int senderSlot;

    /**
     * Creates an instance of this event.
     *
     * @param player The player causing the two items to interact.
     * @param receiverSlot The slot of the item getting interacted with.
     * @param senderSlot The slot of the item creating the interaction.
     */
    public ItemOnItemEvent(Player player, int receiverSlot, int senderSlot) {
	this.player = player;
	this.receiverSlot = receiverSlot;
	this.senderSlot = senderSlot;
    }

    /**
     * The player causing the two items to interact.
     *
     * @return The player.
     */
    public Player getPlayer() {
	return player;
    }

    /**
     * The slot of the item getting interacted with.
     *
     * @return The slot of the item getting interacted with.
     */
    public int getReceiverSlot() {
	return receiverSlot;
    }

    /**
     * The slot of the item creating the interaction.
     *
     * @return The slot of the item creating the interaction.
     */
    public int getSenderSlot() {
	return senderSlot;
    }
}