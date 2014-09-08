package org.apollo.game.msg.handler;

import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.DropItemMessage;

/**
 * This class handles the {@link DropItemMessage}.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
@HandlesMessage(DropItemMessage.class)
public class DropItemMessageHandler implements MessageHandler<DropItemMessage> {

	/**
	 * The world object.
	 */
	private final World world;

	/**
	 * Constructs a new instance of this class.
	 *
	 * @param world The world object.
	 */
	public DropItemMessageHandler(World world) {
		this.world = world;
	}

	@Override
	public void handle(Player player, DropItemMessage msg) {
		Item item = new Item(msg.getItemId(), player.getInventory().get(msg.getSlotId()).getAmount());
		player.getInventory().remove(item);
		world.register(new GroundItem(player, item, player.getPosition()));
	}

}