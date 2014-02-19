
package org.apollo.game.command;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;

/**
 * A class which dispatches {@link Command}s to {@link CommandListener}s.
 * @author Graham
 */
public final class CommandDispatcher
{

	/**
	 * The singleton instance.
	 */
	private static final CommandDispatcher INSTANCE = new CommandDispatcher();

	/**
	 * A map of event listeners.
	 */
	private final Map<String, CommandListener> listeners = new HashMap<String, CommandListener>();


	/**
	 * Creates the command dispatcher and registers a listener for the credits
	 * command.
	 */
	private CommandDispatcher()
	{

	}


	/**
	 * Adds a command listener to the map.
	 * @param name The commands name
	 * @param listener The commands listener.
	 */
	public void addListener( String name, CommandListener listener )
	{
		listeners.put( name, listener );
	}


	/**
	 * Dispatches a command to the appropriate listener.
	 * @param player The player.
	 * @param command The command.
	 */
	public void dispatch( Player player, Command command )
	{
		CommandListener listener = listeners.get( command.getName().toLowerCase() );
		if( listener != null ) {
			listener.execute( player, command );
		}
	}


	/**
	 * Gets the singleton instance.
	 * @return the instance The singleton instance
	 */
	public static CommandDispatcher getInstance()
	{
		return INSTANCE;
	}

}
