package org.apollo.game.msg;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MessageEncoder} encodes {@link Message} objects into
 * {@link GamePacket} s which can be sent over the network.
 * 
 * @author Graham
 * @param <E> The type of {@link Message}.
 */
public abstract class MessageEncoder<E extends Message> {

    /**
     * Encodes the specified message into a packet.
     * 
     * @param msg The message.
     * @return The packet.
     */
    public abstract GamePacket encode(E msg);

}
