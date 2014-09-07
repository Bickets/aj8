package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.ServerMessageMessage;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link ServerMessageMessage}.
 *
 * @author Graham
 */
@EncodesMessage(ServerMessageMessage.class)
public final class ServerMessageMessageEncoder implements MessageEncoder<ServerMessageMessage> {

	@Override
	public GamePacket encode(ServerMessageMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(253, GamePacketType.VARIABLE_BYTE);
		builder.putString(message.getMessage());
		return builder.toGamePacket();
	}

}