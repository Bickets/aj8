package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.EnteredAmountMessage;

/**
 * An {@link MessageHandler} for the {@link EnteredAmountMessage}.
 *
 * @author Graham
 */
@HandlesMessage(EnteredAmountMessage.class)
public final class EnteredAmountMessageHandler implements MessageHandler<EnteredAmountMessage> {

    @Override
    public void handle(Player player, EnteredAmountMessage message) {
	int amount = message.getAmount();

	if (amount < 1) {
	    amount = 1;
	}

	player.getInterfaceSet().enteredAmount(amount);
    }

}