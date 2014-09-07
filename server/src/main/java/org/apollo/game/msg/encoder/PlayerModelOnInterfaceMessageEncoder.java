package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.PlayerModelOnInterfaceMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link PlayerModelOnInterfaceMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(PlayerModelOnInterfaceMessage.class)
public final class PlayerModelOnInterfaceMessageEncoder implements MessageEncoder<PlayerModelOnInterfaceMessage> {

	@Override
	public GamePacket encode(PlayerModelOnInterfaceMessage message) {
		GamePacketBuilder bldr = new GamePacketBuilder(185);
		bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getInterfaceId());
		return bldr.toGamePacket();
	}

}