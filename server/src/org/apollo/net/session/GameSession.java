
package org.apollo.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

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
	 * The game service.
	 */
	private final GameService gameService;


	/**
	 * Creates a login session for the specified channel context.
	 * @param ctx This sessions channels context.
	 * @param eventTranslator The event translator.
	 * @param player The player.
	 * @param gameService The game service.
	 */
	public GameSession( ChannelHandlerContext ctx, EventTranslator eventTranslator, Player player, GameService gameService )
	{
		super( ctx );
		this.eventTranslator = eventTranslator;
		this.player = player;
		this.gameService = gameService;
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
			ChannelFuture future = channel.writeAndFlush( event );
			if( event.getClass() == LogoutEvent.class ) { // TODO: Better way?
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
	 */
	public void handlePlayerSaverResponse()
	{
		gameService.finalizePlayerUnregistration( player );
	}


	@Override
	public void destroy()
	{
		gameService.unregisterPlayer( player );
	}

}
