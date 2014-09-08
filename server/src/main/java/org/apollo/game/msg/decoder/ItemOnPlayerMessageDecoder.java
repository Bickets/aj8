package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ItemOnPlayerMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * This class decodes the message sent when an item is used on another player.
 * 
 * @author Tyler Buchanan <http://www.github.com/TylerBuchanan97)
 */
@DecodesMessage(14)
public final class ItemOnPlayerMessageDecoder implements MessageDecoder<ItemOnPlayerMessage> {

	@Override
	public ItemOnPlayerMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int victimId = (int) reader.getUnsigned(DataType.SHORT);
		int itemId = (int) reader.getUnsigned(DataType.SHORT);
		int slotId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		return new ItemOnPlayerMessage(interfaceId, victimId, itemId, slotId);
	}

}