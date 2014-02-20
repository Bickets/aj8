
package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.FirstObjectActionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link FirstObjectActionEvent}.
 * @author Graham
 */
@DecodesEvent( 132 )
public final class FirstObjectActionEventDecoder extends EventDecoder<FirstObjectActionEvent>
{

	public FirstObjectActionEvent decode( GamePacket packet )
	{
		GamePacketReader reader = new GamePacketReader( packet );
		int x = ( int )reader.getUnsigned( DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD );
		int id = ( int )reader.getUnsigned( DataType.SHORT );
		int y = ( int )reader.getUnsigned( DataType.SHORT, DataTransformation.ADD );
		return new FirstObjectActionEvent( id, new Position( x, y ) );
	}

}
