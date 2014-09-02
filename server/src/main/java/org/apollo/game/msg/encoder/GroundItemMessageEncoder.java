package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.GroundItemMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the message for a ground item.
 * 
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
@EncodesMessage(GroundItemMessage.class)
public final class GroundItemMessageEncoder implements MessageEncoder<GroundItemMessage> {

    @Override
    public GamePacket encode(GroundItemMessage msg) {
	GamePacketBuilder bldr = new GamePacketBuilder(44);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, msg.getGroundItem().getItem().getId());
	bldr.put(DataType.SHORT, msg.getGroundItem().getItem().getAmount());
	bldr.put(DataType.BYTE, msg.getPositionOffset());
	return bldr.toGamePacket();
    }

}