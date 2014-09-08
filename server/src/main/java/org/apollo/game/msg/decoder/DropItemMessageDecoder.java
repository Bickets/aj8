package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.DropItemMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Defines the decoder for a {@link DropItemMessage}.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
@DecodesMessage(87)
public final class DropItemMessageDecoder implements MessageDecoder<DropItemMessage> {

	@Override
	public DropItemMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int itemId = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
		int slotId = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		return new DropItemMessage(itemId, interfaceId, slotId);
	}

}