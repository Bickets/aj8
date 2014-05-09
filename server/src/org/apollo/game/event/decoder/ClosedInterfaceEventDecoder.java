package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.ClosedInterfaceEvent;
import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link EventDecoder} for the {@link ClosedInterfaceEvent}.
 * 
 * @author Graham
 */
@DecodesEvent(130)
public final class ClosedInterfaceEventDecoder extends EventDecoder<ClosedInterfaceEvent> {

    @Override
    public ClosedInterfaceEvent decode(GamePacket packet) {
	return new ClosedInterfaceEvent();
    }

}
