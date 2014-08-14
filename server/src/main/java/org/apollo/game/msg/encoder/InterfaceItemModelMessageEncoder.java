package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.InterfaceItemModelMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link InterfaceItemModelMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(InterfaceItemModelMessage.class)
public final class InterfaceItemModelMessageEncoder implements MessageEncoder<InterfaceItemModelMessage> {

    @Override
    public GamePacket encode(InterfaceItemModelMessage message) {
	GamePacketBuilder bldr = new GamePacketBuilder(246);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, message.getInterfaceId());
	bldr.put(DataType.SHORT, message.getZoom());
	bldr.put(DataType.SHORT, message.getItem().getId());
	return bldr.toGamePacket();
    }

}