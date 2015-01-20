package org.apollo.game.msg.handler;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.ItemOnPlayerActionEvent;
import org.apollo.game.model.GameCharacterRepository;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.game.model.inv.Inventory;
import org.apollo.game.model.inv.InventorySupplier;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ItemOnPlayerMessage;

/**
 * This class handles the message that is sent when an item is used on another
 * player.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
@HandlesMessage(ItemOnPlayerMessage.class)
public final class ItemOnPlayerMessageHandler implements MessageHandler<ItemOnPlayerMessage> {

	@Override
	public void handle(Player player, ItemOnPlayerMessage message) {
		World world = player.getWorld();

		if (player.getInterfaceSet().isOpen()) {
			player.getInterfaceSet().close();
		}

		if (!player.getAttributes().isClientWindowFocused()) {
			return;
		}

		GameCharacterRepository<Player> repository = world.getPlayerRepository();

		int victimIndex = message.getVictimIndex();
		if (victimIndex < 1 || victimIndex >= repository.capacity()) {
			player.getWalkingQueue().clear();
			return;
		}

		Player victim = repository.get(victimIndex);
		if (victim == null || victimIndex != victim.getIndex()) {
			player.getWalkingQueue().clear();
			return;
		}

		if (victim.getInterfaceSet().isOpen()) {
			player.sendMessage("Other player is busy at the moment.");
			return;
		}

		if (!player.getPosition().isWithinDistance(victim.getPosition(), player.getViewingDistance() + 1)) {
			player.getWalkingQueue().clear();
			return;
		}

		if (message.getInterfaceId() < 0 || message.getInterfaceId() > InterfaceDefinition.count()) {
			player.getWalkingQueue().clear();
			return;
		}

		InventorySupplier supplier = Inventory.getInventory(message.getInterfaceId());
		if (supplier == null) {
			player.getWalkingQueue().clear();
			return;
		}

		Inventory inventory = supplier.getInventory(player);

		if (message.getSlot() < 0 || message.getSlot() >= inventory.capacity()) {
			player.getWalkingQueue().clear();
			return;
		}

		Item item = inventory.get(message.getSlot());
		if (item == null) {
			player.getWalkingQueue().clear();
			return;
		}

		if (!inventory.contains(item.getId())) {
			player.getWalkingQueue().clear();
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, victim.getPosition(), victim.getSize()) {
			@Override
			public void executeAction() {
				player.turnTo(victim.getPosition());
				player.post(new ItemOnPlayerActionEvent(victim, item, message.getInterfaceId()));
				stop();
			}
		});
	}

}