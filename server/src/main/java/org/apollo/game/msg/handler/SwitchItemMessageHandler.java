package org.apollo.game.msg.handler;

import org.apollo.game.model.Interfaces;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.SwitchItemMessage;

/**
 * An {@link MessageHandler} which updates an {@link Inventory} when the client
 * sends a {@link SwitchItemMessage} to the server.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(SwitchItemMessage.class)
public final class SwitchItemMessageHandler extends MessageHandler<SwitchItemMessage> {

    @Override
    public void handle(Player player, SwitchItemMessage message) {
	if (message.getOldSlot() < 0 || message.getNewSlot() < 0) {
	    return;
	}

	Inventory inventory = Interfaces.getInventoryForInterface(player, message.getInterfaceId());

	// Should never happen
	if (inventory == null) {
	    return;
	}

	if (message.getOldSlot() < inventory.capacity() && message.getNewSlot() < inventory.capacity()) {
	    inventory.swap(message.isInserting() && Interfaces.insertPermitted(message.getInterfaceId()), message.getOldSlot(), message.getNewSlot());
	}
    }

}
