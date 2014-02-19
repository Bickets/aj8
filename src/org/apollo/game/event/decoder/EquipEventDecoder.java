package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.EquipEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link EquipEvent}.
 * @author Graham
 */
public final class EquipEventDecoder extends EventDecoder<EquipEvent> {

    public EquipEventDecoder(int opcode) {
        super(opcode);
    }

    @Override
    public EquipEvent decode(GamePacket packet) {
        GamePacketReader reader = new GamePacketReader(packet);
        int id = (int) reader.getUnsigned(DataType.SHORT);
        int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
        int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
        return new EquipEvent(interfaceId, id, slot);
    }

}
