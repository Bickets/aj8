package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.WelcomeScreenMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link WelcomeScreenMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(WelcomeScreenMessage.class)
public final class WelcomeScreenMessageEncoder implements MessageEncoder<WelcomeScreenMessage> {

    @Override
    public GamePacket encode(WelcomeScreenMessage msg) {
	GamePacketBuilder bldr = new GamePacketBuilder(176);
	bldr.put(DataType.BYTE, DataTransformation.NEGATE, msg.getLastRecoveryChange());
	bldr.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, msg.getUnreadMessages());
	bldr.put(DataType.BYTE, msg.isMembersWarning() ? 1 : 0);
	bldr.put(DataType.INT, DataOrder.INVERSED_MIDDLE, msg.getLastAddress());
	bldr.put(DataType.SHORT, DataOrder.LITTLE, msg.getLastLogin());
	return bldr.toGamePacket();
    }

}