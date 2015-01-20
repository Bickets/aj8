package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.LogoutMessage;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link LogoutMessage}.
 *
 * @author Graham
 */
@EncodesMessage(LogoutMessage.class)
public final class LogoutMessageEncoder implements MessageEncoder<LogoutMessage> {

	@Override
	public GamePacket encode(LogoutMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(109);
		return builder.toGamePacket();
	}

}