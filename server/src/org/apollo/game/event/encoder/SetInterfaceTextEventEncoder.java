package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.annotate.EncodesEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;

/**
 * An {@link EventEncoder} for the {@link SetInterfaceTextEvent}.
 * 
 * @author The Wanderer
 */
@EncodesEvent(SetInterfaceTextEvent.class)
public final class SetInterfaceTextEventEncoder extends EventEncoder<SetInterfaceTextEvent> {

    @Override
    public GamePacket encode(SetInterfaceTextEvent event) {
	GamePacketBuilder builder = new GamePacketBuilder(126, PacketType.VARIABLE_SHORT);
	builder.putString(event.getText());
	builder.put(DataType.SHORT, DataTransformation.ADD, event.getInterfaceId());

	return builder.toGamePacket();
    }

}
