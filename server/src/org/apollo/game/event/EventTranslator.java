
package org.apollo.game.event;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.annotate.HandlesEvent;
import org.apollo.game.event.decoder.ButtonEventDecoder;
import org.apollo.game.event.decoder.CharacterDesignEventDecoder;
import org.apollo.game.event.decoder.ChatEventDecoder;
import org.apollo.game.event.decoder.ClosedInterfaceEventDecoder;
import org.apollo.game.event.decoder.CommandEventDecoder;
import org.apollo.game.event.decoder.EnteredAmountEventDecoder;
import org.apollo.game.event.decoder.EquipEventDecoder;
import org.apollo.game.event.decoder.FifthItemActionEventDecoder;
import org.apollo.game.event.decoder.FirstItemActionEventDecoder;
import org.apollo.game.event.decoder.FirstObjectActionEventDecoder;
import org.apollo.game.event.decoder.FourthItemActionEventDecoder;
import org.apollo.game.event.decoder.KeepAliveEventDecoder;
import org.apollo.game.event.decoder.SecondItemActionEventDecoder;
import org.apollo.game.event.decoder.SecondObjectActionEventDecoder;
import org.apollo.game.event.decoder.SwitchItemEventDecoder;
import org.apollo.game.event.decoder.ThirdItemActionEventDecoder;
import org.apollo.game.event.decoder.ThirdObjectActionEventDecoder;
import org.apollo.game.event.decoder.WalkEventDecoder;
import org.apollo.game.event.encoder.CloseInterfaceEventEncoder;
import org.apollo.game.event.encoder.EnterAmountEventEncoder;
import org.apollo.game.event.encoder.IdAssignmentEventEncoder;
import org.apollo.game.event.encoder.LogoutEventEncoder;
import org.apollo.game.event.encoder.MobSynchronizationEventEncoder;
import org.apollo.game.event.encoder.OpenDialogueInterfaceEventEncoder;
import org.apollo.game.event.encoder.OpenInterfaceEventEncoder;
import org.apollo.game.event.encoder.OpenInterfaceSidebarEventEncoder;
import org.apollo.game.event.encoder.PlayerSynchronizationEventEncoder;
import org.apollo.game.event.encoder.RegionChangeEventEncoder;
import org.apollo.game.event.encoder.ServerMessageEventEncoder;
import org.apollo.game.event.encoder.SetInterfaceTextEventEncoder;
import org.apollo.game.event.encoder.SwitchTabInterfaceEventEncoder;
import org.apollo.game.event.encoder.UpdateItemsEventEncoder;
import org.apollo.game.event.encoder.UpdateSkillEventEncoder;
import org.apollo.game.event.encoder.UpdateSlottedItemsEventEncoder;
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
import org.apollo.game.model.World;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketMetaDataGroup;

/**
 * The responsibility of this class is to translate registered {@link Event}'s to their respective
 * handler.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class EventTranslator
{

	/**
	 * A {@link Map} of {@link Integer}s to {@link EventDecoder}s
	 */
	private final Map<Integer, EventDecoder< ? >> decoders = new HashMap<>();

	/**
	 * A {@link Map} of {@link Class}' to {@link EventEncoder}s
	 */
	private final Map<Class< ? >, EventEncoder< ? >> encoders = new HashMap<>();

	/**
	 * A {@link Map} of {@link Class}' to {@link EventHandler}s
	 */
	private final Map<Class< ? >, EventHandler< ? >> handlers = new HashMap<>();

	/**
	 * The incoming packet meta data.
	 */
	private final PacketMetaDataGroup incomingPacketMetaData = PacketMetaDataGroup.create();

	/**
	 * The world, used for world event handlers
	 */
	private final World world = World.getInstance();

	/**
	 * The logger.
	 */
	private final Logger logger = Logger.getLogger( getClass().getName() );


	/**
	 * Constructs a new {@link EventTranslator}.
	 */
	public EventTranslator()
	{
		registerAll();
	}


	/**
	 * Registers all event decoders, encoders and handlers.
	 */
	private void registerAll()
	{
		// register decoders
		register( new KeepAliveEventDecoder() );
		register( new CharacterDesignEventDecoder() );
		register( new WalkEventDecoder() );
		register( new ChatEventDecoder() );
		register( new ButtonEventDecoder() );
		register( new CommandEventDecoder() );
		register( new SwitchItemEventDecoder() );
		register( new FirstObjectActionEventDecoder() );
		register( new SecondObjectActionEventDecoder() );
		register( new ThirdObjectActionEventDecoder() );
		register( new EquipEventDecoder() );
		register( new FirstItemActionEventDecoder() );
		register( new SecondItemActionEventDecoder() );
		register( new ThirdItemActionEventDecoder() );
		register( new FourthItemActionEventDecoder() );
		register( new FifthItemActionEventDecoder() );
		register( new ClosedInterfaceEventDecoder() );
		register( new EnteredAmountEventDecoder() );

		// register encoders
		register( new IdAssignmentEventEncoder() );
		register( new RegionChangeEventEncoder() );
		register( new ServerMessageEventEncoder() );
		register( new MobSynchronizationEventEncoder() );
		register( new PlayerSynchronizationEventEncoder() );
		register( new OpenInterfaceEventEncoder() );
		register( new CloseInterfaceEventEncoder() );
		register( new SwitchTabInterfaceEventEncoder() );
		register( new LogoutEventEncoder() );
		register( new UpdateItemsEventEncoder() );
		register( new UpdateSlottedItemsEventEncoder() );
		register( new UpdateSkillEventEncoder() );
		register( new OpenInterfaceSidebarEventEncoder() );
		register( new EnterAmountEventEncoder() );
		register( new SetInterfaceTextEventEncoder() );
		register( new OpenDialogueInterfaceEventEncoder() );

		// register handlers
		register( new CharacterDesignEventHandler() );
		register( new WalkEventHandler() );
		register( new ChatEventHandler() );
		register( new CommandEventHandler() );
		register( new SwitchItemEventHandler() );
		register( new ObjectEventHandler() );
		register( new EquipEventHandler() );
		register( new ItemActionEventHandler() );
		register( new ClosedInterfaceEventHandler() );
		register( new EnteredAmountEventHandler() );

		// world handlers
		register( new ButtonEventHandler( world ) );
	}


	/**
	 * Registers an {@link EventDecoder} to its respective map.
	 */
	private void register( EventDecoder< ? > decoder )
	{
		DecodesEvent annotation = decoder.getClass().getAnnotation( DecodesEvent.class );
		if( annotation == null ) {
			logger.warning( String.format( "%s is not annotated with @DecodesEvent!", decoder.toString() ) );
			return;
		}
		for( int value: annotation.value() ) {
			decoders.put( value, decoder );
		}
	}


	/**
	 * Registers an {@link EventEncoder} to its respective map.
	 */
	private void register( EventEncoder< ? > encoder )
	{
		EncodesEvent annotation = encoder.getClass().getAnnotation( EncodesEvent.class );
		if( annotation == null ) {
			logger.warning( String.format( "%s is not annotated with @EncodesEvent!", encoder.toString() ) );
			return;
		}
		encoders.put( annotation.value(), encoder );
	}


	/**
	 * Registers an {@link EventHandler} to its respective map.
	 */
	private void register( EventHandler< ? > handler )
	{
		HandlesEvent annotation = handler.getClass().getAnnotation( HandlesEvent.class );
		if( annotation == null ) {
			logger.warning( String.format( "%s is not annotated with @HandlesEvent!", handler.toString() ) );
			return;
		}
		handlers.put( annotation.value(), handler );
	}


	/**
	 * Returns a decoded {@link Event} or {@code null} if the {@link EventDecoder} does not exist
	 * for the specified packets opcode.
	 * @param packet The packet.
	 */
	public Event decode( GamePacket packet )
	{
		@SuppressWarnings( "unchecked" )
		EventDecoder<Event> decoder = ( EventDecoder<Event> )decoders.get( packet.getOpcode() );
		if( decoder == null ) {
			return null;
		}
		return decoder.decode( packet );
	}


	/**
	 * Returns an encoded {@link GamePacket} or {@code null} if the {@link EventEncoder} does not
	 * exist for the specified event's class.
	 * @param event The event.
	 */
	public GamePacket encode( Event event )
	{
		@SuppressWarnings( "unchecked" )
		EventEncoder<Event> encoder = ( EventEncoder<Event> )encoders.get( event.getClass() );
		if( encoder == null ) {
			return null;
		}
		return encoder.encode( event );
	}


	/**
	 * Handles an {@link Event} for a specified {@link Player} if it exists by the event's class.
	 * @param player The player
	 * @param event The event
	 */
	public void handle( Player player, Event event )
	{
		@SuppressWarnings( "unchecked" )
		EventHandler<Event> handler = ( EventHandler<Event> )handlers.get( event.getClass() );
		if( handler == null ) {
			return;
		}
		handler.handle( player, event );
	}


	/**
	 * Returns data about packet data for a specified opcode.
	 * @param opcode The opcode.
	 */
	public PacketMetaData getIncomingPacketMetaData( int opcode )
	{
		return incomingPacketMetaData.getMetaData( opcode );
	}

}
