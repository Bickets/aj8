package org.apollo.net.codec.game;

import static org.apollo.game.model.def.GamePacketDefinition.VAR_BYTE;
import static org.apollo.game.model.def.GamePacketDefinition.incomingDefinition;
import static org.apollo.net.codec.game.GameDecoderState.GAME_LENGTH;
import static org.apollo.net.codec.game.GameDecoderState.GAME_OPCODE;
import static org.apollo.net.codec.game.GameDecoderState.GAME_PAYLOAD;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import net.burtleburtle.bob.rand.IsaacAlgorithm;

import org.apollo.game.model.def.GamePacketDefinition;

/**
 * A {@link ByteToMessageDecoder} which decodes game packets.
 *
 * @author Graham
 */
public final class GamePacketDecoder extends ByteToMessageDecoder {

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
	private GamePacketType type;

	/**
	 * The current length.
	 */
	private int length;

	/**
	 * The actual length.
	 */
	private int actualLength;

	/**
	 * The current decode state.
	 */
	private GameDecoderState state = GAME_OPCODE;

	/**
	 * Creates the {@link GamePacketDecoder}.
	 *
	 * @param random The random number generator.
	 */
	public GamePacketDecoder(IsaacAlgorithm random) {
		this.random = random;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
		while (buffer.isReadable()) {
			if (state == GAME_OPCODE) {

				opcode = buffer.readUnsignedByte() - random.nextInt() & 0xFF;

				GamePacketDefinition def = incomingDefinition(opcode);
				if (def == null) {
					throw new RuntimeException("Illegal opcode: " + opcode);
				}

				length = actualLength = def.getLength();
				type = def.getType();

				state = length >= 0 ? GAME_PAYLOAD : GAME_LENGTH;
			}

			if (state == GAME_LENGTH) {

				int check = length == VAR_BYTE ? 1 : 2;
				if (!buffer.isReadable(check)) {
					return;
				}

				actualLength = 0;
				for (int i = 0; i < check; i++) {
					actualLength |= (buffer.readByte() & 0xFF) << 8 * (check - 1 - i);
				}

				state = GAME_PAYLOAD;
			}

			if (state == GAME_PAYLOAD) {

				if (!buffer.isReadable(actualLength)) {
					return;
				}

				ByteBuf packetBuf = buffer.readBytes(actualLength);

				out.add(new GamePacket(opcode, type, packetBuf));

				state = GAME_OPCODE;
			}
		}
	}

}