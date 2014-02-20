
package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.OpenInterfaceEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link OpenInterfaceEvent}.
 * @author Graham
 */
@EncodesEvent( OpenInterfaceEvent.class )
public final class OpenInterfaceEventEncoder extends EventEncoder<OpenInterfaceEvent>
{

	@Override
	public GamePacket encode( OpenInterfaceEvent event )
	{
		GamePacketBuilder builder = new GamePacketBuilder( 97 );
		builder.put( DataType.SHORT, event.getId() );
		return builder.toGamePacket();
	}

}
