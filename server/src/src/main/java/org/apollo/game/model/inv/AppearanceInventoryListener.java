package org.apollo.game.model.inv;

import org.apollo.game.model.Inventory;
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

	/**
	 * Updates the players appearance.
	 */
	private void update() {
		player.updateApprarance();
	}

	@Override
	public void itemUpdated(Inventory inventory, int slot, Item item) {
		update();
	}

	@Override
	public void itemsUpdated(Inventory inventory) {
		update();
	}

}