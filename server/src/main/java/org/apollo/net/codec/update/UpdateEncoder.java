package org.apollo.net.codec.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * A {@link OneToOneEncoder} for the 'on-demand' protocol.
 * 
 * @author Graham
 */
public final class UpdateEncoder extends MessageToMessageEncoder<OnDemandResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, OnDemandResponse msg, List<Object> out) {
	ByteBuf chunkData = msg.getChunkData();

	ByteBuf buf = Unpooled.buffer(6 + chunkData.readableBytes());
	buf.writeByte(msg.getIndex() - 1);
	buf.writeShort(msg.getId());
	buf.writeShort(msg.getFileSize());
	buf.writeByte(msg.getChunkId());
	buf.writeBytes(chunkData);

	out.add(buf);
    }

}
