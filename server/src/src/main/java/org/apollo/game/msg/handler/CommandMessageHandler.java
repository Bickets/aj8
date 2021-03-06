package org.apollo.game.msg.handler;

import org.apollo.game.command.CommandEvent;
import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.CommandMessage;
import org.apollo.util.TextUtil;

/**
 * An {@link MessageHandler} which dispatches {@link CommandMessage}s.
 *
 * @author Graham
 */
@HandlesMessage(CommandMessage.class)
public final class CommandMessageHandler implements MessageHandler<CommandMessage> {

	@Override
	public void handle(Player player, CommandMessage message) {
		String str = message.getCommand();
		String[] components = TextUtil.parse(str);

		String name = components[0];
		String[] arguments = new String[components.length - 1];

		System.arraycopy(components, 1, arguments, 0, arguments.length);

		player.post(new CommandEvent(name, arguments));
	}

}