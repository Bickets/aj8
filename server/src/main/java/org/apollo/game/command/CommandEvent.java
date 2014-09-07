package org.apollo.game.command;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;

/**
 * Represents a command event.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class CommandEvent implements Event {

	/**
	 * The player executing this command.
	 */
	private final Player player;

	/**
	 * The name of the command.
	 */
	private final String name;

	/**
	 * The command's arguments.
	 */
	private final String[] arguments;

	/**
	 * Creates the command.
	 *
	 * @param player The player executing this command.
	 * @param name The name of the command.
	 * @param arguments The command's arguments.
	 */
	public CommandEvent(Player player, String name, String[] arguments) {
		this.player = player;
		this.name = name;
		this.arguments = arguments;
	}

	/**
	 * Returns the player executing this command.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns this name of this command.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the arguments of this command.
	 */
	public String[] getArguments() {
		return arguments;
	}

}