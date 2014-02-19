
package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.SecondObjectActionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link SecondObjectActionEvent}.
 * @author Graham
 */
public final class SecondObjectActionEventDecoder extends EventDecoder<SecondObjectActionEvent>
{

	public SecondObjectActionEventDecoder( int opcode )
	{
		super( opcode );
	}


	@Override
	public SecondObjectActionEvent decode( GamePacket packet )
	{
		GamePacketReader reader = new GamePacketReader( packet );
		int id = ( int )reader.getUnsigned( DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD );
		int y = ( int )reader.getUnsigned( DataType.SHORT, DataOrder.LITTLE );
		int x = ( int )reader.getUnsigned( DataType.SHORT, DataTransformation.ADD );
		return new SecondObjectActionEvent( id, new Position( x, y ) );
	}

}
