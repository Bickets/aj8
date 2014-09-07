package org.apollo.game.msg.encoder;

import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.GameCharacterHintIconMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * A {@link MessageEncoder} for the {@link GameCharacterHintIconMessage}.
 *
 * @author JamesMonger
 */
@EncodesMessage(GameCharacterHintIconMessage.class)
public final class GameCharacterHintIconMessageEncoder implements MessageEncoder<GameCharacterHintIconMessage> {

	@Override
	public GamePacket encode(GameCharacterHintIconMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(254);
		builder.put(DataType.BYTE, message.getType().getIndex());
		builder.put(DataType.BYTE, message.getCharacter().getIndex());
		return builder.toGamePacket();
	}

}