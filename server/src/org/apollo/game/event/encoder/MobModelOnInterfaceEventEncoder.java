package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.MobModelOnInterfaceEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link MobModelOnInterfaceEvent}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesEvent(MobModelOnInterfaceEvent.class)
public final class MobModelOnInterfaceEventEncoder extends EventEncoder<MobModelOnInterfaceEvent> {

    @Override
    public GamePacket encode(MobModelOnInterfaceEvent event) {
	GamePacketBuilder bldr = new GamePacketBuilder(75);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, event.getMobId());
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, event.getInterfaceId());
	return bldr.toGamePacket();
    }

}