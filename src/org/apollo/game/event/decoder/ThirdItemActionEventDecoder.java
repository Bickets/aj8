package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.ThirdItemActionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link ThirdItemActionEvent}.
 * 
 * @author Graham
 */
public final class ThirdItemActionEventDecoder extends EventDecoder<ThirdItemActionEvent> {

    public ThirdItemActionEventDecoder(int opcode) {
        super(opcode);
    }

    @Override
    public ThirdItemActionEvent decode(GamePacket packet) {
        GamePacketReader reader = new GamePacketReader(packet);
        int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
        int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
        int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
        return new ThirdItemActionEvent(interfaceId, id, slot);
    }

}