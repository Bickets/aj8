package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.CommandMessage;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link CommandMessage}.
 *
 * @author Graham
 */
@DecodesMessage(103)
public final class CommandMessageDecoder implements MessageDecoder<CommandMessage> {

    @Override
    public CommandMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	return new CommandMessage(reader.getString());
    }

}