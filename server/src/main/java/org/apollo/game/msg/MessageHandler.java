package org.apollo.game.msg;

import org.apollo.game.model.Player;

/**
 * A class which handles messages.
 * 
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The type of message this class handles.
 */
public abstract class MessageHandler<E extends Message> {

    /**
     * Handles a message.
     * 
     * @param player The player.
     * @param msg The message.
     */
    public abstract void handle(Player player, E msg);

}
