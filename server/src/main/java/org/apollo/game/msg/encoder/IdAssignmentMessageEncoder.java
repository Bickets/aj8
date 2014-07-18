package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.IdAssignmentMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link IdAssignmentMessage}.
 *
 * @author Graham
 */
@EncodesMessage(IdAssignmentMessage.class)
public final class IdAssignmentMessageEncoder implements MessageEncoder<IdAssignmentMessage> {

    @Override
    public GamePacket encode(IdAssignmentMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(249);
	builder.put(DataType.BYTE, DataTransformation.ADD, message.isMembers() ? 1 : 0);
	builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getId());
	return builder.toGamePacket();
    }

}
