package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.CloseInterfaceMessage;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link CloseInterfaceMessage}.
 *
 * @author Graham
 */
@EncodesMessage(CloseInterfaceMessage.class)
public final class CloseInterfaceMessageEncoder extends MessageEncoder<CloseInterfaceMessage> {

    @Override
    public GamePacket encode(CloseInterfaceMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(219);
	return builder.toGamePacket();
    }

}
