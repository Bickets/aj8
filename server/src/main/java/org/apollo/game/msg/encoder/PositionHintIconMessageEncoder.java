package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.PositionHintIconMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * A {@link MessageEncoder} for the {@link PositionHintIconMessage}.
 *
 * @author JamesMonger
 */
@EncodesMessage(PositionHintIconMessage.class)
public final class PositionHintIconMessageEncoder implements MessageEncoder<PositionHintIconMessage> {

	@Override
	public GamePacket encode(PositionHintIconMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(254);
		builder.put(DataType.BYTE, message.getType().getIndex());
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getPosition().getX());
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getPosition().getY());
		builder.put(DataType.BYTE, message.getDrawHeight());
		return builder.toGamePacket();
	}

}