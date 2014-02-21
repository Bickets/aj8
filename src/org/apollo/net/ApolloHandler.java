
package org.apollo.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.Attribute;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.game.event.EventTranslator;
import org.apollo.net.codec.handshake.HandshakeConstants;
import org.apollo.net.codec.handshake.HandshakeMessage;
import org.apollo.net.codec.jaggrab.JagGrabRequest;
import org.apollo.net.session.LoginSession;
import org.apollo.net.session.Session;
import org.apollo.net.session.UpdateSession;

/**
 * An implementation of {@link ChannelHandlerAdapter} which handles
 * incoming upstream events from Netty.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @see {@link Sharable}
 */
@Sharable
public final class ApolloHandler extends ChannelHandlerAdapter
{

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger( ApolloHandler.class.getName() );

	/**
	 * The server context.
	 */
	private final ServerContext serverContext;

	/**
	 * The event translator.
	 */
	private final EventTranslator eventTranslator;


	/**
	 * Creates the Apollo event handler.
	 * @param context The server context.
	 * @param eventTranslator The event translator.
	 */
	public ApolloHandler( ServerContext context, EventTranslator eventTranslator )
	{
		this.serverContext = context;
		this.eventTranslator = eventTranslator;
	}


	@Override
	public void channelInactive( ChannelHandlerContext ctx )
	{
		Channel channel = ctx.channel();
		Session session = ctx.attr( NetworkConstants.NETWORK_SESSION ).getAndRemove();
		if( session != null ) {
			session.destroy();
		}
		logger.info( "Channel disconnected: " + channel );
		channel.close();
	}


	@Override
	public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception
	{
		Attribute<Session> attribute = ctx.attr( NetworkConstants.NETWORK_SESSION );
		Session session = attribute.get();

		if( msg.getClass() == HttpRequest.class || msg.getClass() == JagGrabRequest.class ) {
			session = new UpdateSession( ctx, serverContext );
		}

		if( session != null ) {
			session.messageReceived( msg );
			return;
		}

		HandshakeMessage handshakeMessage = ( HandshakeMessage )msg;
		switch( handshakeMessage.getServiceId() ) {
			case HandshakeConstants.SERVICE_GAME:
				attribute.set( new LoginSession( ctx, serverContext, eventTranslator ) );
				break;
			case HandshakeConstants.SERVICE_UPDATE:
				attribute.set( new UpdateSession( ctx, serverContext ) );
				break;
			default:
				throw new IllegalStateException( "Invalid service id" );
		}
	}


	@Override
	public void exceptionCaught( ChannelHandlerContext ctx, Throwable e )
	{
		Channel channel = ctx.channel();
		logger.log( Level.WARNING, "Exception occurred for channel: " + channel + ", closing...", e.getCause() );
		channel.close();
	}

}
