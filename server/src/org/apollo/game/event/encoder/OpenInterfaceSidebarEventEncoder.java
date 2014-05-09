package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.OpenInterfaceSidebarEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link OpenInterfaceSidebarEvent}.
 * 
 * @author Graham
 */
@EncodesEvent(OpenInterfaceSidebarEvent.class)
public final class OpenInterfaceSidebarEventEncoder extends EventEncoder<OpenInterfaceSidebarEvent> {

    @Override
    public GamePacket encode(OpenInterfaceSidebarEvent event) {
	GamePacketBuilder builder = new GamePacketBuilder(248);
	builder.put(DataType.SHORT, DataTransformation.ADD, event.getInterfaceId());
	builder.put(DataType.SHORT, event.getSidebarId());
	return builder.toGamePacket();
    }

}
