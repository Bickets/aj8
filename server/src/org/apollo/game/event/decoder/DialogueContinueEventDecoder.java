package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.DialogueContinueEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link DialogueContinueEvent}.
 * 
 * @author Chris Fletcher
 */
@DecodesEvent(40)
public final class DialogueContinueEventDecoder extends EventDecoder<DialogueContinueEvent> {

    @Override
    public DialogueContinueEvent decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
	return new DialogueContinueEvent(interfaceId);
    }

}