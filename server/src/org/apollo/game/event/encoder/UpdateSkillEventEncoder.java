
package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.UpdateSkillEvent;
import org.apollo.game.model.Skill;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link UpdateSkillEvent}.
 * @author Graham
 */
@EncodesEvent( UpdateSkillEvent.class )
public final class UpdateSkillEventEncoder extends EventEncoder<UpdateSkillEvent>
{

	@Override
	public GamePacket encode( UpdateSkillEvent event )
	{
		GamePacketBuilder builder = new GamePacketBuilder( 134 );
		Skill skill = event.getSkill();

		builder.put( DataType.BYTE, event.getId() );
		builder.put( DataType.INT, DataOrder.MIDDLE, ( int )skill.getExperience() );
		builder.put( DataType.BYTE, skill.getCurrentLevel() );

		return builder.toGamePacket();
	}

}
