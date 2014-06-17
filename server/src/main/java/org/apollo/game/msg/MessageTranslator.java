package org.apollo.game.msg;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.decoder.ButtonMessageDecoder;
import org.apollo.game.msg.decoder.CharacterDesignMessageDecoder;
import org.apollo.game.msg.decoder.ChatMessageDecoder;
import org.apollo.game.msg.decoder.ClosedInterfaceMessageDecoder;
import org.apollo.game.msg.decoder.CommandMessageDecoder;
import org.apollo.game.msg.decoder.DialogueContinueMessageDecoder;
import org.apollo.game.msg.decoder.EnteredAmountMessageDecoder;
import org.apollo.game.msg.decoder.EquipMessageDecoder;
import org.apollo.game.msg.decoder.FifthItemActionMessageDecoder;
import org.apollo.game.msg.decoder.FirstItemActionMessageDecoder;
import org.apollo.game.msg.decoder.FirstObjectActionMessageDecoder;
import org.apollo.game.msg.decoder.FourthItemActionMessageDecoder;
import org.apollo.game.msg.decoder.KeepAliveMessageDecoder;
import org.apollo.game.msg.decoder.SecondItemActionMessageDecoder;
import org.apollo.game.msg.decoder.SecondObjectActionMessageDecoder;
import org.apollo.game.msg.decoder.SwitchItemMessageDecoder;
import org.apollo.game.msg.decoder.ThirdItemActionMessageDecoder;
import org.apollo.game.msg.decoder.ThirdObjectActionMessageDecoder;
import org.apollo.game.msg.decoder.WalkMessageDecoder;
import org.apollo.game.msg.encoder.CloseInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.EnterAmountMessageEncoder;
import org.apollo.game.msg.encoder.IdAssignmentMessageEncoder;
import org.apollo.game.msg.encoder.InterfaceItemModelMessageEncoder;
import org.apollo.game.msg.encoder.InterfaceModelAnimationMessageEncoder;
import org.apollo.game.msg.encoder.LogoutMessageEncoder;
import org.apollo.game.msg.encoder.MobModelOnInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.MobSynchronizationMessageEncoder;
import org.apollo.game.msg.encoder.OpenDialogueInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.OpenInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.OpenInterfaceSidebarMessageEncoder;
import org.apollo.game.msg.encoder.PlayerSynchronizationMessageEncoder;
import org.apollo.game.msg.encoder.RegionChangeMessageEncoder;
import org.apollo.game.msg.encoder.ServerMessageMessageEncoder;
import org.apollo.game.msg.encoder.SetInterfaceTextMessageEncoder;
import org.apollo.game.msg.encoder.SwitchTabInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.UpdateItemsMessageEncoder;
import org.apollo.game.msg.encoder.UpdateSkillMessageEncoder;
import org.apollo.game.msg.encoder.UpdateSlottedItemsMessageEncoder;
import org.apollo.game.msg.handler.ButtonMessageHandler;
import org.apollo.game.msg.handler.CharacterDesignMessageHandler;
import org.apollo.game.msg.handler.ChatMessageHandler;
import org.apollo.game.msg.handler.ClosedInterfaceMessageHandler;
import org.apollo.game.msg.handler.CommandMessageHandler;
import org.apollo.game.msg.handler.DialogueContinueMessageHandler;
import org.apollo.game.msg.handler.EnteredAmountMessageHandler;
import org.apollo.game.msg.handler.EquipMessageHandler;
import org.apollo.game.msg.handler.ItemActionMessageHandler;
import org.apollo.game.msg.handler.ObjectMessageHandler;
import org.apollo.game.msg.handler.SwitchItemMessageHandler;
import org.apollo.game.msg.handler.WalkMessageHandler;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketMetaDataGroup;

/**
 * The responsibility of this class is to translate registered {@link Message}'s
 * to their respective handler.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MessageTranslator {

    /**
     * A {@link Map} of {@link Integer}s to {@link MessageDecoder}s
     */
    private final Map<Integer, MessageDecoder<?>> decoders = new HashMap<>();

    /**
     * A {@link Map} of {@link Class}' to {@link MessageEncoder}s
     */
    private final Map<Class<?>, MessageEncoder<?>> encoders = new HashMap<>();

    /**
     * A {@link Map} of {@link Class}' to {@link MessageHandler}s
     */
    private final Map<Class<?>, MessageHandler<?>> handlers = new HashMap<>();

    /**
     * The incoming packet meta data.
     */
    private final PacketMetaDataGroup incomingPacketMetaData = PacketMetaDataGroup.create();

    /**
     * The world used for world message handlers
     */
    private final World world;

    /**
     * Constructs a new {@link MessageTranslator}.
     *
     * @param world The world used for world message handlers.
     */
    public MessageTranslator(World world) {
	this.world = world;
	registerAll();
    }

    /**
     * Registers all message decoders, encoders and handlers.
     */
    private void registerAll() {
	// register decoders
	register(new KeepAliveMessageDecoder());
	register(new CharacterDesignMessageDecoder());
	register(new WalkMessageDecoder());
	register(new ChatMessageDecoder());
	register(new ButtonMessageDecoder());
	register(new CommandMessageDecoder());
	register(new SwitchItemMessageDecoder());
	register(new FirstObjectActionMessageDecoder());
	register(new SecondObjectActionMessageDecoder());
	register(new ThirdObjectActionMessageDecoder());
	register(new EquipMessageDecoder());
	register(new FirstItemActionMessageDecoder());
	register(new SecondItemActionMessageDecoder());
	register(new ThirdItemActionMessageDecoder());
	register(new FourthItemActionMessageDecoder());
	register(new FifthItemActionMessageDecoder());
	register(new ClosedInterfaceMessageDecoder());
	register(new EnteredAmountMessageDecoder());
	register(new DialogueContinueMessageDecoder());

	// register encoders
	register(new IdAssignmentMessageEncoder());
	register(new RegionChangeMessageEncoder());
	register(new ServerMessageMessageEncoder());
	register(new MobSynchronizationMessageEncoder());
	register(new PlayerSynchronizationMessageEncoder());
	register(new OpenInterfaceMessageEncoder());
	register(new CloseInterfaceMessageEncoder());
	register(new SwitchTabInterfaceMessageEncoder());
	register(new LogoutMessageEncoder());
	register(new UpdateItemsMessageEncoder());
	register(new UpdateSlottedItemsMessageEncoder());
	register(new UpdateSkillMessageEncoder());
	register(new OpenInterfaceSidebarMessageEncoder());
	register(new EnterAmountMessageEncoder());
	register(new SetInterfaceTextMessageEncoder());
	register(new OpenDialogueInterfaceMessageEncoder());
	register(new MobModelOnInterfaceMessageEncoder());
	register(new InterfaceModelAnimationMessageEncoder());
	register(new InterfaceItemModelMessageEncoder());

	// register handlers
	register(new CharacterDesignMessageHandler());
	register(new WalkMessageHandler());
	register(new ChatMessageHandler());
	register(new CommandMessageHandler());
	register(new SwitchItemMessageHandler());
	register(new EquipMessageHandler());
	register(new ClosedInterfaceMessageHandler());
	register(new EnteredAmountMessageHandler());
	register(new DialogueContinueMessageHandler());

	// world handlers
	register(new ObjectMessageHandler(world));
	register(new ButtonMessageHandler(world));
	register(new ItemActionMessageHandler(world));
    }

    /**
     * Registers an {@link MessageDecoder} to its respective map.
     */
    private void register(MessageDecoder<?> decoder) {
	DecodesMessage annotation = decoder.getClass().getAnnotation(DecodesMessage.class);
	if (annotation == null) {
	    throw new NullPointerException(decoder.getClass().getSimpleName() + " must be annotated with @DecodesMessage");
	}
	for (int value : annotation.value()) {
	    decoders.put(value, decoder);
	}
    }

    /**
     * Registers an {@link MessageEncoder} to its respective map.
     */
    private void register(MessageEncoder<?> encoder) {
	EncodesMessage annotation = encoder.getClass().getAnnotation(EncodesMessage.class);
	if (annotation == null) {
	    throw new NullPointerException(encoder.getClass().getSimpleName() + " must be annotated with @EncodesMessage");
	}
	encoders.put(annotation.value(), encoder);
    }

    /**
     * Registers an {@link MessageHandler} to its respective map.
     */
    private void register(MessageHandler<?> handler) {
	HandlesMessage annotation = handler.getClass().getAnnotation(HandlesMessage.class);
	if (annotation == null) {
	    throw new NullPointerException(handler.getClass().getSimpleName() + " must be annotated with @HandlesMessage");
	}
	handlers.put(annotation.value(), handler);
    }

    /**
     * Returns a decoded {@link Message} or {@code null} if the
     * {@link MessageDecoder} does not exist for the specified packets opcode.
     *
     * @param packet The packet.
     */
    public Message decode(GamePacket packet) {
	@SuppressWarnings("unchecked")
	MessageDecoder<Message> decoder = (MessageDecoder<Message>) decoders.get(packet.getOpcode());
	if (decoder == null) {
	    return null;
	}
	return decoder.decode(packet);
    }

    /**
     * Returns an encoded {@link GamePacket} or {@code null} if the
     * {@link MessageEncoder} does not exist for the specified message's class.
     *
     * @param msg The message.
     */
    public GamePacket encode(Message msg) {
	@SuppressWarnings("unchecked")
	MessageEncoder<Message> encoder = (MessageEncoder<Message>) encoders.get(msg.getClass());
	if (encoder == null) {
	    return null;
	}
	return encoder.encode(msg);
    }

    /**
     * Handles a {@link Message} for a specified {@link Player} if it exists by
     * the message's class.
     *
     * @param player The player.
     * @param msg The message.
     */
    public void handle(Player player, Message msg) {
	@SuppressWarnings("unchecked")
	MessageHandler<Message> handler = (MessageHandler<Message>) handlers.get(msg.getClass());
	if (handler == null) {
	    return;
	}
	handler.handle(player, msg);
    }

    /**
     * Returns data about packet data for a specified opcode.
     *
     * @param opcode The opcode.
     */
    public PacketMetaData getIncomingPacketMetaData(int opcode) {
	return incomingPacketMetaData.getMetaData(opcode);
    }

}
