package org.apollo.game.msg.handler;

import org.apollo.game.interact.ButtonActionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ButtonMessage;

/**
 * An {@link MessageHandler} which responds to {@link ButtonMessage}'s.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(ButtonMessage.class)
public final class ButtonMessageHandler implements MessageHandler<ButtonMessage> {

	@Override
	public void handle(Player player, ButtonMessage message) {
		if (message.getInterfaceId() < 0 || message.getInterfaceId() > InterfaceDefinition.count()) {
			return;
		}

		player.getWorld().post(new ButtonActionEvent(player, message.getInterfaceId()));
	}

}