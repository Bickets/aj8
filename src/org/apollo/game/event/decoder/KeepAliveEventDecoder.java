
package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.KeepAliveEvent;
import org.apollo.net.codec.game.GamePacket;

/**
 * A {@link EventDecoder} for the {@link KeepAliveEvent}.
 * @author Graham
 */
public final class KeepAliveEventDecoder extends EventDecoder<KeepAliveEvent>
{

	public KeepAliveEventDecoder( int opcode )
	{
		super( opcode );
	}


	@Override
	public KeepAliveEvent decode( GamePacket packet )
	{
		return new KeepAliveEvent();
	}

}
