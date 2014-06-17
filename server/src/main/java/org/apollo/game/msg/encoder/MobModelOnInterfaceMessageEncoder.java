package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.MobModelOnInterfaceMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link MobModelOnInterfaceMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(MobModelOnInterfaceMessage.class)
public final class MobModelOnInterfaceMessageEncoder extends MessageEncoder<MobModelOnInterfaceMessage> {

    @Override
    public GamePacket encode(MobModelOnInterfaceMessage message) {
	GamePacketBuilder bldr = new GamePacketBuilder(75);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getMobId());
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getInterfaceId());
	return bldr.toGamePacket();
    }

}