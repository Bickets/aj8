
package org.apollo.net.codec.game;

import org.apollo.game.event.Event;
import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.EventTranslator;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

/**
 * A {@link OneToOneDecoder} that decodes {@link GamePacket}s into {@link Event}s.
 * @author Graham
 */
public final class GameEventDecoder extends OneToOneDecoder
{

	@Override
	protected Object decode( ChannelHandlerContext ctx, Channel c, Object msg ) throws Exception
	{
		if( msg instanceof GamePacket ) {
			GamePacket packet = ( GamePacket )msg;
			EventDecoder< ? > decoder = EventTranslator.getInstance().get( packet.getOpcode() );
			if( decoder != null ) {
				return decoder.decode( packet );
			}
			return null;
		}
		return msg;
	}

}
