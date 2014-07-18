package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.RegionChangeMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link RegionChangeMessage}.
 *
 * @author Graham
 */
@EncodesMessage(RegionChangeMessage.class)
public final class RegionChangeMessageEncoder implements MessageEncoder<RegionChangeMessage> {

    @Override
    public GamePacket encode(RegionChangeMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(73);
	builder.put(DataType.SHORT, DataTransformation.ADD, message.getPosition().getCentralRegionX());
	builder.put(DataType.SHORT, message.getPosition().getCentralRegionY());
	return builder.toGamePacket();
    }

}
