package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.ItemActionEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inter.bank.BankDepositEnterAmountListener;
import org.apollo.game.model.inter.bank.BankUtils;
import org.apollo.game.model.inter.bank.BankWithdrawEnterAmountListener;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

/**
 * An event handler which handles item actions.
 * @author Graham
 */
public final class ItemActionEventHandler extends EventHandler<ItemActionEvent> {

    @Override
    public void handle(Player player, ItemActionEvent event) {
        if (!player.getInterfaceSet().contains(BankConstants.BANK_WINDOW_ID)) {
            return;
        }

        if (event.getInterfaceId() == BankConstants.SIDEBAR_INVENTORY_ID) {
            deposit(player, event);
        } else if (event.getInterfaceId() == BankConstants.BANK_INVENTORY_ID) {
            withdraw(player, event);
        } else if (event.getOption() == 1 && event.getInterfaceId() == SynchronizationInventoryListener.EQUIPMENT_ID) {
            remove(player, event);
        }
    }

    /**
     * Removes an item from the equipment inventory.
     * @param player	The player.
     * @param event	The item event.
     */
    private void remove(Player player, ItemActionEvent event) {
        Inventory inventory = player.getInventory();
        Inventory equipment = player.getEquipment();

        boolean hasRoomForStackable = inventory.contains(event.getId()) && inventory.get(event.getSlot()).getDefinition().isStackable();
        if (inventory.freeSlots() < 1 && !hasRoomForStackable) {
            inventory.forceCapacityExceeded();
            return;
        }

        int slot = event.getSlot();
        if (slot < 0 || slot >= equipment.capacity()) {
            return;
        }

        Item item = equipment.get(slot);
        if (item == null || item.getId() != event.getId()) {
            return;
        }

        boolean removed = true;

        inventory.stopFiringEvents();
        equipment.stopFiringEvents();

        try {
            equipment.set(slot, null);
            Item copy = item;
            inventory.add(item.getId(), item.getAmount());
            if (copy != null) {
                removed = false;
                equipment.set(slot, copy);
            }
        } finally {
            inventory.startFiringEvents();
            equipment.startFiringEvents();
        }

        if (removed) {
            // TODO: Should we only refresh the slot that got used?
            inventory.forceRefresh();
            equipment.forceRefresh();
        } else {
            inventory.forceCapacityExceeded();
        }
    }

    /**
     * Handles a withdraw action.
     * @param ctx The event handler context.
     * @param player The player.
     * @param event The event.
     */
    private void withdraw(Player player, ItemActionEvent event) {
        int amount = optionToAmount(event.getOption());
        if (amount == -1) {
            player.getInterfaceSet().openEnterAmountDialog(new BankWithdrawEnterAmountListener(player, event.getSlot(), event.getId()));
        } else {
            if (!BankUtils.withdraw(player, event.getSlot(), event.getId(), amount)) {
                return;
            }
        }
    }

    /**
     * Handles a deposit action.
     * @param ctx The event handler context.
     * @param player The player.
     * @param event The event.
     */
    private void deposit(Player player, ItemActionEvent event) {
        int amount = optionToAmount(event.getOption());
        if (amount == -1) {
            player.getInterfaceSet().openEnterAmountDialog(new BankDepositEnterAmountListener(player, event.getSlot(), event.getId()));
        } else {
            if (!BankUtils.deposit(player, event.getSlot(), event.getId(), amount)) {
                return;
            }
        }
    }

    /**
     * Converts an option to an amount.
     * @param option The option.
     * @return The amount.
     * @throws IllegalArgumentException if the option is not legal.
     */
    private static final int optionToAmount(int option) {
        switch (option) {
        case 1:
            return 1;
        case 2:
            return 5;
        case 3:
            return 10;
        case 4:
            return Integer.MAX_VALUE;
        case 5:
            return -1;
        }
        throw new IllegalArgumentException();
    }

}
