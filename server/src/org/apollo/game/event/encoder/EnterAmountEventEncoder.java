package org.apollo.game.event.encoder;

import io.netty.buffer.Unpooled;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.EnterAmountEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.meta.PacketType;

/**
 * An {@link EventEncoder} for the {@link EnterAmountEvent}.
 * 
 * @author Graham
 */
@EncodesEvent(EnterAmountEvent.class)
public final class EnterAmountEventEncoder extends EventEncoder<EnterAmountEvent> {

    @Override
    public GamePacket encode(EnterAmountEvent event) {
	return new GamePacket(27, PacketType.FIXED, Unpooled.EMPTY_BUFFER);
    }

}
