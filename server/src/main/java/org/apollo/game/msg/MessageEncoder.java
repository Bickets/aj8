package org.apollo.game.msg;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MessageEncoder} encodes {@link Message} objects into
 * {@link GamePacket} s which can be sent over the network.
 * 
 * <p>
 * This is a functional interface whose functional method is
 * {@link #encode(Message)}
 *
 * @author Graham
 * @param <E> The type of {@link Message}.
 */
@FunctionalInterface
public interface MessageEncoder<E extends Message> {

	/**
	 * Encodes the specified message into a packet.
	 *
	 * @param msg The message.
	 * @return The packet.
	 */
	GamePacket encode(E msg);

}