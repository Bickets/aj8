package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.SwitchItemEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

/**
 * An {@link EventHandler} which updates an {@link Inventory} when the client
 * sends a {@link SwitchItemEvent} to the server.
 * @author Graham
 */
public final class SwitchItemEventHandler extends EventHandler<SwitchItemEvent> {

    @Override
    public void handle(Player player, SwitchItemEvent event) {
        Inventory inventory = getInventoryForInterface(player, event.getInterfaceId());

        if (inventory == null) {
            return;
        }

        if (event.getOldSlot() >= 0 && event.getNewSlot() >= 0 && event.getOldSlot() < inventory.capacity() && event.getNewSlot() < inventory.capacity()) {
            inventory.swap(insertPermitted(event.getInterfaceId()) ? event.isInserting() : false, event.getOldSlot(), event.getNewSlot());
        }
    }

    private Inventory getInventoryForInterface(Player player, int id) {
        switch (id) {
        case SynchronizationInventoryListener.INVENTORY_ID:
        case BankConstants.SIDEBAR_INVENTORY_ID:
            return player.getInventory();
        case SynchronizationInventoryListener.EQUIPMENT_ID:
            return player.getEquipment();
        case BankConstants.BANK_INVENTORY_ID:
            return player.getBank();
        default:
            return null;
        }
    }

    private boolean insertPermitted(int id) {
        return id == BankConstants.BANK_INVENTORY_ID;
    }

}
