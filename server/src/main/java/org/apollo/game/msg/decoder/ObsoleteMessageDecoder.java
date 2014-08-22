package org.apollo.game.msg.decoder;

import io.netty.buffer.Unpooled;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ObsoleteMessage;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Decodes the {@link ObsoleteMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage({ 36, 77, 78, 210, 226 })
public final class ObsoleteMessageDecoder implements MessageDecoder<ObsoleteMessage> {

    @Override
    public ObsoleteMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	byte[] bytes = new byte[packet.getLength()];
	reader.getBytes(bytes);
	return new ObsoleteMessage(Unpooled.wrappedBuffer(bytes));
    }

}