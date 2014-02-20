
package org.apollo.game.event;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.handler.ButtonEventHandler;
import org.apollo.game.event.handler.CharacterDesignEventHandler;
import org.apollo.game.event.handler.ChatEventHandler;
import org.apollo.game.event.handler.ClosedInterfaceEventHandler;
import org.apollo.game.event.handler.CommandEventHandler;
import org.apollo.game.event.handler.EnteredAmountEventHandler;
import org.apollo.game.event.handler.EquipEventHandler;
import org.apollo.game.event.handler.ItemActionEventHandler;
import org.apollo.game.event.handler.ObjectEventHandler;
import org.apollo.game.event.handler.SwitchItemEventHandler;
import org.apollo.game.event.handler.WalkEventHandler;
import org.apollo.game.model.Player;

/**
 * Dispatches {@link Event}'s.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class EventDispatcher
{

	/**
	 * The singleton instance.
	 */
	private static final EventDispatcher INSTANCE = new EventDispatcher();

	/**
	 * An instance of logger, used to print messages to the console.
	 */
	private static final Logger logger = Logger.getLogger( EventDispatcher.class.getCanonicalName() );

	/**
	 * A map of event handlers.
	 */
	private final Map<Class< ? >, EventHandler< ? >> handlers = new HashMap<>();


	/**
	 * Constructs a new {@link EventDispatcher}.
	 */
	private EventDispatcher()
	{
		init();
	}


	/**
	 * Registers all of the event handlers.
	 */
	private void init()
	{
		register( new CharacterDesignEventHandler() );
		register( new WalkEventHandler() );
		register( new ChatEventHandler() );
		register( new ButtonEventHandler() );
		register( new CommandEventHandler() );
		register( new SwitchItemEventHandler() );
		register( new ObjectEventHandler() );
		register( new EquipEventHandler() );
		register( new ItemActionEventHandler() );
		register( new ClosedInterfaceEventHandler() );
		register( new EnteredAmountEventHandler() );
	}


	/**
	 * Dispatches an event.
	 * @param player The player to dispatch for.
	 * @param event The event to dispatch.
	 */
	@SuppressWarnings( "unchecked" )
	public void dispatch( Player player, Event event )
	{
		EventHandler<Event> handler = ( EventHandler<Event> )handlers.get( event.getClass() );

		if( handler == null ) {
			logger.warning( "Null handler for event: " + event.getClass().getName() + "." );
			return;
		}

		try {
			handler.handle( player, event );
		} catch( Throwable t ) {
			logger.log( Level.SEVERE, "Error processing event:", t );
		}
	}


	/**
	 * Registers a handler.
	 * @param clazz The event class.
	 * @param handler The handler.
	 */
	private <T extends Event> void register( EventHandler<T> handler )
	{
		HandlesEvent annotation = handler.getClass().getAnnotation( HandlesEvent.class );
		if( annotation == null ) {
			throw new IllegalArgumentException( "Event handlers must be annotated with @HandlesEvent" );
		}
		handlers.put( annotation.value(), handler );
	}


	/**
	 * Returns the singleton instance of this class.
	 * @return The singleton instance
	 */
	public static EventDispatcher getInstance()
	{
		return INSTANCE;
	}

}
