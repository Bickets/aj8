package org.apollo.game.msg.impl;

import org.apollo.game.msg.Message;

/**
 * A message issued by the client to send a {@code ::} command.
 *
 * @author Graham
 */
public final class CommandMessage extends Message {

    /**
     * The command.
     */
    private final String command;

    /**
     * Creates the command message.
     *
     * @param command The command.
     */
    public CommandMessage(String command) {
	this.command = command;
    }

    /**
     * Gets the command.
     *
     * @return The command.
     */
    public String getCommand() {
	return command;
    }

}