package org.apollo.game.model.inter.bank;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.InventoryConstants;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.inter.InterfaceListener;
import org.apollo.game.model.inv.InventoryListener;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

/**
 * Contains bank-related utility methods.
 *
 * @author Graham
 */
public final class BankUtils {

    /**
     * Opens a players bank.
     *
     * @param player The player.
     */
    public static void openBank(Player player) {
	InventoryListener invListener = new SynchronizationInventoryListener(player, InventoryConstants.BANK_SIDEBAR_INVENTORY_ID);
	InventoryListener bankListener = new SynchronizationInventoryListener(player, InventoryConstants.BANK_INVENTORY_ID);

	player.getInventory().addListener(invListener);
	player.getBank().addListener(bankListener);

	player.getInventory().forceRefresh();
	player.getBank().forceRefresh();

	InterfaceListener interListener = new BankInterfaceListener(player, invListener, bankListener);

	player.getInterfaceSet().openWindowWithSidebar(interListener, BankConstants.BANK_WINDOW_ID, BankConstants.SIDEBAR_ID);
    }

    /**
     * Deposits an item into the players bank.
     *
     * @param player The player.
     * @param slot The slot.
     * @param id The id.
     * @param amount The amount.
     * @return {@code true} if and only if the specified item was deposited
     *         otherwise {@code false}.
     */
    public static boolean deposit(Player player, int slot, int id, int amount) {
	if (amount == 0) {
	    return false;
	}

	Inventory inventory = player.getInventory();
	Inventory bank = player.getBank();

	if (slot < 0 || slot >= inventory.capacity()) {
	    return false;
	}

	Item item = inventory.get(slot);
	if (item.getId() != id) {
	    return false;
	}

	int newId = ItemDefinition.noteToItem(item.getId());

	if (bank.freeSlots() == 0 && !bank.contains(item.getId())) {
	    bank.forceCapacityExceeded();
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
	bank.add(newId, removed);

	return true;
    }

    /**
     * Withdraws an item from a players bank.
     *
     * @param player The player.
     * @param slot The slot.
     * @param id The id.
     * @param amount The amount.
     * @return {@code true} if and only if the specified item was removed
     *         otherwise {@code false}.
     */
    public static boolean withdraw(Player player, int slot, int id, int amount) {
	if (amount == 0) {
	    return false;
	}

	Inventory inventory = player.getInventory();
	Inventory bank = player.getBank();

	if (slot < 0 || slot >= bank.capacity()) {
	    return false;
	}

	Item item = bank.get(slot);
	if (item == null || item.getId() != id) {
	    return false;
	}

	if (amount >= item.getAmount()) {
	    amount = item.getAmount();
	}

	int newId = player.getFields().isWithdrawingNotes() ? ItemDefinition.itemToNote(item.getId()) : item.getId();

	if (inventory.freeSlots() == 0 && !(inventory.contains(newId) && ItemDefinition.forId(newId).isStackable())) {
	    inventory.forceCapacityExceeded();
	    return false;
	}

	int remaining = inventory.add(newId, amount);

	bank.stopFiringEvents();
	try {
	    bank.remove(item.getId(), amount - remaining);
	    bank.shift();
	} finally {
	    bank.startFiringEvents();
	}

	bank.forceRefresh();
	return true;
    }

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private BankUtils() {
	throw new InstantiationError("static-utility classes may not be instantiated.");
    }

}