package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.OpenInterfaceSidebarMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link OpenInterfaceSidebarMessage}.
 *
 * @author Graham
 */
@EncodesMessage(OpenInterfaceSidebarMessage.class)
public final class OpenInterfaceSidebarMessageEncoder implements MessageEncoder<OpenInterfaceSidebarMessage> {

    @Override
    public GamePacket encode(OpenInterfaceSidebarMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(248);
	builder.put(DataType.SHORT, DataTransformation.ADD, message.getInterfaceId());
	builder.put(DataType.SHORT, message.getSidebarId());
	return builder.toGamePacket();
    }

}