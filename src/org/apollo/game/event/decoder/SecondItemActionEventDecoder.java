package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.SecondItemActionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link SecondItemActionEvent}.
 * 
 * @author Graham
 */
public final class SecondItemActionEventDecoder extends EventDecoder<SecondItemActionEvent> {

    public SecondItemActionEventDecoder(int opcode) {
        super(opcode);
    }

    @Override
    public SecondItemActionEvent decode(GamePacket packet) {
        GamePacketReader reader = new GamePacketReader(packet);
        int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
        int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
        int slot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
        return new SecondItemActionEvent(interfaceId, id, slot);
    }

}