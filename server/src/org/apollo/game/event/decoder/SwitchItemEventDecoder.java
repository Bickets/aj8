package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.SwitchItemEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link SwitchItemEvent}.
 * 
 * @author Graham
 */
@DecodesEvent(214)
public final class SwitchItemEventDecoder extends EventDecoder<SwitchItemEvent> {

    @Override
    public SwitchItemEvent decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	boolean inserting = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE) == 1;
	int oldSlot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	int newSlot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	return new SwitchItemEvent(interfaceId, inserting, oldSlot, newSlot);
    }

}
