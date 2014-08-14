package org.apollo.game.msg.encoder;

import org.apollo.game.model.Skill;
import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.UpdateSkillMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MessageEncoder} for the {@link UpdateSkillMessage}.
 *
 * @author Graham
 */
@EncodesMessage(UpdateSkillMessage.class)
public final class UpdateSkillMessageEncoder implements MessageEncoder<UpdateSkillMessage> {

    @Override
    public GamePacket encode(UpdateSkillMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(134);
	Skill skill = message.getSkill();

	builder.put(DataType.BYTE, message.getId());
	builder.put(DataType.INT, DataOrder.MIDDLE, (int) skill.getExperience());
	builder.put(DataType.BYTE, skill.getCurrentLevel());

	return builder.toGamePacket();
    }

}