package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.InterfaceItemModelEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link InterfaceItemModelEvent}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesEvent(InterfaceItemModelEvent.class)
public final class InterfaceItemModelEventEncoder extends EventEncoder<InterfaceItemModelEvent> {

    @Override
    public GamePacket encode(InterfaceItemModelEvent event) {
	GamePacketBuilder bldr = new GamePacketBuilder(246);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, event.getInterfaceId());
	bldr.put(DataType.SHORT, event.getZoom());
	bldr.put(DataType.SHORT, event.getItem().getId());
	return bldr.toGamePacket();
    }

}
