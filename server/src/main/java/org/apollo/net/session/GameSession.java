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
import org.apollo.game.model.Player;
import org.apollo.game.msg.Message;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.game.msg.impl.LogoutMessage;

/**
 * A game session.
 *
 * @author Graham
 */
public final class GameSession extends Session {

    /**
     * The logger for this class.
     */
    private static final Logger logger = Logger.getLogger(GameSession.class.getName());

    /**
     * The message translator.
     */
    private final MessageTranslator messageTranslator;

    /**
     * The queue of pending {@link Message}s.
     */
    private final BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(GameConstants.MESSAGES_PER_PULSE);

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
     *
     * @param ctx This sessions channels context.
     * @param messageTranslator The message translator.
     * @param player The player.
     * @param gameService The game service.
     */
    public GameSession(ChannelHandlerContext ctx, MessageTranslator messageTranslator, Player player, GameService gameService) {
	super(ctx);
	this.messageTranslator = messageTranslator;
	this.player = player;
	this.gameService = gameService;
    }

    @Override
    public void messageReceived(Object msg) {
	Message message = (Message) msg;
	if (messageQueue.size() >= GameConstants.MESSAGES_PER_PULSE) {
	    logger.warning("Too many messages in queue for game session, dropping...");
	} else {
	    messageQueue.add(message);
	}
    }

    /**
     * Encodes and dispatches the specified message.
     *
     * @param message The message
     */
    public void dispatchMessage(Message message) {
	Channel channel = ctx().channel();
	if (channel.isActive()) {
	    ChannelFuture future = channel.writeAndFlush(message);
	    if (message.getClass() == LogoutMessage.class) {
		future.addListener(ChannelFutureListener.CLOSE);
	    }
	}
    }

    /**
     * Handles pending messages for this session.
     */
    public void handlePendingMessages() {
	for (Message message = messageQueue.poll(); message != null;) {
	    messageTranslator.handle(player, message);
	}
    }

    /**
     * Handles a player saver response.
     */
    public void handlePlayerSaverResponse() {
	gameService.finalizePlayerUnregistration(player);
    }

    @Override
    public void destroy() {
	gameService.unregisterPlayer(player);
    }

}
