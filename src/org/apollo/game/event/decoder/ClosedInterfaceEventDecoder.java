package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.ClosedInterfaceEvent;
import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link EventDecoder} for the {@link ClosedInterfaceEvent}.
 * @author Graham
 */
public final class ClosedInterfaceEventDecoder extends EventDecoder<ClosedInterfaceEvent> {

    public ClosedInterfaceEventDecoder(int opcode) {
        super(opcode);
    }

    @Override
    public ClosedInterfaceEvent decode(GamePacket packet) {
        return new ClosedInterfaceEvent();
    }

}
