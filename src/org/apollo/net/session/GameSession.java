
package org.apollo.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.game.GameConstants;
import org.apollo.game.GameService;
import org.apollo.game.event.Event;
import org.apollo.game.event.EventTranslator;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.game.model.Player;

/**
 * A game session.
 * @author Graham
 */
public final class GameSession extends Session
{

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger( GameSession.class.getName() );

	/**
	 * The server context.
	 */
	private final ServerContext context;

	/**
	 * The event translator.
	 */
	private final EventTranslator eventTranslator;

	/**
	 * The queue of pending {@link Event}s.
	 */
	private final BlockingQueue<Event> eventQueue = new ArrayBlockingQueue<>( GameConstants.EVENTS_PER_PULSE );

	/**
	 * The player.
	 */
	private final Player player;


	/**
	 * Creates a login session for the specified channel context.
	 * @param ctx This sessions channels context.
	 * @param context The server context.
	 * @param eventTranslator The event translator.
	 * @param player The player.
	 */
	public GameSession( ChannelHandlerContext ctx, ServerContext context, EventTranslator eventTranslator, Player player )
	{
		super( ctx );
		this.context = context;
		this.eventTranslator = eventTranslator;
		this.player = player;
	}


	@Override
	public void messageReceived( Object message )
	{
		Event event = ( Event )message;
		if( eventQueue.size() >= GameConstants.EVENTS_PER_PULSE ) {
			logger.warning( "Too many events in queue for game session, dropping..." );
		} else {
			eventQueue.add( event );
		}
	}


	/**
	 * Encodes and dispatches the specified event.
	 * @param event The event.
	 */
	public void dispatchEvent( Event event )
	{
		Channel channel = ctx().channel();
		if( channel.isActive() ) {
			ChannelFuture future = channel.write( event );
			if( event.getClass() == LogoutEvent.class ) {
				future.addListener( ChannelFutureListener.CLOSE );
			}
		}
	}


	/**
	 * Handles pending events for this session.
	 */
	public void handlePendingEvents()
	{
		Event event;
		while( ( event = eventQueue.poll() ) != null ) {
			eventTranslator.handle( player, event );
		}
	}


	/**
	 * Handles a player saver response.
	 * @param success A flag indicating if the save was successful.
	 */
	public void handlePlayerSaverResponse( boolean success )
	{
		context.getService( GameService.class ).finalizePlayerUnregistration( player );
	}


	@Override
	public void destroy()
	{
		context.getService( GameService.class ).unregisterPlayer( player );
	}

}
