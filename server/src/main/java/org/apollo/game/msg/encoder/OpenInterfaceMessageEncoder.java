package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.OpenInterfaceMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link OpenInterfaceMessage}.
 *
 * @author Graham
 */
@EncodesMessage(OpenInterfaceMessage.class)
public final class OpenInterfaceMessageEncoder implements MessageEncoder<OpenInterfaceMessage> {

    @Override
    public GamePacket encode(OpenInterfaceMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(97);
	builder.put(DataType.SHORT, message.getId());
	return builder.toGamePacket();
    }

}
