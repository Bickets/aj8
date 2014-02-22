
package org.apollo.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import org.apollo.ServerContext;
import org.apollo.net.codec.jaggrab.JagGrabRequest;
import org.apollo.net.codec.update.OnDemandRequest;
import org.apollo.update.UpdateDispatcher;
import org.apollo.update.UpdateService;

/**
 * An update session.
 * @author Graham
 */
public final class UpdateSession extends Session
{

	/**
	 * The server context.
	 */
	private final ServerContext context;


	/**
	 * Creates an update session for the specified channel.
	 * @param ctx The channels context.
	 * @param context The server context.
	 */
	public UpdateSession( ChannelHandlerContext ctx, ServerContext context )
	{
		super( ctx );
		this.context = context;
	}


	@Override
	public void messageReceived( Object message )
	{
		UpdateDispatcher dispatcher = context.getService( UpdateService.class ).getDispatcher();
		Channel channel = ctx().channel();
		if( message instanceof OnDemandRequest ) {
			dispatcher.dispatch( channel, ( OnDemandRequest )message );
		} else if( message instanceof JagGrabRequest ) {
			dispatcher.dispatch( channel, ( JagGrabRequest )message );
		} else if( message instanceof HttpRequest ) {
			dispatcher.dispatch( channel, ( HttpRequest )message );
		} else {
			throw new IllegalStateException( "unknown message type" );
		}
	}

}
