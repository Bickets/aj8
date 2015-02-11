package org.apollo.game.model.inv;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * An {@link InventoryListener} which updates the players appearance when any
 * items are updated.
 *
 * @author Graham
 */
public final class AppearanceInventoryListener extends InventoryAdapter {

	/**
	 * The player who is having their appearance updated.
	 */
	private final Player player;

	/**
	 * Creates the appearance inventory listener.
	 *
	 * @param player The player.
	 */
	public AppearanceInventoryListener(Player player) {
		this.player = player;
	}

	@Override
	public void itemUpdated(Inventory inventory, int slot, Item item) {
		player.updateApprarance();
	}

	@Override
	public void itemsUpdated(Inventory inventory) {
		player.updateApprarance();
	}

}