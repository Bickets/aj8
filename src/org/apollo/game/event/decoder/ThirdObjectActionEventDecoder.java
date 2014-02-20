
package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.ThirdObjectActionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link ThirdObjectActionEvent}.
 * @author Graham
 */
@DecodesEvent( 70 )
public final class ThirdObjectActionEventDecoder extends EventDecoder<ThirdObjectActionEvent>
{

	@Override
	public ThirdObjectActionEvent decode( GamePacket packet )
	{
		GamePacketReader reader = new GamePacketReader( packet );
		int x = ( int )reader.getUnsigned( DataType.SHORT, DataOrder.LITTLE );
		int y = ( int )reader.getUnsigned( DataType.SHORT );
		int id = ( int )reader.getUnsigned( DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD );
		return new ThirdObjectActionEvent( id, new Position( x, y ) );
	}

}
