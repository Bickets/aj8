package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemOnItemEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
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

    /**
     * The main world object.
     */
    private final World world;

    /**
     * Constructs a new {@link ItemOnItemMessageHandler}.
     *
     * @param world The world.
     */
    public ItemOnItemMessageHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ItemOnItemMessage msg) {
	world.post(new ItemOnItemEvent(player, msg.getReceiverSlot(), msg.getSenderSlot()));
    }

}