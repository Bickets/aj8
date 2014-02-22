
package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.KeepAliveEvent;
import org.apollo.net.codec.game.GamePacket;

/**
 * A {@link EventDecoder} for the {@link KeepAliveEvent}.
 * @author Graham
 */
@DecodesEvent( 0 )
public final class KeepAliveEventDecoder extends EventDecoder<KeepAliveEvent>
{

	@Override
	public KeepAliveEvent decode( GamePacket packet )
	{
		return new KeepAliveEvent();
	}

}
