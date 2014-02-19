package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.impl.IdAssignmentEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link IdAssignmentEvent}.
 * @author Graham
 */
public final class IdAssignmentEventEncoder extends EventEncoder<IdAssignmentEvent> {

    public IdAssignmentEventEncoder(Class<IdAssignmentEvent> clazz) {
        super(clazz);
    }

    @Override
    public GamePacket encode(IdAssignmentEvent event) {
        GamePacketBuilder builder = new GamePacketBuilder(249);
        builder.put(DataType.BYTE, DataTransformation.ADD, event.isMembers() ? 1 : 0);
        builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, event.getId());
        return builder.toGamePacket();
    }

}
