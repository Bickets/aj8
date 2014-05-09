package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.PlayerModelOnInterfaceEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link PlayerModelOnInterfaceEvent}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesEvent(PlayerModelOnInterfaceEvent.class)
public final class PlayerModelOnInterfaceEventEncoder extends EventEncoder<PlayerModelOnInterfaceEvent> {

    @Override
    public GamePacket encode(PlayerModelOnInterfaceEvent event) {
	GamePacketBuilder bldr = new GamePacketBuilder(185);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, event.getInterfaceId());
	return bldr.toGamePacket();
    }

}
