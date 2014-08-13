package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Interfaces.InterfaceOption;
import org.apollo.game.model.Player;

/**
 * An event which manages item actions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ItemActionEvent implements Event {

    /**
     * The player to perform the item action for.
     */
    private final Player player;

    /**
     * The interface id.
     */
    private final int interfaceId;

    /**
     * The items id.
     */
    private final int id;

    /**
     * The items slot.
     */
    private final int slot;

    /**
     * The interface option clicked.
     */
    private final InterfaceOption option;

    /**
     * Constructs a new {@link ItemActionEvent} with the specified player,
     * interface id, item id, item slot and interface option.
     *
     * @param player The player performing this event.
     * @param interfaceId The interface id.
     * @param id The items id.
     * @param slot The items slot.
     * @param option The interface option clicked.
     */
    public ItemActionEvent(Player player, int interfaceId, int id, int slot, InterfaceOption option) {
	this.player = player;
	this.interfaceId = interfaceId;
	this.id = id;
	this.slot = slot;
	this.option = option;
    }

    /**
     * Returns the player performing this event.
     */
    public Player getPlayer() {
	return player;
    }

    /**
     * Returns the interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

    /**
     * Returns the item id.
     */
    public int getId() {
	return id;
    }

    /**
     * Returns the items slot.
     */
    public int getSlot() {
	return slot;
    }

    /**
     * Returns the interface option clicked.
     */
    public InterfaceOption getOption() {
	return option;
    }

}