package org.apollo.game.event;

import java.io.IOException;

import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link EventDecoder} decodes a {@link GamePacket} into an {@link Event}
 * object which can be processed by the server.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <E> The type of {@link Event}.
 */
public abstract class EventDecoder<E extends Event> {

    /**
     * The opcode of this event.
     */
    private final int opcode;

    /**
     * Construcs a new {@link EventDecoder}.
     * @param opcode	The opcode for this event.
     */
    public EventDecoder(int opcode) {
        this.opcode = opcode;
    }

    /**
     * Decodes the specified packet into an event.
     * @param packet The packet.
     * @return The event.
     * @throws IOException	If some I/O exception occurs.
     */
    public abstract E decode(GamePacket packet) throws IOException;

    /**
     * Returns the opcode of this event.
     * @return	The opcode.
     */
    public int getOpcode() {
        return opcode;
    }

}
