package org.apollo.game.model.inv;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.SlottedItem;
import org.apollo.game.msg.impl.UpdateItemsMessage;
import org.apollo.game.msg.impl.UpdateSlottedItemsMessage;

/**
 * An {@link InventoryListener} which synchronizes the state of the server's
 * inventory with the client's.
 *
 * @author Graham
 */
public final class SynchronizationInventoryListener extends InventoryAdapter {

	/**
	 * The player who we are synchronization their inventory for.
	 */
	private final Player player;

	/**
	 * The interface id of the inventory to synchronize.
	 */
	private final int interfaceId;

	/**
	 * Creates the synchronization inventory listener.
	 *
	 * @param player The player.
	 * @param interfaceId The interface id.
	 */
	public SynchronizationInventoryListener(Player player, int interfaceId) {
		this.player = player;
		this.interfaceId = interfaceId;
	}

	@Override
	public void itemUpdated(Inventory inventory, int slot, Item item) {
		player.send(new UpdateSlottedItemsMessage(interfaceId, new SlottedItem(slot, item)));
	}

	@Override
	public void itemsUpdated(Inventory inventory) {
		player.send(new UpdateItemsMessage(interfaceId, inventory.getItems()));
	}

}