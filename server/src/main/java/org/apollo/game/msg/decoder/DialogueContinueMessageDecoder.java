package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.DialogueContinueMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link DialogueContinueMessage}.
 *
 * @author Chris Fletcher
 */
@DecodesMessage(40)
public final class DialogueContinueMessageDecoder extends MessageDecoder<DialogueContinueMessage> {

    @Override
    public DialogueContinueMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
	return new DialogueContinueMessage(interfaceId);
    }

}