package org.apollo.game.msg.encoder;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;
import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.CharacterHintIconMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link CharacterHintIconMessage}.
 *
 * @author JamesMonger
 */
@EncodesMessage(CharacterHintIconMessage.class)
public class CharacterHintIconMessageEncoder implements MessageEncoder<CharacterHintIconMessage> {

    @Override
    public GamePacket encode(CharacterHintIconMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(254, GamePacketType.FIXED);
	builder.put(DataType.BYTE, message.getType().getNumericType());
	builder.put(DataType.BYTE, message.getTarget().getIndex());
	return builder.toGamePacket();
    }

}
