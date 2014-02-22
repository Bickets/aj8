
package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link LogoutEvent}.
 * @author Graham
 */
@EncodesEvent( LogoutEvent.class )
public final class LogoutEventEncoder extends EventEncoder<LogoutEvent>
{

	@Override
	public GamePacket encode( LogoutEvent event )
	{
		GamePacketBuilder builder = new GamePacketBuilder( 109 );
		return builder.toGamePacket();
	}

}
