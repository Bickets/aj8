package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.ChatMessage;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * A message handler which broadcasts public chat messages.
 *
 * @author Graham
 */
@HandlesMessage(ChatMessage.class)
public final class ChatMessageHandler extends MessageHandler<ChatMessage> {

    @Override
    public void handle(Player player, ChatMessage message) {
	int color = message.getTextColor();
	int effects = message.getTextEffects();
	if (color < 0 || color > 11 || effects < 0 || effects > 5) {
	    return;
	}

	player.getBlockSet().add(SynchronizationBlock.createChatBlock(player, message));
    }

}
