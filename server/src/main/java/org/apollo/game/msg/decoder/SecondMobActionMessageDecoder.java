package org.apollo.game.msg.decoder;

import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;
import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.MobActionMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Decodes the second {@link MobActionMessage}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(155)
public final class SecondMobActionMessageDecoder implements MessageDecoder<MobActionMessage> {

	@Override
	public MobActionMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int index = (int) reader.getUnsigned(DataType.SHORT);
		return new MobActionMessage(InteractContextMenuAction.ACTION_FIVE, index);
	}

}