package org.apollo.game.msg.handler;

import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.RegionLoadedMessage;

/**
 * A {@link MessageHandler} for the {@link RegionLoadedMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@HandlesMessage(RegionLoadedMessage.class)
public final class RegionLoadedMessageHandler implements MessageHandler<RegionLoadedMessage> {

	@Override
	public void handle(Player player, RegionLoadedMessage msg) {
	}

}