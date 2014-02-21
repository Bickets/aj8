
package org.apollo.net.codec.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.apollo.fs.FileDescriptor;

/**
 * A {@link OneToOneEncoder} for the 'on-demand' protocol.
 * @author Graham
 */
public final class UpdateEncoder extends MessageToMessageEncoder<OnDemandResponse>
{

	@Override
	protected void encode( ChannelHandlerContext ctx, OnDemandResponse msg, List<Object> out )
	{
		FileDescriptor fileDescriptor = msg.getFileDescriptor();
		int fileSize = msg.getFileSize();
		int chunkId = msg.getChunkId();
		ByteBuf chunkData = msg.getChunkData();

		ByteBuf buf = Unpooled.buffer( 6 + chunkData.readableBytes() );
		buf.writeByte( fileDescriptor.getType() - 1 );
		buf.writeShort( fileDescriptor.getFile() );
		buf.writeShort( fileSize );
		buf.writeByte( chunkId );
		buf.writeBytes( chunkData );

		out.add( buf );
	}

}
