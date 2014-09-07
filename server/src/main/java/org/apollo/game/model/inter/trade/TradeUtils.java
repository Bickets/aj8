package org.apollo.game.model.inter.trade;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;

/**
 * Contains static utility methods for dealing with trade inventories.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeUtils {

	/**
	 * Offers an item onto the trade screen.
	 *
	 * @param player The player who is offering the item.
	 * @param slot The slot of the item to offer.
	 * @param id The id of the item to offer.
	 * @param amount The amount of the item to offer.
	 * @return {@code true} if and only if the specified item was offered
	 *         otherwise {@code false}.
	 */
	public static boolean offer(Player player, int slot, int id, int amount) {
		if (amount == 0) {
			return false;
		}

		Inventory inventory = player.getInventory();
		Inventory trade = player.getTrade();

		if (slot < 0 || slot >= inventory.capacity()) {
			return false;
		}

		Item item = inventory.get(slot);
		if (item.getId() != id) {
			return false;
		}

		if (trade.freeSlots() == 0 && !trade.contains(item.getId())) {
			trade.forceCapacityExceeded();
			return false;
		}

		int removed;
		if (amount > 1) {
			inventory.stopFiringEvents();
		}
		try {
			removed = inventory.remove(item.getId(), amount);
		} finally {
			if (amount > 1) {
				inventory.startFiringEvents();
			}
		}
		if (amount > 1) {
			inventory.forceRefresh();
		}
		trade.add(id, removed);

		return true;
	}

	/**
	 * Withdraws an item from the trade screen.
	 *
	 * @param player The player who is withdrawing the item.
	 * @param slot The slot of the item to withdraw.
	 * @param id The id of the item to withdraw.
	 * @param amount The amount of the item to withdraw.
	 * @return {@code true} if and only if the specified item was removed
	 *         otherwise {@code false}.
	 */
	public static boolean withdraw(Player player, int slot, int id, int amount) {
		if (amount == 0) {
			return false;
		}

		Inventory inventory = player.getInventory();
		Inventory trade = player.getTrade();

		if (slot < 0 || slot >= trade.capacity()) {
			return false;
		}

		Item item = trade.get(slot);
		if (item == null || item.getId() != id) {
			return false;
		}

		if (amount >= item.getAmount()) {
			amount = item.getAmount();
		}

		if (inventory.freeSlots() == 0 && !(inventory.contains(id) && ItemDefinition.forId(id).isStackable())) {
			inventory.forceCapacityExceeded();
			return false;
		}

		int remaining = inventory.add(id, amount);

		trade.stopFiringEvents();
		try {
			trade.remove(item.getId(), amount - remaining);
			trade.shift();
		} finally {
			trade.startFiringEvents();
		}

		trade.forceRefresh();
		return true;
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws InstantiationError If this class is instantiated within itself.
	 */
	private TradeUtils() {
		throw new InstantiationError("static-utility classes may not be instantiated.");
	}

}