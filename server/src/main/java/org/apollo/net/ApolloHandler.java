package org.apollo.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.Attribute;

import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 * An implementation of {@link ChannelHandlerAdapter} which handles incoming
 * upstream events from Netty.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @see {@link Sharable}
 */
@Sharable
public final class ApolloHandler extends ChannelHandlerAdapter {

    /**
     * The logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ApolloHandler.class.getName());

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
	logger.info("Channel disconnected: " + channel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	Attribute<Session> attribute = ctx.attr(NetworkConstants.NETWORK_SESSION);
	Session session = attribute.get();

	if (msg.getClass() == HttpRequest.class || msg.getClass() == JagGrabRequest.class) {
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
	    throw new IllegalStateException("Invalid service id");
	}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
	Channel channel = ctx.channel();
	logger.log(Level.WARNING, "Exception occurred for channel: " + channel + ", closing...", e.getCause());
	channel.close();
    }

}
