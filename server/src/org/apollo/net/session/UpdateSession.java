
package org.apollo.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

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
	 * The update service.
	 */
	private final UpdateService updateService;


	/**
	 * Creates an update session for the specified channel.
	 * @param service The update service.
	 * @param ctx The channels context.
	 */
	public UpdateSession( ChannelHandlerContext ctx, UpdateService updateService )
	{
		super( ctx );
		this.updateService = updateService;
	}


	@Override
	public void messageReceived( Object message )
	{
		UpdateDispatcher dispatcher = updateService.getDispatcher();
		Channel channel = ctx().channel();
		if( message.getClass() == OnDemandRequest.class ) {
			dispatcher.dispatch( channel, ( OnDemandRequest )message );
		} else if( message.getClass() == JagGrabRequest.class ) {
			dispatcher.dispatch( channel, ( JagGrabRequest )message );
		} else if( message.getClass() == HttpRequest.class ) {
			dispatcher.dispatch( channel, ( HttpRequest )message );
		} else {
			throw new IllegalStateException( "unknown message type" );
		}
	}

}
