package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.RegionLoadedMessage;
import org.apollo.net.codec.game.GamePacket;

/**
 * Decodes the {@link RegionLoadedMessage}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(121)
public final class RegionLoadedMessageDecoder implements MessageDecoder<RegionLoadedMessage> {

	@Override
	public RegionLoadedMessage decode(GamePacket packet) {
		return new RegionLoadedMessage();
	}

}