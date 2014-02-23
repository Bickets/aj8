
package org.apollo.net.codec.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import net.burtleburtle.bob.rand.IsaacAlgorithm;

import org.apollo.game.event.EventTranslator;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketType;
import org.apollo.util.StatefulByteToMessageDecoder;

/**
 * A {@link StatefulByteToMessageDecoder} which decodes game packets.
 * @author Graham
 */
public final class GamePacketDecoder extends StatefulByteToMessageDecoder<GameDecoderState>
{

	/**
	 * The random number generator.
	 */
	private final IsaacAlgorithm random;

	/**
	 * The current opcode.
	 */
	private int opcode;

	/**
	 * The packet type.
	 */
	private PacketType type;

	/**
	 * The current length.
	 */
	private int length;

	/**
	 * The event translator.
	 */
	private final EventTranslator translator;


	/**
	 * Creates the {@link GamePacketDecoder}.
	 * @param random The random number generator.
	 * @param translator The event translator.
	 */
	public GamePacketDecoder( IsaacAlgorithm random, EventTranslator translator )
	{
		super( GameDecoderState.GAME_OPCODE );
		this.random = random;
		this.translator = translator;
	}


	@Override
	protected void decode( ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out, GameDecoderState state )
	{
		switch( state ) {
			case GAME_OPCODE:
				decodeOpcode( ctx, buffer, out );
				break;
			case GAME_LENGTH:
				decodeLength( ctx, buffer, out );
				break;
			case GAME_PAYLOAD:
				decodePayload( ctx, buffer, out );
				break;
			default:
				throw new IllegalStateException( "Invalid game decoder state" );
		}
	}


	/**
	 * Decodes the opcode state.
	 * @param ctx The channels context.
	 * @param in The input buffer.
	 * @param out The {@link List} to which written data should be added to.
	 */
	private void decodeOpcode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out )
	{
		if( ! in.isReadable() ) {
			return;
		}

		int encryptedOpcode = in.readUnsignedByte();
		opcode = ( encryptedOpcode - random.nextInt() ) & 0xFF;

		PacketMetaData metaData = translator.getIncomingPacketMetaData( opcode );
		if( metaData == null ) {
			throw new IllegalStateException( "Illegal opcode: " + opcode );
		}

		type = metaData.getType();
		switch( type ) {
			case FIXED:
				length = metaData.getLength();
				if( length != 0 ) {
					setState( GameDecoderState.GAME_PAYLOAD );
				}
				break;
			case VARIABLE_BYTE:
				setState( GameDecoderState.GAME_LENGTH );
				break;
			default:
				throw new IllegalStateException( "Illegal packet type: " + type );
		}
	}


	/**
	 * Decodes the length state.
	 * @param ctx The channels context.
	 * @param in The input buffer.
	 * @param out The {@link List} to which written data should be added to.
	 */
	private void decodeLength( ChannelHandlerContext ctx, ByteBuf in, List<Object> out )
	{
		if( ! in.isReadable() ) {
			return;
		}

		length = in.readUnsignedByte();
		if( length != 0 ) {
			setState( GameDecoderState.GAME_PAYLOAD );
		}
	}


	/**
	 * Decodes the payload state.
	 * @param ctx The channels context.
	 * @param in The input buffer.
	 * @param out The {@link List} to which written data should be added to.
	 */
	private void decodePayload( ChannelHandlerContext ctx, ByteBuf in, List<Object> out )
	{
		if( in.readableBytes() < length ) {
			return;
		}

		ByteBuf payload = in.readBytes( length );
		setState( GameDecoderState.GAME_OPCODE );
		out.add( new GamePacket( opcode, type, payload ) );
	}

}
