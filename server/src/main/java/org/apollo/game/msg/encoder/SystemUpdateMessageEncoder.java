package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.SystemUpdateMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link SystemUpdateMessage}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(SystemUpdateMessage.class)
public final class SystemUpdateMessageEncoder implements MessageEncoder<SystemUpdateMessage> {

	@Override
	public GamePacket encode(SystemUpdateMessage msg) {
		GamePacketBuilder builder = new GamePacketBuilder(114);
		builder.put(DataType.SHORT, DataOrder.LITTLE, msg.getSeconds());
		return builder.toGamePacket();
	}

}