package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.impl.CommandEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link CommandEvent}.
 * @author Graham
 */
public final class CommandEventDecoder extends EventDecoder<CommandEvent> {

    public CommandEventDecoder(int opcode) {
        super(opcode);
    }

    @Override
    public CommandEvent decode(GamePacket packet) {
        GamePacketReader reader = new GamePacketReader(packet);
        return new CommandEvent(reader.getString());
    }

}
