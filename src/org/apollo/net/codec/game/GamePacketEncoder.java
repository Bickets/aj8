
package org.apollo.net.codec.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import net.burtleburtle.bob.rand.IsaacAlgorithm;

import org.apollo.net.meta.PacketType;

/**
 * A {@link OneToOneEncoder} which encodes in-game packets.
 * @author Graham
 */
public final class GamePacketEncoder extends MessageToMessageEncoder<GamePacket>
{

	/**
	 * The random number generator.
	 */
	private final IsaacAlgorithm random;


	/**
	 * Creates the {@link GamePacketEncoder}.
	 * @param random The random number generator.
	 */
	public GamePacketEncoder( IsaacAlgorithm random )
	{
		this.random = random;
	}


	@Override
	protected void encode( ChannelHandlerContext ctx, GamePacket msg, List<Object> out )
	{
		PacketType type = msg.getType();
		int headerLength = 1;
		int payloadLength = msg.getLength();

		if( type == PacketType.VARIABLE_BYTE ) {
			headerLength ++ ;
			if( payloadLength >= 256 ) {
				throw new IllegalStateException( "Payload too long for variable byte packet" );
			}
		} else if( type == PacketType.VARIABLE_SHORT ) {
			headerLength += 2;
			if( payloadLength >= 65536 ) {
				throw new IllegalStateException( "Payload too long for variable short packet" );
			}
		}

		ByteBuf buffer = Unpooled.buffer( headerLength + payloadLength );
		buffer.writeByte( ( msg.getOpcode() + random.nextInt() ) & 0xFF );

		if( type == PacketType.VARIABLE_BYTE ) {
			buffer.writeByte( payloadLength );
		} else if( type == PacketType.VARIABLE_SHORT ) {
			buffer.writeShort( payloadLength );
		}

		out.add( buffer.writeBytes( msg.getPayload() ) );
	}

}
