package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.ItemActionEvent;
import org.apollo.game.model.InterfaceConstants;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link FirstItemActionEvent}.
 * 
 * @author Graham
 */
@DecodesEvent(145)
public final class FirstItemActionEventDecoder extends EventDecoder<ItemActionEvent> {

    @Override
    public ItemActionEvent decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	return new ItemActionEvent(InterfaceConstants.OPTION_ONE, interfaceId, id, slot);
    }

}
