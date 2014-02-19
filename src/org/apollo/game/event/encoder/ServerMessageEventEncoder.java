
package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;

/**
 * An {@link EventEncoder} for the {@link ServerMessageEvent}.
 * @author Graham
 */
public final class ServerMessageEventEncoder extends EventEncoder<ServerMessageEvent>
{

	public ServerMessageEventEncoder( Class<ServerMessageEvent> clazz )
	{
		super( clazz );
	}


	@Override
	public GamePacket encode( ServerMessageEvent event )
	{
		GamePacketBuilder builder = new GamePacketBuilder( 253, PacketType.VARIABLE_BYTE );
		builder.putString( event.getMessage() );
		return builder.toGamePacket();
	}

}
