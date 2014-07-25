package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.IdleMessage;
import org.apollo.net.codec.game.GamePacket;

/**
 * Decodes the {@link IdleMessage}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(202)
public final class IdleMessageDecoder implements MessageDecoder<IdleMessage> {

    @Override
    public IdleMessage decode(GamePacket packet) {
	return new IdleMessage();
    }

}