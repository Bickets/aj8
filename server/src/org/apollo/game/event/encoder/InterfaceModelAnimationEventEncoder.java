package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.InterfaceModelAnimationEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link InterfaceModelAnimationEvent}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesEvent(InterfaceModelAnimationEvent.class)
public class InterfaceModelAnimationEventEncoder extends EventEncoder<InterfaceModelAnimationEvent> {

    @Override
    public GamePacket encode(InterfaceModelAnimationEvent event) {
	GamePacketBuilder bldr = new GamePacketBuilder(200);
	bldr.put(DataType.SHORT, event.getInterfaceId());
	bldr.put(DataType.SHORT, event.getAnimation().getId());
	return bldr.toGamePacket();
    }

}
