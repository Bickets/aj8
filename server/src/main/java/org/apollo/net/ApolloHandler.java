package org.apollo.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;

import org.apollo.fs.FileSystem;
import org.apollo.game.GameService;
import org.apollo.game.msg.MessageTranslator;
import org.apollo.io.player.PlayerSerializerWorker;
import org.apollo.net.codec.handshake.HandshakeConstants;
import org.apollo.net.codec.handshake.HandshakeMessage;
import org.apollo.net.codec.jaggrab.JagGrabRequest;
import org.apollo.net.session.LoginSession;
import org.apollo.net.session.Session;
import org.apollo.net.session.UpdateSession;
import org.apollo.update.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link Sharable} implementation of {@link ChannelInboundHandlerAdapter}
 * which handles incoming upstream events from Netty.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@Sharable
public final class ApolloHandler extends ChannelInboundHandlerAdapter {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(ApolloHandler.class);

	/**
	 * The message translator.
	 */
	private final MessageTranslator messageTranslator;

	/**
	 * The file system.
	 */
	private final FileSystem fileSystem;

	/**
	 * The player serializer.
	 */
	private final PlayerSerializerWorker playerSerializer;

	/**
	 * The game service.
	 */
	private final GameService gameService;

	/**
	 * The update service.
	 */
	private final UpdateService updateService;

	/**
	 * Creates the Apollo event handler.
	 *
	 * @param messageTranslator The message translator.
	 * @param fileSystem The file system
	 * @param playerSerializer The player serializer.
	 * @param gameService The game service.
	 * @param updateService The update service.
	 */
	public ApolloHandler(MessageTranslator messageTranslator, FileSystem fileSystem, PlayerSerializerWorker playerSerializer, GameService gameService, UpdateService updateService) {
		this.messageTranslator = messageTranslator;
		this.fileSystem = fileSystem;
		this.playerSerializer = playerSerializer;
		this.gameService = gameService;
		this.updateService = updateService;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();
		Session session = ctx.attr(NetworkConstants.NETWORK_SESSION).getAndRemove();
		if (session != null) {
			session.destroy();
		}
		logger.trace("Channel disconnected: {}", channel);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			Attribute<Session> attribute = ctx.attr(NetworkConstants.NETWORK_SESSION);
			Session session = attribute.get();

			if (msg instanceof HttpRequest || msg instanceof JagGrabRequest) {
				session = new UpdateSession(ctx, updateService);
			}

			if (session != null) {
				session.messageReceived(msg);
				return;
			}

			HandshakeMessage handshakeMessage = (HandshakeMessage) msg;
			switch (handshakeMessage.getServiceId()) {
			case HandshakeConstants.SERVICE_GAME:
				attribute.set(new LoginSession(ctx, messageTranslator, fileSystem, playerSerializer, gameService));
				break;
			case HandshakeConstants.SERVICE_UPDATE:
				attribute.set(new UpdateSession(ctx, updateService));
				break;
			default:
				throw new UnsupportedOperationException("Unexpected service id: " + handshakeMessage.getServiceId());
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		Channel channel = ctx.channel();
		if (shouldNotify(e)) {
			logger.error("Exception occurred for channel: {}, closing...", channel, e);
		}
		channel.close();
	}

	/**
	 * Tests if the specified throwable should be notified.
	 *
	 * @param e The throwable.
	 * @return {@code true} if and only if the specified {@link Throwable}
	 *         should be notified otherwise {@code false}.
	 */
	private boolean shouldNotify(Throwable e) {
		String msg = e.getMessage();

		if (msg == null) {
			return true;
		}

		// TODO: A proper way to manage this??
		if (msg.equals("An existing connection was forcibly closed by the remote host")) {
			return false;
		}

		return true;
	}

}