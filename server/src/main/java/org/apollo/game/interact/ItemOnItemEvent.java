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
    private Player player;

    /**
     * The item getting interacted with.
     */
    private Item receiver;

    /**
     * The item creating the interaction.
     */
    private Item sender;

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
}