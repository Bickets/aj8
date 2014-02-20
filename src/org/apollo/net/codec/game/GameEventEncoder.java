
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

	/**
	 * The event translator.
	 */
	private final EventTranslator translator;


	/**
	 * Constructs a new {@link GameEventEncoder}.
	 * @param translator The event translator.
	 */
	public GameEventEncoder( EventTranslator translator )
	{
		this.translator = translator;
	}


	@Override
	protected void encode( ChannelHandlerContext ctx, Event msg, List<Object> out )
	{
		@SuppressWarnings( "unchecked" )
		EventEncoder<Event> encoder = ( EventEncoder<Event> )translator.get( msg.getClass() );
		if( encoder != null ) {
			out.add( encoder.encode( msg ) );
		}
	}

}
