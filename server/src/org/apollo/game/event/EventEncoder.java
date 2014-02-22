
package org.apollo.game.event;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link EventEncoder} encodes {@link Event} objects into {@link GamePacket}s which can be sent
 * over the network.
 * @author Graham
 * @param <E> The type of {@link Event}.
 */
public abstract class EventEncoder<E extends Event>
{

	/**
	 * Encodes the specified event into a packet.
	 * @param event The event.
	 * @return The packet.
	 */
	public abstract GamePacket encode( E event );

}
