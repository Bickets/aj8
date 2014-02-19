package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link CloseInterfaceEvent}.
 * @author Graham
 */
public final class CloseInterfaceEventEncoder extends EventEncoder<CloseInterfaceEvent> {

    public CloseInterfaceEventEncoder(Class<CloseInterfaceEvent> clazz) {
        super(clazz);
    }

    @Override
    public GamePacket encode(CloseInterfaceEvent event) {
        GamePacketBuilder builder = new GamePacketBuilder(219);
        return builder.toGamePacket();
    }

}
