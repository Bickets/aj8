
package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.EnteredAmountEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link EnteredAmountEvent}.
 * @author Graham
 */
public final class EnteredAmountEventDecoder extends EventDecoder<EnteredAmountEvent>
{

	public EnteredAmountEventDecoder( int opcode )
	{
		super( opcode );
	}


	@Override
	public EnteredAmountEvent decode( GamePacket packet )
	{
		GamePacketReader reader = new GamePacketReader( packet );
		int amount = ( int )reader.getUnsigned( DataType.INT );
		return new EnteredAmountEvent( amount );
	}

}
