package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link SetInterfaceTextMessage}.
 *
 * @author The Wanderer
 */
@EncodesMessage(SetInterfaceTextMessage.class)
public final class SetInterfaceTextMessageEncoder implements MessageEncoder<SetInterfaceTextMessage> {

    @Override
    public GamePacket encode(SetInterfaceTextMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(126, GamePacketType.VARIABLE_SHORT);
	builder.putString(message.getText());
	builder.put(DataType.SHORT, DataTransformation.ADD, message.getInterfaceId());

	return builder.toGamePacket();
    }

}
