package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.game.model.inter.InterfaceType;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.DialogueContinueMessage;

/**
 * An {@link MessageHandler} for the {@link DialogueContinueMessage}.
 *
 * @author Chris Fletcher
 */
@HandlesMessage(DialogueContinueMessage.class)
public final class DialogueContinueMessageHandler implements MessageHandler<DialogueContinueMessage> {

	@Override
	public void handle(Player player, DialogueContinueMessage message) {
		if (message.getInterfaceId() < 0 || message.getInterfaceId() > InterfaceDefinition.count()) {
			player.getInterfaceSet().close();
			return;
		}

		if (!player.getInterfaceSet().contains(InterfaceType.DIALOGUE)) {
			player.getInterfaceSet().close();
			return;
		}

		if (!player.getAttributes().isClientWindowFocused()) {
			player.getInterfaceSet().close();
			return;
		}

		player.getInterfaceSet().continueRequested();
	}

}