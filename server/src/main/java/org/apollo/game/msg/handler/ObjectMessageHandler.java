package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ObjectActionMessage;

/**
 * Handles an object action for the {@link ObjectActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(ObjectActionMessage.class)
public final class ObjectMessageHandler extends MessageHandler<ObjectActionMessage> {

    /**
     * The world
     */
    private final World world;

    /**
     * Constructs a new {@link ButtonEvnetHandler}.
     *
     * @param world The world.
     */
    public ObjectMessageHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ObjectActionMessage message) {
	world.getInteractionHandler().dispatch(message.getId(), message.getOption(), player, message.getPosition());
    }

}
