package org.apollo.game.msg.handler;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inv.SynchronizationInventoryListener;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.SwitchItemMessage;

/**
 * An {@link MessageHandler} which updates an {@link Inventory} when the client
 * sends a {@link SwitchItemMessage} to the server.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(SwitchItemMessage.class)
public final class SwitchItemMessageHandler extends MessageHandler<SwitchItemMessage> {

    @Override
    public void handle(Player player, SwitchItemMessage message) {
	Inventory inventory = getInventoryForInterface(player, message.getInterfaceId());

	// Should never happen
	if (inventory == null) {
	    return;
	}

	if (message.getOldSlot() >= 0 && message.getNewSlot() >= 0 && message.getOldSlot() < inventory.capacity() && message.getNewSlot() < inventory.capacity()) {
	    inventory.swap(message.isInserting() && insertPermitted(message.getInterfaceId()), message.getOldSlot(), message.getNewSlot());
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
