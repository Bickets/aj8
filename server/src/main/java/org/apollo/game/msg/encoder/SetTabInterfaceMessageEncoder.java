package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.SetTabInterfaceMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link SetTabInterfaceMessage}.
 *
 * @author Graham
 */
@EncodesMessage(SetTabInterfaceMessage.class)
public final class SetTabInterfaceMessageEncoder implements MessageEncoder<SetTabInterfaceMessage> {

	@Override
	public GamePacket encode(SetTabInterfaceMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(71);
		builder.put(DataType.SHORT, message.getInterfaceId());
		builder.put(DataType.BYTE, DataTransformation.ADD, message.getTabId());
		return builder.toGamePacket();
	}

}