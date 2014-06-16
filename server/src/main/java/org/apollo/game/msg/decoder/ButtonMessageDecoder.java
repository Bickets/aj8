package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ButtonMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link ButtonMessage}.
 * 
 * @author Graham
 */
@DecodesMessage(185)
public final class ButtonMessageDecoder extends MessageDecoder<ButtonMessage> {

    @Override
    public ButtonMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
	return new ButtonMessage(interfaceId);
    }

}
