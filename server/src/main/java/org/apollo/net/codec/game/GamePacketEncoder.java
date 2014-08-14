package org.apollo.net.codec.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.burtleburtle.bob.rand.IsaacAlgorithm;

/**
 * A {@link OneToOneEncoder} which encodes in-game packets.
 *
 * @author Graham
 */
public final class GamePacketEncoder extends MessageToByteEncoder<GamePacket> {

    /**
     * The random number generator.
     */
    private final IsaacAlgorithm random;

    /**
     * Creates the {@link GamePacketEncoder}.
     *
     * @param random The random number generator.
     */
    public GamePacketEncoder(IsaacAlgorithm random) {
	this.random = random;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, GamePacket msg, ByteBuf out) throws Exception {
	out.writeByte(msg.getOpcode() + random.nextInt() & 0xFF);

	switch (msg.getType()) {
	case VARIABLE_BYTE:
	    out.writeByte(msg.getLength());
	    break;
	case VARIABLE_SHORT:
	    out.writeShort(msg.getLength());
	    break;
	default:
	    break;
	}

	out.writeBytes(msg.getPayload());
    }

}