
package org.apollo.net.codec.game;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.EventTranslator;

/**
 * A {@link MessageToMessageDecoder} that decodes {@link GamePacket}s into {@link Event}s.
 * @author Graham
 */
public final class GameEventDecoder extends MessageToMessageDecoder<GamePacket>
{

	@Override
	protected void decode( ChannelHandlerContext ctx, GamePacket msg, List<Object> out )
	{
		EventDecoder< ? > decoder = EventTranslator.getInstance().get( msg.getOpcode() );
		if( decoder != null ) {
			out.add( decoder.decode( msg ) );
		}
	}

}
