package org.apollo.game.msg.handler;

import org.apollo.game.interact.ItemActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ItemActionMessage;

/**
 * A message handler which handles item actions.
 *
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
	world.post(new ItemActionEvent(player, message.getInterfaceId(), message.getId(), message.getSlot(), message.getOption()));
    }

}