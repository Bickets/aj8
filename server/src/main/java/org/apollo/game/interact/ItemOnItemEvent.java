package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * This defines the type of event that is called when an item interacts with
 * another.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
public final class ItemOnItemEvent implements Event {

    /**
     * The player causing the two items to interact.
     */
    private final Player player;

    /**
     * The item getting interacted with.
     */
    private final Item receiver;

    /**
     * The item creating the interaction.
     */
    private final Item sender;

    /**
     * Creates an instance of this event.
     *
     * @param player The player causing the two items to interact.
     * @param receiver The slot of the item getting interacted with.
     * @param sender The slot of the item creating the interaction.
     */
    public ItemOnItemEvent(Player player, Item receiver, Item sender) {
	this.player = player;
	this.receiver = receiver;
	this.sender = sender;
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
     * Returns the item getting interacted with.
     *
     * @return The item getting interacted with.
     */
    public Item getReceiver() {
	return receiver;
    }

    /**
     * Returns the item creating the interaction.
     *
     * @return The item creating the interaction.
     */
    public Item getSender() {
	return sender;
    }

    /**
     * Tests if the specified items can be combined within this event.
     *
     * @param receiverId The received, used, items id.
     * @param senderId The senders, used with, items id.
     * @return Returns {@code true} if and only if the specified items can be
     *         combined.
     */
    public boolean canCombine(int receiverId, int senderId) {
	return receiver.getId() == receiverId && sender.getId() == senderId || receiver.getId() == senderId && sender.getId() == receiverId;
    }

}