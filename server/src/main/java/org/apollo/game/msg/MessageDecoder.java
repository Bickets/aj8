package org.apollo.game.msg;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MessageDecoder} decodes a {@link GamePacket} into a {@link Message}
 * object which can be processed by the server.
 * 
 * <p>
 * This is a functional interface whose functional method is
 * {@link #decode(GamePacket)}
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The type of {@link Message}.
 */
@FunctionalInterface
public interface MessageDecoder<E extends Message> {

    /**
     * Decodes the specified packet into a message.
     *
     * @param packet The packet.
     * @return The message.
     */
    E decode(GamePacket packet);

}