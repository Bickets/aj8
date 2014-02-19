
package org.apollo.net.codec.login;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * A class which encodes login response messsages.
 * @author Graham
 */
public final class LoginEncoder extends MessageToMessageEncoder<LoginResponse>
{

	@Override
	protected void encode( ChannelHandlerContext ctx, LoginResponse msg, List<Object> out )
	{
		ByteBuf buffer = Unpooled.buffer( 3 );
		buffer.writeByte( msg.getStatus() );
		if( msg.getStatus() == LoginConstants.STATUS_OK ) {
			buffer.writeByte( msg.getRights() );
			buffer.writeByte( msg.isFlagged() ? 1: 0 );
		}
		out.add( buffer );
	}

}
