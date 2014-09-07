package org.apollo.game.msg.encoder;

import io.netty.buffer.Unpooled;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.EnterAmountMessage;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link EnterAmountMessage}.
 *
 * @author Graham
 */
@EncodesMessage(EnterAmountMessage.class)
public final class EnterAmountMessageEncoder implements MessageEncoder<EnterAmountMessage> {

	@Override
	public GamePacket encode(EnterAmountMessage message) {
		return new GamePacket(27, GamePacketType.FIXED, Unpooled.EMPTY_BUFFER);
	}

}