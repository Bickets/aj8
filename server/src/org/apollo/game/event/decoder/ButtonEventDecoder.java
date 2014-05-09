package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.ButtonEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link ButtonEvent}.
 * 
 * @author Graham
 */
@DecodesEvent(185)
public final class ButtonEventDecoder extends EventDecoder<ButtonEvent> {

    @Override
    public ButtonEvent decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
	return new ButtonEvent(interfaceId);
    }

}
