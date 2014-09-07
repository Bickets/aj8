package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.Interfaces;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
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

	/**
	 * The world used to post item action events to this worlds event provider.
	 */
	private final World world;

	/**
	 * Constructs a new {@link ItemActionMessageHandler}.
	 *
	 * @param world The world.
	 */
	public ItemActionMessageHandler(World world) {
		this.world = world;
	}

	@Override
	public void handle(Player player, ItemActionMessage message) {
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

		if (!inventory.contains(message.getId())) {
			return;
		}

		Item item = inventory.get(message.getSlot());
		if (item == null || message.getId() != item.getId()) {
			return;
		}

		world.post(new ItemActionEvent(player, message.getOption(), message.getInterfaceId(), message.getId(), message.getSlot()));
	}

}