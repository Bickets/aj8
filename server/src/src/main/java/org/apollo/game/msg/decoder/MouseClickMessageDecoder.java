package org.apollo.game.msg.decoder;

import java.awt.Point;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.MouseClickMessage;
import org.apollo.game.msg.impl.MouseClickMessage.ClickType;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Decodes the mouse click message.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(241)
public final class MouseClickMessageDecoder implements MessageDecoder<MouseClickMessage> {

	@Override
	public MouseClickMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);

		int clickHash = (int) reader.getUnsigned(DataType.INT);
		int time = clickHash >> 20;
		int type = clickHash >> 19 & 0x1;

		clickHash &= (1 << 19) - 1;

		int y = clickHash / 765;
		int x = clickHash - y * 765;

		return new MouseClickMessage(time, ClickType.forId(type), new Point(x, y));
	}

}