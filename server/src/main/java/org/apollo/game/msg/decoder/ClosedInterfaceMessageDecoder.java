package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ClosedInterfaceMessage;
import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MessageDecoder} for the {@link ClosedInterfaceMessage}.
 *
 * @author Graham
 */
@DecodesMessage(130)
public final class ClosedInterfaceMessageDecoder extends MessageDecoder<ClosedInterfaceMessage> {

    @Override
    public ClosedInterfaceMessage decode(GamePacket packet) {
	return new ClosedInterfaceMessage();
    }

}
