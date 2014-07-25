package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ClientFocusedMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Decodes the client focused message.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(3)
public final class ClientFocusedMessageDecoder implements MessageDecoder<ClientFocusedMessage> {

    @Override
    public ClientFocusedMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int value = (int) reader.getSigned(DataType.BYTE);
	return new ClientFocusedMessage(value == 1);
    }

}