package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.SwitchTabInterfaceMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link SwitchTabInterfaceMessage}.
 *
 * @author Graham
 */
@EncodesMessage(SwitchTabInterfaceMessage.class)
public final class SwitchTabInterfaceMessageEncoder implements MessageEncoder<SwitchTabInterfaceMessage> {

	@Override
	public GamePacket encode(SwitchTabInterfaceMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(71);
		builder.put(DataType.SHORT, message.getInterfaceId());
		builder.put(DataType.BYTE, DataTransformation.ADD, message.getTabId());
		return builder.toGamePacket();
	}

}