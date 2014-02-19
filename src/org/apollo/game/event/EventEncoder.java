package org.apollo.game.event;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link EventEncoder} encodes {@link Event} objects into
 * {@link GamePacket}s which can be sent over the network.
 * @author Graham
 * @param <E> The type of {@link Event}.
 */
public abstract class EventEncoder<E extends Event> {

    /**
     * The events class.
     */
    private final Class<E> clazz;

    /**
     * Constructs a new {@link EventEncoder}
     * @param clazz	The events class.
     */
    public EventEncoder(Class<E> clazz) {
        this.clazz = clazz;
    }

    /**
     * Encodes the specified event into a packet.
     * @param event The event.
     * @return The packet.
     */
    public abstract GamePacket encode(E event);

    /**
     * Returns the events class.
     * @return	The events class.
     */
    public Class<E> getClazz() {
        return clazz;
    }

}
