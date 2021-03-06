package org.apollo.game.msg.encoder;

import org.apollo.game.model.Item;
import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.UpdateItemsMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link UpdateItemsMessage}.
 *
 * @author Graham
 */
@EncodesMessage(UpdateItemsMessage.class)
public final class UpdateItemsMessageEncoder implements MessageEncoder<UpdateItemsMessage> {

	@Override
	public GamePacket encode(UpdateItemsMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(53, GamePacketType.VARIABLE_SHORT);

		Item[] items = message.getItems();

		builder.put(DataType.SHORT, message.getInterfaceId());
		builder.put(DataType.SHORT, items.length);

		for (Item item : items) {
			int id = item == null ? -1 : item.getId();
			int amount = item == null ? 0 : item.getAmount();

			if (amount > 254) {
				builder.put(DataType.BYTE, 255);
				builder.put(DataType.INT, DataOrder.INVERSED_MIDDLE, amount);
			} else {
				builder.put(DataType.BYTE, amount);
			}

			builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, id + 1);
		}

		return builder.toGamePacket();
	}

}