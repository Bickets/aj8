package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ButtonMessage;

/**
 * An {@link MessageHandler} which responds to {@link ButtonMessage}'s.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(ButtonMessage.class)
public final class ButtonMessageHandler extends MessageHandler<ButtonMessage> {

    /**
     * The world used to get this worlds interaction handler.
     */
    private final World world;

    /**
     * Constructs a new {@link ButtonEvnetHandler}.
     *
     * @param world The world.
     */
    public ButtonMessageHandler(World world) {
	this.world = world;
    }

    @Override
    public void handle(Player player, ButtonMessage message) {
	world.getInteractionHandler().dispatch(message.getInterfaceId(), player);
    }

}
