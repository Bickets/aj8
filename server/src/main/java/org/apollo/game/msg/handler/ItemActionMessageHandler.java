package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.game.model.inv.Inventory;
import org.apollo.game.model.inv.InventorySupplier;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ItemActionMessage;

/**
 * A message handler which handles the {@link ItemActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Graham
 */
@HandlesMessage(ItemActionMessage.class)
public final class ItemActionMessageHandler implements MessageHandler<ItemActionMessage> {

	@Override
	public void handle(Player player, ItemActionMessage message) {
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

		if (message.getSlot() < 0 || message.getSlot() >= inventory.capacity()) {
			player.getInterfaceSet().close();
			return;
		}

		Item item = inventory.get(message.getSlot());
		if (item == null) {
			player.getInterfaceSet().close();
			return;
		}

		if (!inventory.contains(item.getId())) {
			player.getInterfaceSet().close();
			return;
		}

		player.post(new ItemActionEvent(message.getOption(), message.getInterfaceId(), message.getId(), message.getSlot()));
	}

}