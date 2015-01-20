package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.GameObjectMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the game object message.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(GameObjectMessage.class)
public class GameObjectMessageEncoder implements MessageEncoder<GameObjectMessage> {

	@Override
	public GamePacket encode(GameObjectMessage msg) {
		GamePacketBuilder bldr = new GamePacketBuilder(151);
		bldr.put(DataType.BYTE, DataTransformation.ADD, msg.getPositionOffset());
		bldr.put(DataType.SHORT, DataOrder.LITTLE, msg.getObject().getId());
		bldr.put(DataType.BYTE, DataTransformation.SUBTRACT, msg.getObject().hashCode());
		return bldr.toGamePacket();
	}

}