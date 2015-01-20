package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.Interfaces;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.InterfaceDefinition;
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
		if (player.getInterfaceSet().isOpen()) {
			player.getInterfaceSet().close();
		}

		if (message.getInterfaceId() < 0 || message.getInterfaceId() > InterfaceDefinition.count()) {
			return;
		}

		Inventory inventory = Interfaces.getInventoryForInterface(player, message.getInterfaceId());
		if (inventory == null) {
			return;
		}

		if (message.getSlot() < 0 || message.getSlot() >= inventory.capacity()) {
			return;
		}

		Item item = inventory.get(message.getSlot());
		if (item == null) {
			return;
		}

		if (!inventory.contains(item.getId())) {
			return;
		}

		player.post(new ItemActionEvent(message.getOption(), message.getInterfaceId(), message.getId(), message.getSlot()));
	}

}