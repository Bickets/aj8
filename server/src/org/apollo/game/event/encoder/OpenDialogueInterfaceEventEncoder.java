
package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.OpenDialogueInterfaceEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * Encodes the {@link OpenDialogueInterfaceEvent}.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@EncodesEvent( OpenDialogueInterfaceEvent.class )
public final class OpenDialogueInterfaceEventEncoder extends EventEncoder<OpenDialogueInterfaceEvent>
{

	@Override
	public GamePacket encode( OpenDialogueInterfaceEvent event )
	{
		GamePacketBuilder bldr = new GamePacketBuilder( 164 );
		bldr.put( DataType.SHORT, DataOrder.LITTLE, event.getInterfaceId() );
		return bldr.toGamePacket();
	}

}
