
package org.apollo.game.event;

import java.util.HashMap;
import java.util.Map;

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
import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.game.event.impl.EnterAmountEvent;
import org.apollo.game.event.impl.IdAssignmentEvent;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.game.event.impl.MobSynchronizationEvent;
import org.apollo.game.event.impl.OpenInterfaceEvent;
import org.apollo.game.event.impl.OpenInterfaceSidebarEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.event.impl.RegionChangeEvent;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.event.impl.SwitchTabInterfaceEvent;
import org.apollo.game.event.impl.UpdateItemsEvent;
import org.apollo.game.event.impl.UpdateSkillEvent;
import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
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
	 * The singleton instance.
	 */
	private static final EventTranslator INSTANCE = new EventTranslator();

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
	 * This constructor should not be called directly. Use the {@link #getInstance()} method
	 * instead.
	 */
	private EventTranslator()
	{
		init();
	}


	/**
	 * Initializes this event translator by registering encoders and decoders.
	 */
	private void init()
	{
		// register decoders
		register( new KeepAliveEventDecoder( 0 ) );
		register( new CharacterDesignEventDecoder( 101 ) );
		register( new WalkEventDecoder( 248 ) );
		register( new WalkEventDecoder( 164 ) );
		register( new WalkEventDecoder( 98 ) );
		register( new ChatEventDecoder( 4 ) );
		register( new ButtonEventDecoder( 185 ) );
		register( new CommandEventDecoder( 103 ) );
		register( new SwitchItemEventDecoder( 214 ) );
		register( new FirstObjectActionEventDecoder( 132 ) );
		register( new SecondObjectActionEventDecoder( 252 ) );
		register( new ThirdObjectActionEventDecoder( 70 ) );
		register( new EquipEventDecoder( 41 ) );
		register( new FirstItemActionEventDecoder( 145 ) );
		register( new SecondItemActionEventDecoder( 117 ) );
		register( new ThirdItemActionEventDecoder( 43 ) );
		register( new FourthItemActionEventDecoder( 129 ) );
		register( new FifthItemActionEventDecoder( 135 ) );
		register( new ClosedInterfaceEventDecoder( 130 ) );
		register( new EnteredAmountEventDecoder( 208 ) );

		// register encoders
		register( new IdAssignmentEventEncoder( IdAssignmentEvent.class ) );
		register( new RegionChangeEventEncoder( RegionChangeEvent.class ) );
		register( new ServerMessageEventEncoder( ServerMessageEvent.class ) );
		register( new MobSynchronizationEventEncoder( MobSynchronizationEvent.class ) );
		register( new PlayerSynchronizationEventEncoder( PlayerSynchronizationEvent.class ) );
		register( new OpenInterfaceEventEncoder( OpenInterfaceEvent.class ) );
		register( new CloseInterfaceEventEncoder( CloseInterfaceEvent.class ) );
		register( new SwitchTabInterfaceEventEncoder( SwitchTabInterfaceEvent.class ) );
		register( new LogoutEventEncoder( LogoutEvent.class ) );
		register( new UpdateItemsEventEncoder( UpdateItemsEvent.class ) );
		register( new UpdateSlottedItemsEventEncoder( UpdateSlottedItemsEvent.class ) );
		register( new UpdateSkillEventEncoder( UpdateSkillEvent.class ) );
		register( new OpenInterfaceSidebarEventEncoder( OpenInterfaceSidebarEvent.class ) );
		register( new EnterAmountEventEncoder( EnterAmountEvent.class ) );
		register( new SetInterfaceTextEventEncoder( SetInterfaceTextEvent.class ) );
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
	 * @throws NullPointerException If the class doesn't exist.
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
		decoders[ decoder.getOpcode() ] = decoder;
	}


	/**
	 * Registers an event encoder.
	 * @param encoder The encoder to register.
	 */
	public void register( EventEncoder< ? > encoder )
	{
		encoders.put( encoder.getClazz(), encoder );
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


	/**
	 * Returns the singleton instance of this class.
	 * @return The singleton instance.
	 */
	public static EventTranslator getInstance()
	{
		return INSTANCE;
	}

}
