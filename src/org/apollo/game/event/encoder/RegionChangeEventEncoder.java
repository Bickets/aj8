
package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.RegionChangeEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link RegionChangeEvent}.
 * @author Graham
 */
@EncodesEvent( RegionChangeEvent.class )
public final class RegionChangeEventEncoder extends EventEncoder<RegionChangeEvent>
{

	@Override
	public GamePacket encode( RegionChangeEvent event )
	{
		GamePacketBuilder builder = new GamePacketBuilder( 73 );
		builder.put( DataType.SHORT, DataTransformation.ADD, event.getPosition().getCentralRegionX() );
		builder.put( DataType.SHORT, event.getPosition().getCentralRegionY() );
		return builder.toGamePacket();
	}

}
