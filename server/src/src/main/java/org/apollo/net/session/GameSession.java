package org.apollo.net.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apollo.game.GameService;
import org.apollo.game.model.Player;
import org.apollo.game.msg.Message;
import org.apollo.game.msg.impl.LogoutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A game session.
 *
 * @author Graham
 */
public final class GameSession extends Session {

	/**
	 * The maximum message per pulse per session.
	 */
	public static final int MESSAGES_PER_PULSE = 10;

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(GameSession.class);

	/**
	 * The queue of pending {@link Message}s.
	 */
	private final BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(MESSAGES_PER_PULSE);

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The game service.
	 */
	private final GameService gameService;

	/**
	 * Creates a login session for the specified channel handler context.
	 *
	 * @param ctx This sessions channels context.
	 * @param player The player.
	 * @param gameService The game service.
	 */
	public GameSession(ChannelHandlerContext ctx, Player player, GameService gameService) {
		super(ctx);
		this.player = player;
		this.gameService = gameService;
	}

	@Override
	public void messageReceived(Object msg) {
		Message message = (Message) msg;
		if (messageQueue.size() >= MESSAGES_PER_PULSE) {
			logger.trace("Too many messages in queue for game session, dropping...");
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
		for (;;) {
			Message message = messageQueue.poll();
			if (message == null) {
				break;
			}
			gameService.getMessageTranslator().handle(player, message);
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