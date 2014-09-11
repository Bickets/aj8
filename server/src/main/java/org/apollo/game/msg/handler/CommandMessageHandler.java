package org.apollo.game.msg.handler;

import org.apollo.game.command.CommandEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.MessageHandler;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.impl.CommandMessage;

/**
 * An {@link MessageHandler} which dispatches {@link CommandMessage}s.
 *
 * @author Graham
 */
@HandlesMessage(CommandMessage.class)
public final class CommandMessageHandler implements MessageHandler<CommandMessage> {

	/**
	 * The world used to post command events to this worlds event provider.
	 */
	private final World world;

	/**
	 * Constructs a new {@link CommandMessageHandler}.
	 *
	 * @param world The world.
	 */
	public CommandMessageHandler(World world) {
		this.world = world;
	}

	@Override
	public void handle(Player player, CommandMessage message) {
		/* Could a null string be sent? */
		String str = message.getCommand();
		String[] components = str.split(" ");

		String name = components[0];
		String[] arguments = new String[components.length - 1];

		System.arraycopy(components, 1, arguments, 0, arguments.length);

		world.post(new CommandEvent(player, name, arguments));
	}

}