package org.apollo.game.msg;

import org.apollo.game.model.Player;

/**
 * A class which handles messages.
 *
 * <p>
 * This is a functional interface whose functional method is
 * {@link #handle(Player, Message)}
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The type of message this class handles.
 */
@FunctionalInterface
public interface MessageHandler<E extends Message> {

    /**
     * Handles a message.
     *
     * @param player The player.
     * @param msg The message.
     */
    void handle(Player player, E msg);

}