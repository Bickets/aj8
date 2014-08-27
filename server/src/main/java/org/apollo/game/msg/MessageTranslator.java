package org.apollo.game.msg;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.annotate.HandlesMessage;
import org.apollo.game.msg.decoder.ButtonMessageDecoder;
import org.apollo.game.msg.decoder.CameraMovementMessageDecoder;
import org.apollo.game.msg.decoder.CharacterDesignMessageDecoder;
import org.apollo.game.msg.decoder.ChatMessageDecoder;
import org.apollo.game.msg.decoder.ClientFocusedMessageDecoder;
import org.apollo.game.msg.decoder.ClosedInterfaceMessageDecoder;
import org.apollo.game.msg.decoder.CommandMessageDecoder;
import org.apollo.game.msg.decoder.DialogueContinueMessageDecoder;
import org.apollo.game.msg.decoder.DropItemMessageDecoder;
import org.apollo.game.msg.decoder.EnteredAmountMessageDecoder;
import org.apollo.game.msg.decoder.FifthItemActionMessageDecoder;
import org.apollo.game.msg.decoder.FirstInventoryItemActionMessageDecoder;
import org.apollo.game.msg.decoder.FirstItemActionMessageDecoder;
import org.apollo.game.msg.decoder.FirstObjectActionMessageDecoder;
import org.apollo.game.msg.decoder.FirstSingleItemActionMessageDecoder;
import org.apollo.game.msg.decoder.FourthItemActionMessageDecoder;
import org.apollo.game.msg.decoder.IdleMessageDecoder;
import org.apollo.game.msg.decoder.KeepAliveMessageDecoder;
import org.apollo.game.msg.decoder.MouseClickMessageDecoder;
import org.apollo.game.msg.decoder.ObsoleteMessageDecoder;
import org.apollo.game.msg.decoder.RegionLoadedMessageDecoder;
import org.apollo.game.msg.decoder.SecondItemActionMessageDecoder;
import org.apollo.game.msg.decoder.SecondObjectActionMessageDecoder;
import org.apollo.game.msg.decoder.SecondSingleItemActionMessageDecoder;
import org.apollo.game.msg.decoder.SwitchItemMessageDecoder;
import org.apollo.game.msg.decoder.ThirdItemActionMessageDecoder;
import org.apollo.game.msg.decoder.ThirdObjectActionMessageDecoder;
import org.apollo.game.msg.decoder.ThirdInventoryItemActionMessageDecoder;
import org.apollo.game.msg.decoder.WalkMessageDecoder;
import org.apollo.game.msg.encoder.CharacterHintIconMessageEncoder;
import org.apollo.game.msg.encoder.CloseInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.EnterAmountMessageEncoder;
import org.apollo.game.msg.encoder.GameObjectMessageEncoder;
import org.apollo.game.msg.encoder.GroundItemMessageEncoder;
import org.apollo.game.msg.encoder.IdAssignmentMessageEncoder;
import org.apollo.game.msg.encoder.InterfaceItemModelMessageEncoder;
import org.apollo.game.msg.encoder.InterfaceModelAnimationMessageEncoder;
import org.apollo.game.msg.encoder.LocationHintIconMessageEncoder;
import org.apollo.game.msg.encoder.LogoutMessageEncoder;
import org.apollo.game.msg.encoder.MobModelOnInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.MobSynchronizationMessageEncoder;
import org.apollo.game.msg.encoder.OpenDialogueInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.OpenInterfaceMessageEncoder;
import org.apollo.game.msg.encoder.OpenInterfaceSidebarMessageEncoder;
import org.apollo.game.msg.encoder.PlayerSynchronizationMessageEncoder;
import org.apollo.game.msg.encoder.PositionMessageEncoder;
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
import org.apollo.game.msg.handler.DropItemMessageHandler;
import org.apollo.game.msg.handler.EnteredAmountMessageHandler;
import org.apollo.game.msg.handler.ItemActionMessageHandler;
import org.apollo.game.msg.handler.ObjectMessageHandler;
import org.apollo.game.msg.handler.RegionLoadedMessageHandler;
import org.apollo.game.msg.handler.SwitchItemMessageHandler;
import org.apollo.game.msg.handler.WalkMessageHandler;
import org.apollo.net.codec.game.GamePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The responsibility of this class is to translate registered {@link Message}'s
 * to their respective handler.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MessageTranslator {

    /**
     * The logger used to print information and debug messages to the console.
     */
    private final Logger logger = LoggerFactory.getLogger(MessageTranslator.class);

    /**
     * A {@link Map} of {@link Integer}s to {@link MessageDecoder}s.
     */
    private final Map<Integer, MessageDecoder<?>> decoders = new HashMap<>();

    /**
     * A {@link Map} of {@link Class}' to {@link MessageEncoder}s.
     */
    private final Map<Class<?>, MessageEncoder<?>> encoders = new HashMap<>();

    /**
     * A {@link Map} of {@link Class}' to {@link MessageHandler}s.
     */
    private final Map<Class<?>, MessageHandler<?>> handlers = new HashMap<>();

    /**
     * The world used for world message handlers.
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
	register(new ClosedInterfaceMessageDecoder());
	register(new EnteredAmountMessageDecoder());
	register(new DialogueContinueMessageDecoder());
	register(new ClientFocusedMessageDecoder());
	register(new MouseClickMessageDecoder());
	register(new IdleMessageDecoder());
	register(new RegionLoadedMessageDecoder());
	register(new ObsoleteMessageDecoder());
	register(new CameraMovementMessageDecoder());
	register(new DropItemMessageDecoder());

	register(new FirstInventoryItemActionMessageDecoder());

	register(new FirstSingleItemActionMessageDecoder());
	register(new SecondSingleItemActionMessageDecoder());
	register(new ThirdInventoryItemActionMessageDecoder());

	register(new FirstItemActionMessageDecoder());
	register(new ThirdItemActionMessageDecoder());
	register(new SecondItemActionMessageDecoder());
	register(new FourthItemActionMessageDecoder());
	register(new FifthItemActionMessageDecoder());

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
	register(new PositionMessageEncoder());
	register(new GameObjectMessageEncoder());
	register(new GroundItemMessageEncoder());
	register(new LocationHintIconMessageEncoder());
	register(new CharacterHintIconMessageEncoder());

	// register handlers
	register(new CharacterDesignMessageHandler());
	register(new WalkMessageHandler());
	register(new ChatMessageHandler());
	register(new SwitchItemMessageHandler());
	register(new ClosedInterfaceMessageHandler());
	register(new EnteredAmountMessageHandler());
	register(new DialogueContinueMessageHandler());
	register(new RegionLoadedMessageHandler());

	// world handlers
	register(new ObjectMessageHandler(world));
	register(new ButtonMessageHandler(world));
	register(new CommandMessageHandler(world));
	register(new ItemActionMessageHandler(world));
	register(new DropItemMessageHandler(world));
    }

    /**
     * Registers an {@link MessageDecoder} to its respective map.
     *
     * @param decoder The message decoder to register.
     */
    private <E extends Message> void register(MessageDecoder<E> decoder) {
	DecodesMessage annotation = decoder.getClass().getAnnotation(DecodesMessage.class);
	if (annotation == null) {
	    throw new NullPointerException(decoder + " must be annotated with @DecodesMessage");
	}

	for (int value : annotation.value()) {
	    decoders.put(value, decoder);
	}
    }

    /**
     * Registers an {@link MessageEncoder} to its respective map.
     *
     * @param encoder The message encoder to register.
     */
    private <E extends Message> void register(MessageEncoder<E> encoder) {
	EncodesMessage annotation = encoder.getClass().getAnnotation(EncodesMessage.class);
	if (annotation == null) {
	    throw new NullPointerException(encoder + " must be annotated with @EncodesMessage");
	}

	encoders.put(annotation.value(), encoder);
    }

    /**
     * Registers an {@link MessageHandler} to its respective map.
     *
     * @param handler The message handler to register.
     */
    private <E extends Message> void register(MessageHandler<E> handler) {
	HandlesMessage annotation = handler.getClass().getAnnotation(HandlesMessage.class);
	if (annotation == null) {
	    throw new NullPointerException(handler + " must be annotated with @HandlesMessage");
	}

	handlers.put(annotation.value(), handler);
    }

    /**
     * Attempts to decode the specified {@code packet} into a {@link Message}.
     *
     * @param packet The packet.
     * @return
     * @return The decoded message if and only if it was decoded successfully,
     *         otherwise {@code null}.
     */
    @SuppressWarnings("unchecked")
    public <E extends Message> E decode(GamePacket packet) {
	MessageDecoder<E> decoder = (MessageDecoder<E>) decoders.get(packet.getOpcode());
	if (decoder == null) {
	    logger.error("No message decoder for packet: {}", packet.getOpcode());
	    return null;
	}

	return decoder.decode(packet);
    }

    /**
     * Attempts to encode the specified {@code msg} into a {@link GamePacket}.
     *
     * @param msg The message.
     * @return The encoded game packet, if and only if it was encoded
     *         successfully, otherwise {@code null}.
     */
    @SuppressWarnings("unchecked")
    public <E extends Message> GamePacket encode(E msg) {
	MessageEncoder<E> encoder = (MessageEncoder<E>) encoders.get(msg.getClass());
	if (encoder == null) {
	    logger.error("No message encoder for message: {}", msg);
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
    @SuppressWarnings("unchecked")
    public <E extends Message> void handle(Player player, E msg) {
	MessageHandler<E> handler = (MessageHandler<E>) handlers.get(msg.getClass());
	if (handler == null) {
	    return;
	}

	handler.handle(player, msg);
    }

}