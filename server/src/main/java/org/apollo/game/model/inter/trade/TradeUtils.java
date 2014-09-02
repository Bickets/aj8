package org.apollo.game.model.inter.trade;

import static org.apollo.game.model.InventoryConstants.OTHER_TRADE_INVENTORY_ID;
import static org.apollo.game.model.InventoryConstants.TRADE_INVENTORY_ID;
import static org.apollo.game.model.InventoryConstants.TRADE_SIDEBAR_INVENTORY_ID;
import static org.apollo.game.model.inter.trade.TradeConstants.SIDEBAR_ID;
import static org.apollo.game.model.inter.trade.TradeConstants.TRADE_WINDOW_ID;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.inter.InterfaceListener;
import org.apollo.game.model.inv.InventoryListener;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

/**
 * Contains static utility methods for dealing with trade inventories.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeUtils {

    /**
     * Opens a trade session between two players.
     *
     * @param player The player who instantiated the trade.
     * @param other The other player in the trade.
     */
    public static void openTrade(Player player, Player other) {
	TradeSession session = player.getFields().getTradeSession();
	TradeSession otherSession = other.getFields().getTradeSession();

	if (session != null || otherSession != null) {
	    return;
	}

	player.getFields().setTradeSession(new TradeSession(player, other));
	other.getFields().setTradeSession(new TradeSession(other, player));

	InventoryListener updateListener = new TradeInventoryListener(player.getFields().getTradeSession());

	register(player, updateListener, new SynchronizationInventoryListener(other, OTHER_TRADE_INVENTORY_ID));
	register(other, updateListener, new SynchronizationInventoryListener(player, OTHER_TRADE_INVENTORY_ID));

	initialize(player, TRADE_WINDOW_ID, SIDEBAR_ID);
	initialize(other, TRADE_WINDOW_ID, SIDEBAR_ID);
    }

    /**
     * Initializes a trade for the specified player.
     *
     * @param player The player to initialize the trade for.
     * @param windowId The window to open for this trade.
     * @param sidebarId The sidebar to open for this trade.
     */
    private static void initialize(Player player, int windowId, int sidebarId) {
	InventoryListener invListener = new SynchronizationInventoryListener(player, TRADE_SIDEBAR_INVENTORY_ID);
	InventoryListener tradeInvListener = new SynchronizationInventoryListener(player, TRADE_INVENTORY_ID);

	player.getInventory().addListener(invListener);
	player.getTrade().addListener(tradeInvListener);

	player.getInventory().forceRefresh();
	player.getTrade().forceRefresh();

	InterfaceListener interListener = new TradeInterfaceListener(player, invListener, tradeInvListener);
	player.getInterfaceSet().openWindowWithSidebar(interListener, windowId, sidebarId);
    }

    /**
     * Registers the update and synchronization inventory listeners to the trade
     * inventory.
     *
     * @param player The player who owns the trade inventory.
     * @param updateListener The update inventory listener.
     * @param syncListener The synchronization inventory listener.
     */
    private static void register(Player player, InventoryListener updateListener, InventoryListener syncListener) {
	player.getTrade().addListener(updateListener);
	player.getTrade().addListener(syncListener);
    }

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