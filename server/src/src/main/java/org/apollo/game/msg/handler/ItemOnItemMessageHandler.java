package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemOnItemActionEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ItemOnItemMessage;

/**
 * This handles a {@link ItemOnItemMessage}.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
@HandlesMessage(ItemOnItemMessage.class)
public final class ItemOnItemMessageHandler implements MessageHandler<ItemOnItemMessage> {

	@Override
	public void handle(Player player, ItemOnItemMessage msg) {
		Inventory inventory = player.getInventory();

		if (player.getInterfaceSet().isOpen()) {
			player.getInterfaceSet().close();
		}

		if (msg.getReceiverSlot() < 0 || msg.getSenderSlot() < 0) {
			return;
		}

		if (msg.getReceiverSlot() >= inventory.capacity() || msg.getSenderSlot() >= inventory.capacity()) {
			return;
		}

		Item receiver = inventory.get(msg.getReceiverSlot());
		Item sender = inventory.get(msg.getSenderSlot());

		if (receiver == null || sender == null) {
			return;
		}

		if (!inventory.contains(receiver.getId()) || !inventory.contains(sender.getId())) {
			return;
		}

		player.post(new ItemOnItemActionEvent(receiver, sender));
	}

}