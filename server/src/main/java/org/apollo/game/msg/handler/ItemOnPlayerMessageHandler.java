package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemOnPlayerActionEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
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

	/**
	 * The main world object.
	 */
	private final World world;

	/**
	 * Constructs a new instance of this class.
	 *
	 * @param world The world.
	 */
	public ItemOnPlayerMessageHandler(World world) {
		this.world = world;
	}

	@Override
	public void handle(Player player, ItemOnPlayerMessage msg) {
		Item item = new Item(msg.getItemId());

		world.post(new ItemOnPlayerActionEvent(player, world.getPlayerRepository().get(msg.getVictimId()), item, msg.getInterfaceId()));
	}
}