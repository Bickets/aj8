package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.game.model.inter.Interfaces;
import org.apollo.game.model.inv.Inventory;
import org.apollo.game.model.inv.InventorySupplier;
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
public final class SwitchItemMessageHandler implements MessageHandler<SwitchItemMessage> {

	@Override
	public void handle(Player player, SwitchItemMessage message) {
		if (message.getOldSlot() < 0 || message.getNewSlot() < 0) {
			player.getInterfaceSet().close();
			return;
		}

		if (message.getInterfaceId() < 0 || message.getInterfaceId() > InterfaceDefinition.count()) {
			player.getInterfaceSet().close();
			return;
		}

		if (!player.getAttributes().isClientWindowFocused()) {
			player.getInterfaceSet().close();
			return;
		}

		InventorySupplier supplier = Inventory.getInventory(message.getInterfaceId());
		if (supplier == null) {
			player.getInterfaceSet().close();
			return;
		}

		Inventory inventory = supplier.getInventory(player);

		if (message.getOldSlot() < inventory.capacity() && message.getNewSlot() < inventory.capacity()) {
			inventory.swap(message.isInserting() && Interfaces.insertPermitted(message.getInterfaceId()), message.getOldSlot(), message.getNewSlot());
		}
	}

}