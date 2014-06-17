package org.apollo.game.msg;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MessageDecoder} decodes a {@link GamePacket} into a {@link Message}
 * object which can be processed by the server.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The type of {@link Message}.
 */
public abstract class MessageDecoder<E extends Message> {

    /**
     * Decodes the specified packet into a message.
     *
     * @param packet The packet.
     * @return The message.
     */
    public abstract E decode(GamePacket packet);

}
