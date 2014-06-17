package org.apollo.game.model.inv;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.msg.Message;
import org.apollo.game.msg.impl.ServerMessageMessage;

/**
 * An {@link InventoryListener} which sends a message to a player when an
 * inventory has run out of space.
 *
 * @author Graham
 */
public final class FullInventoryListener extends InventoryAdapter {

    /**
     * The inventory full message.
     */
    public static final String FULL_INVENTORY_MESSAGE = "Not enough inventory space.";

    /**
     * The bank full message.
     */
    public static final String FULL_BANK_MESSAGE = "Not enough bank space.";

    /**
     * The equipment full message.
     */
    public static final String FULL_EQUIPMENT_MESSAGE = "Not enough equipment space.";

    /**
     * The player.
     */
    private final Player player;

    /**
     * The message to send when the capacity has been exceeded.
     */
    private final Message message;

    /**
     * Creates the empty inventory listener.
     *
     * @param player The player.
     * @param message The message to send when the inventory is empty.
     */
    public FullInventoryListener(Player player, String message) {
	this.player = player;
	this.message = new ServerMessageMessage(message);
    }

    @Override
    public void capacityExceeded(Inventory inventory) {
	player.send(message);
    }

}
