package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ChatMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.util.TextUtil;

/**
 * An {@link MessageDecoder} for the {@link ChatMessage}.
 *
 * @author Graham
 */
@DecodesMessage(4)
public final class ChatMessageDecoder implements MessageDecoder<ChatMessage> {

    @Override
    public ChatMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);

	int effects = (int) reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT);
	int color = (int) reader.getUnsigned(DataType.BYTE, DataTransformation.SUBTRACT);
	int length = packet.getLength() - 2;

	byte[] originalCompressed = new byte[length];
	reader.getBytesReverse(DataTransformation.ADD, originalCompressed);

	String uncompressed = TextUtil.uncompress(originalCompressed, length);
	uncompressed = TextUtil.filterInvalidCharacters(uncompressed);
	uncompressed = TextUtil.capitalize(uncompressed);

	byte[] recompressed = new byte[length];
	TextUtil.compress(uncompressed, recompressed);

	return new ChatMessage(uncompressed, recompressed, color, effects);
    }

}