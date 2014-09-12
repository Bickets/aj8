package org.apollo.game.msg.handler;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.interact.ItemOnPlayerActionEvent;
import org.apollo.game.model.GameCharacterRepository;
import org.apollo.game.model.Interfaces;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.def.InterfaceDefinition;
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
			return;
		}

		GameCharacterRepository<Player> repository = world.getPlayerRepository();

		int victimIndex = message.getVictimIndex();
		if (victimIndex < 1 || victimIndex >= repository.capacity()) {
			return;
		}

		Player victim = repository.get(victimIndex);
		if (victim == null || victimIndex != victim.getIndex()) {
			return;
		}

		if (victim.getInterfaceSet().isOpen()) {
			player.sendMessage("Other player is busy at the moment.");
			return;
		}

		if (!player.getPosition().isWithinDistance(victim.getPosition(), player.getViewingDistance() + 1)) {
			return;
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

		if (!inventory.contains(message.getId())) {
			return;
		}

		Item item = inventory.get(message.getSlot());
		if (item == null || message.getId() != item.getId()) {
			return;
		}

		player.startAction(new DistancedAction<Player>(0, true, player, victim.getPosition(), victim.getSize()) {
			@Override
			public void executeAction() {
				player.turnTo(victim.getPosition());
				world.post(new ItemOnPlayerActionEvent(player, victim, item, message.getInterfaceId()));
				stop();
			}
		});
	}

}