package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ItemOnItemMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * This class decodes the {@link ItemOnItemMessage}.
 *
 * @author Tyler Buchanan <https://www.github.com/TylerBuchanan97>
 */
@DecodesMessage(53)
public final class ItemOnItemMessageDecoder implements MessageDecoder<ItemOnItemMessage> {

	@Override
	public ItemOnItemMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int receiverSlot = (int) reader.getUnsigned(DataType.SHORT);
		int senderSlot = (int) reader.getSigned(DataType.SHORT, DataTransformation.ADD);
		return new ItemOnItemMessage(receiverSlot, senderSlot);
	}

}