package org.apollo.game.msg.handler;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandDispatcher;
import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.CommandMessage;

/**
 * An {@link MessageHandler} which dispatches {@link CommandMessage}s.
 *
 * @author Graham
 */
@HandlesMessage(CommandMessage.class)
public final class CommandMessageHandler extends MessageHandler<CommandMessage> {

    @Override
    public void handle(Player player, CommandMessage message) {
	String str = message.getCommand();
	String[] components = str.split(" ");

	String name = components[0];
	String[] arguments = new String[components.length - 1];

	System.arraycopy(components, 1, arguments, 0, arguments.length);

	Command command = new Command(name, arguments);

	CommandDispatcher.getInstance().dispatch(player, command);
    }

}
