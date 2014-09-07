package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.InterfaceModelAnimationMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link InterfaceModelAnimationMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(InterfaceModelAnimationMessage.class)
public class InterfaceModelAnimationMessageEncoder implements MessageEncoder<InterfaceModelAnimationMessage> {

	@Override
	public GamePacket encode(InterfaceModelAnimationMessage message) {
		GamePacketBuilder bldr = new GamePacketBuilder(200);
		bldr.put(DataType.SHORT, message.getInterfaceId());
		bldr.put(DataType.SHORT, message.getAnimation().getId());
		return bldr.toGamePacket();
	}

}