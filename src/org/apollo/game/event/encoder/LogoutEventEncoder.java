package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link LogoutEvent}.
 * @author Graham
 */
public final class LogoutEventEncoder extends EventEncoder<LogoutEvent> {

    public LogoutEventEncoder(Class<LogoutEvent> clazz) {
        super(clazz);
    }

    @Override
    public GamePacket encode(LogoutEvent event) {
        GamePacketBuilder builder = new GamePacketBuilder(109);
        return builder.toGamePacket();
    }

}
