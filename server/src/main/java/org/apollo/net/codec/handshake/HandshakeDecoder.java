package org.apollo.net.codec.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apollo.net.codec.login.LoginDecoder;
import org.apollo.net.codec.login.LoginEncoder;
import org.apollo.net.codec.update.UpdateDecoder;
import org.apollo.net.codec.update.UpdateEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link ByteToMessageDecoder} which decodes the handshake and makes changes
 * to the pipeline as appropriate for the selected service.
 *
 * @author Graham
 */
public final class HandshakeDecoder extends ByteToMessageDecoder {

	/**
	 * The logger used to print information and debug messages to the console.
	 */
	private final Logger logger = LoggerFactory.getLogger(HandshakeDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
		if (!buffer.isReadable()) {
			return;
		}

		int id = buffer.readUnsignedByte();

		switch (id) {
		case HandshakeConstants.SERVICE_GAME:
			ctx.pipeline().addFirst("loginEncoder", new LoginEncoder());
			ctx.pipeline().addAfter("handshakeDecoder", "loginDecoder", new LoginDecoder());
			break;

		case HandshakeConstants.SERVICE_UPDATE:
			ctx.pipeline().addFirst("updateEncoder", new UpdateEncoder());
			ctx.pipeline().addBefore("handler", "updateDecoder", new UpdateDecoder());

			// XXX: Better way?
			ByteBuf buf = ctx.alloc().buffer(8).writeLong(0);
			ctx.channel().writeAndFlush(buf);
			break;

		default:
			logger.trace("Unexpected service id received: {} -- potential flood; ignoring request.", id);
			return;
		}

		ctx.pipeline().remove(this);
		out.add(new HandshakeMessage(id));
	}

}