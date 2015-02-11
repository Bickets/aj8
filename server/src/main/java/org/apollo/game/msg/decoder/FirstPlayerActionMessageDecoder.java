package org.apollo.game.msg.decoder;

import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;
import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.PlayerActionMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Decodes the first {@link PlayerActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(128)
public final class FirstPlayerActionMessageDecoder implements MessageDecoder<PlayerActionMessage> {

	@Override
	public PlayerActionMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int index = (int) reader.getUnsigned(DataType.SHORT);
		return new PlayerActionMessage(InteractContextMenuAction.ACTION_ONE, index);
	}

}