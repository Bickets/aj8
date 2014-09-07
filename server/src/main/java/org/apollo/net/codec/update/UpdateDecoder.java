package org.apollo.net.codec.update;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * A {@link ByteToMessageDecoder} for the 'on-demand' protocol.
 *
 * @author Graham
 */
public final class UpdateDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		if (in.readableBytes() < 4) {
			return;
		}

		int index = in.readUnsignedByte() + 1;
		int id = in.readUnsignedShort();
		int priority = in.readUnsignedByte();

		out.add(new OnDemandRequest(index, id, priority));
	}

}