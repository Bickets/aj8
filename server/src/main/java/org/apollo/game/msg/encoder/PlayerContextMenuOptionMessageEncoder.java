package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.PlayerContextMenuOptionMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * Encodes the {@link PlayerContextMenuOptionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(PlayerContextMenuOptionMessage.class)
public final class PlayerContextMenuOptionMessageEncoder implements MessageEncoder<PlayerContextMenuOptionMessage> {

	@Override
	public GamePacket encode(PlayerContextMenuOptionMessage msg) {
		GamePacketBuilder bldr = new GamePacketBuilder(104, GamePacketType.VARIABLE_BYTE);
		bldr.put(DataType.BYTE, DataTransformation.NEGATE, msg.getPosition());
		bldr.put(DataType.BYTE, DataTransformation.ADD, msg.onTop() ? 0 : 1);
		bldr.putString(msg.getName());
		return bldr.toGamePacket();
	}

}