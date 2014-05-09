package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.impl.SwitchItemEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

/**
 * An {@link EventHandler} which updates an {@link Inventory} when the client
 * sends a {@link SwitchItemEvent} to the server.
 * 
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesEvent(SwitchItemEvent.class)
public final class SwitchItemEventHandler extends EventHandler<SwitchItemEvent> {

    @Override
    public void handle(Player player, SwitchItemEvent event) {
	Inventory inventory = getInventoryForInterface(player, event.getInterfaceId());

	// Should never happen
	if (inventory == null) {
	    return;
	}

	if (event.getOldSlot() >= 0 && event.getNewSlot() >= 0 && event.getOldSlot() < inventory.capacity() && event.getNewSlot() < inventory.capacity()) {
	    inventory.swap(event.isInserting() && insertPermitted(event.getInterfaceId()), event.getOldSlot(), event.getNewSlot());
	}
    }

    /**
     * Returns an {@link Inventory} for the specified interface id.
     * 
     * @param player The player who owns the inventory.
     * @param id The interface id.
     * @return The inventory for the specified interface id, {@code null} if not
     *         supported or does not exist.
     * @throws IllegalArgumentException If the specified interface id does not
     *             exist or is not supported.
     */
    private Inventory getInventoryForInterface(Player player, int id) {
	switch (id) {
	case SynchronizationInventoryListener.INVENTORY_ID:
	case BankConstants.SIDEBAR_INVENTORY_ID:
	    return player.getInventory();
	case SynchronizationInventoryListener.EQUIPMENT_ID:
	    return player.getEquipment();
	case BankConstants.BANK_INVENTORY_ID:
	    return player.getBank();
	}
	throw new IllegalArgumentException("Inventory not supported!");
    }

    /**
     * Returns {@code true} if the insert option is permitted for the specified
     * interface id.
     * 
     * @param id The interface id.
     * @return {@code true} if inserting is permitted for the specified
     *         interface id, otherwise return {@code false}.
     */
    private boolean insertPermitted(int id) {
	return id == BankConstants.BANK_INVENTORY_ID;
    }

}
