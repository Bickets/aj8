package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.OpenDialogueInterfaceMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link OpenDialogueInterfaceMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesMessage(OpenDialogueInterfaceMessage.class)
public final class OpenDialogueInterfaceMessageEncoder extends MessageEncoder<OpenDialogueInterfaceMessage> {

    @Override
    public GamePacket encode(OpenDialogueInterfaceMessage message) {
	GamePacketBuilder bldr = new GamePacketBuilder(164);
	bldr.put(DataType.SHORT, DataOrder.LITTLE, message.getInterfaceId());
	return bldr.toGamePacket();
    }

}
