package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.EnteredAmountEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link EnteredAmountEvent}.
 * 
 * @author Graham
 */
@DecodesEvent(208)
public final class EnteredAmountEventDecoder extends EventDecoder<EnteredAmountEvent> {

    @Override
    public EnteredAmountEvent decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int amount = (int) reader.getUnsigned(DataType.INT);
	return new EnteredAmountEvent(amount);
    }

}
