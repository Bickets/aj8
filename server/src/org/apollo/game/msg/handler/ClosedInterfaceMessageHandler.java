package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ClosedInterfaceMessage;

/**
 * An {@link MessageHandler} for the {@link ClosedInterfaceMessage}.
 * 
 * @author Graham
 */
@HandlesMessage(ClosedInterfaceMessage.class)
public final class ClosedInterfaceMessageHandler extends MessageHandler<ClosedInterfaceMessage> {

    @Override
    public void handle(Player player, ClosedInterfaceMessage message) {
	player.getInterfaceSet().interfaceClosed();
    }

}
