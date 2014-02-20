
package org.apollo.game.event;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.annotate.EncodesEvent;
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
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketMetaDataGroup;

/**
 * A {@link EventTranslator} is a translator to decode and encode a packet.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class EventTranslator
{

	/**
	 * An array of event decoders.
	 */
	private final EventDecoder< ? >[] decoders = new EventDecoder< ? >[ 256 ];

	/**
	 * A map of event encoders.
	 */
	private final Map<Class< ? >, EventEncoder< ? >> encoders = new HashMap<>();

	/**
	 * The incoming packet meta data.
	 */
	private final PacketMetaDataGroup incomingPacketMetaData = PacketMetaDataGroup.create();


	/**
	 * Constructs a new {@link EventTranslator}.
	 */
	public EventTranslator()
	{
		init();
	}


	/**
	 * Initializes this event translator by registering encoders and decoders.
	 */
	private void init()
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
	}


	/**
	 * Returns an event decoder for it's opcode.
	 * @param opcode The opcode.
	 * @return An event decoder instance for the specified opcode.
	 * @throws NullPointerException If the decoder doesnt exist.
	 */
	public EventDecoder< ? > get( int opcode )
	{
		return decoders[ opcode ];
	}


	/**
	 * Returns an event encoder for its event class.
	 * @param clazz The class.
	 * @return An event encoder instance for the specified class.
	 */
	public EventEncoder< ? > get( Class< ? > clazz )
	{
		return encoders.get( clazz );
	}


	/**
	 * Registers an event decoder.
	 * @param decoder The decoder to register.
	 */
	public void register( EventDecoder< ? > decoder )
	{
		DecodesEvent annotation = decoder.getClass().getAnnotation( DecodesEvent.class );
		if( annotation == null ) {
			throw new IllegalArgumentException( "Event decoders must be annotated with @DecodesEvent" );
		}
		for( int value: annotation.value() ) {
			decoders[ value ] = decoder;
		}
	}


	/**
	 * Registers an event encoder.
	 * @param encoder The encoder to register.
	 */
	public void register( EventEncoder< ? > encoder )
	{
		EncodesEvent annotation = encoder.getClass().getAnnotation( EncodesEvent.class );
		if( annotation == null ) {
			throw new IllegalArgumentException( "Event encoders must be annotated with @EncodesEvent" );
		}
		encoders.put( annotation.value(), encoder );
	}


	/**
	 * Gets meta data for the specified incoming packet.
	 * @param opcode The opcode of the incoming packet.
	 * @return The {@link PacketMetaData} object.
	 */
	public final PacketMetaData getIncomingPacketMetaData( int opcode )
	{
		return incomingPacketMetaData.getMetaData( opcode );
	}

}
