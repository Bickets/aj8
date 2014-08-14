package org.apollo.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import org.apollo.net.codec.handshake.HandshakeDecoder;

/**
 * A {@link ChannelInitializer} which creates {@link ChannelPipeline}s for the
 * service pipeline.
 *
 * @author Graham
 */
public final class ServiceChannelHandler extends ChannelInitializer<SocketChannel> {

    /**
     * The network event handler.
     */
    private final ApolloHandler handler;

    /**
     * Creates the service pipeline factory.
     *
     * @param handler The networking event handler.
     */
    public ServiceChannelHandler(ApolloHandler handler) {
	this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
	ChannelPipeline pipeline = channel.pipeline();

	pipeline.addLast("handshakeDecoder", new HandshakeDecoder());
	pipeline.addLast("timeout", new IdleStateHandler(NetworkConstants.IDLE_TIME, 0, 0));
	pipeline.addLast("handler", handler);
    }

}