package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.LocationHintIconMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link LocationHintIconMessage}.
 *
 * @author JamesMonger
 */
@EncodesMessage(LocationHintIconMessage.class)
public class LocationHintIconMessageEncoder implements MessageEncoder<LocationHintIconMessage> {

    @Override
    public GamePacket encode(LocationHintIconMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(254, GamePacketType.FIXED);
	builder.put(DataType.BYTE, message.getType().getNumericType());
	builder.put(DataType.SHORT, message.getTarget().getX());
	builder.put(DataType.SHORT, message.getTarget().getY());
	builder.put(DataType.BYTE, message.getDrawHeight());
	return builder.toGamePacket();
    }

}
