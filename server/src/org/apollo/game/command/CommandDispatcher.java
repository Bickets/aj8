package org.apollo.game.command;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;

/**
 * A class which dispatches {@link Command}s to {@link CommandListener}s.
 * 
 * @author Graham
 */
public final class CommandDispatcher {

    /**
     * The singleton instance.
     */
    private static final CommandDispatcher INSTANCE = new CommandDispatcher();

    /**
     * A map of event listeners.
     */
    private final Map<String, CommandListener> listeners = new HashMap<>();

    /**
     * Default private constructor used to prevent this class from being
     * instantiated by other classes.
     */
    private CommandDispatcher() {

    }

    /**
     * Binds a command listener to the map.
     * 
     * @param listener The commands listener.
     */
    public void bind(CommandListener listener) {
	listeners.put(listener.getName(), listener);
    }

    /**
     * Unbinds a command listener from the map.
     * 
     * @param listener The command listener
     */
    public void unbind(CommandListener listener) {
	listeners.remove(listener.getName());
    }

    /**
     * Dispatches a command to the appropriate listener.
     * 
     * @param player The player.
     * @param command The command.
     */
    public void dispatch(Player player, Command command) {
	CommandListener listener = listeners.get(command.getName().toLowerCase());
	if (listener != null) {
	    listener.execute(player, command);
	}
    }

    /**
     * Gets the singleton instance.
     * 
     * @return the instance The singleton instance
     */
    public static CommandDispatcher getInstance() {
	return INSTANCE;
    }

}
