package org.apollo.game.msg.handler;

import java.util.Arrays;

import org.apollo.game.command.CommandEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
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

    /**
     * The world used to post command events to this worlds event provider.
     */
    private final World world;

    /**
     * Constructs a new {@link ButtonEvnetHandler}.
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
	String[] components = TextUtil.split(str);

	String name = components[0];
	String[] arguments = Arrays.copyOfRange(components, 1, components.length - 1);

	world.post(new CommandEvent(player, name, arguments));
    }

}