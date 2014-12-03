package org.apollo.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;

import org.apollo.ServerContext;
import org.apollo.game.GameService;
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
	 * The context of this server.
	 */
	private final ServerContext context;

	/**
	 * Constructs a new {@link ApolloHandler} with the specified
	 * {@link ServerContext}.
	 *
	 * @param context The context of this server.
	 */
	public ApolloHandler(ServerContext context) {
		this.context = context;
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
				session = new UpdateSession(ctx, context.getService(UpdateService.class));
			}

			if (session != null) {
				session.messageReceived(msg);
				return;
			}

			HandshakeMessage handshakeMessage = (HandshakeMessage) msg;
			switch (handshakeMessage.getServiceId()) {
			case HandshakeConstants.SERVICE_GAME:
				attribute.set(new LoginSession(ctx, context.getService(GameService.class)));
				break;
			case HandshakeConstants.SERVICE_UPDATE:
				attribute.set(new UpdateSession(ctx, context.getService(UpdateService.class)));
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
		return !msg.equals("An existing connection was forcibly closed by the remote host");
	}

}