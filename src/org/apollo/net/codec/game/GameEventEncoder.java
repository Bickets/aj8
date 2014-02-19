
package org.apollo.net.codec.game;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.EventTranslator;

/**
 * A {@link MessageToMessageEncoder} which encodes {@link Event}s into {@link GamePacket}s.
 * @author Graham
 */
public final class GameEventEncoder extends MessageToMessageEncoder<Event>
{

	@Override
	protected void encode( ChannelHandlerContext ctx, Event msg, List<Object> out ) throws Exception
	{
		@SuppressWarnings( "unchecked" )
		EventEncoder<Event> encoder = ( EventEncoder<Event> )EventTranslator.getInstance().get( msg.getClass() );
		if( encoder != null ) {
			out.add( encoder.encode( msg ) );
		}
	}

}
