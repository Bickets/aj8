package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.RemoveGroundItemMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link RemoveGroundItemMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(RemoveGroundItemMessage.class)
public final class RemoveGroundItemMessageEncoder implements MessageEncoder<RemoveGroundItemMessage> {

	@Override
	public GamePacket encode(RemoveGroundItemMessage msg) {
		GamePacketBuilder bldr = new GamePacketBuilder(156);
		bldr.put(DataType.BYTE, DataTransformation.SUBTRACT, msg.getPositionOffset());
		bldr.put(DataType.SHORT, msg.getGroundItem().getItem().getId());
		return bldr.toGamePacket();
	}

}