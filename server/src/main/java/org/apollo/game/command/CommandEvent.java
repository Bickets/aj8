package org.apollo.game.command;

import org.apollo.game.event.Event;

/**
 * Represents a command event.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class CommandEvent implements Event {

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
	 * @param name The name of the command.
	 * @param arguments The command's arguments.
	 */
	public CommandEvent(String name, String[] arguments) {
		this.name = name;
		this.arguments = arguments;
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