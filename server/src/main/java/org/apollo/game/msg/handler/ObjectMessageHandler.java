package org.apollo.game.msg.handler;

import org.apollo.game.interact.ObjectActionEvent;
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
public final class ObjectMessageHandler implements MessageHandler<ObjectActionMessage> {

    /**
     * The world used to post object action events to this worlds event provider.
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
	// TODO: Distance checks, validity checks
	world.post(new ObjectActionEvent(player, message.getId(), message.getOption(), message.getPosition()));
    }

}
