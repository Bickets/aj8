package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.KeepAliveMessage;
import org.apollo.net.codec.game.GamePacket;

/**
 * A {@link MessageDecoder} for the {@link KeepAliveMessage}.
 *
 * @author Graham
 */
@DecodesMessage(0)
public final class KeepAliveMessageDecoder implements MessageDecoder<KeepAliveMessage> {

    @Override
    public KeepAliveMessage decode(GamePacket packet) {
	return new KeepAliveMessage();
    }

}