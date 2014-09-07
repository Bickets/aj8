package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.PositionMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the position message.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(PositionMessage.class)
public final class PositionMessageEncoder implements MessageEncoder<PositionMessage> {

	@Override
	public GamePacket encode(PositionMessage msg) {
		GamePacketBuilder bldr = new GamePacketBuilder(85);
		bldr.put(DataType.BYTE, DataTransformation.NEGATE, msg.getPosition().getLocalY());
		bldr.put(DataType.BYTE, DataTransformation.NEGATE, msg.getPosition().getLocalX());
		return bldr.toGamePacket();
	}

}