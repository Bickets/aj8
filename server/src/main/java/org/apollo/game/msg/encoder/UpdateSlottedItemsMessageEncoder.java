package org.apollo.game.msg.encoder;

import org.apollo.game.model.Item;
import org.apollo.game.model.SlottedItem;
import org.apollo.game.msg.MessageEncoder;
import org.apollo.game.msg.annotate.EncodesMessage;
import org.apollo.game.msg.impl.UpdateSlottedItemsMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.codec.game.GamePacketType;

/**
 * An {@link MessageEncoder} for the {@link UpdateSlottedItemsMessage}.
 *
 * @author Graham
 */
@EncodesMessage(UpdateSlottedItemsMessage.class)
public final class UpdateSlottedItemsMessageEncoder implements MessageEncoder<UpdateSlottedItemsMessage> {

    @Override
    public GamePacket encode(UpdateSlottedItemsMessage message) {
	GamePacketBuilder builder = new GamePacketBuilder(34, GamePacketType.VARIABLE_SHORT);
	SlottedItem[] items = message.getSlottedItems();

	builder.put(DataType.SHORT, message.getInterfaceId());

	for (SlottedItem slottedItem : items) {
	    builder.putSmart(slottedItem.getSlot());

	    Item item = slottedItem.getItem();
	    int id = item == null ? -1 : item.getId();
	    int amount = item == null ? 0 : item.getAmount();

	    builder.put(DataType.SHORT, id + 1);

	    if (amount > 254) {
		builder.put(DataType.BYTE, 255);
		builder.put(DataType.INT, amount);
	    } else {
		builder.put(DataType.BYTE, amount);
	    }
	}

	return builder.toGamePacket();
    }

}
