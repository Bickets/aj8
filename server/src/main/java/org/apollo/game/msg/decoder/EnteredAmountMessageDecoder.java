package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.EnteredAmountMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link EnteredAmountMessage}.
 *
 * @author Graham
 */
@DecodesMessage(208)
public final class EnteredAmountMessageDecoder implements MessageDecoder<EnteredAmountMessage> {

    @Override
    public EnteredAmountMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int amount = (int) reader.getUnsigned(DataType.INT);
	return new EnteredAmountMessage(amount);
    }

}