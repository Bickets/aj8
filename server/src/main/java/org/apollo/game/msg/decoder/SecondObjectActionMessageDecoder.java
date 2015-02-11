package org.apollo.game.msg.decoder;

import org.apollo.game.model.Position;
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;
import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.ObjectActionMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link ObjectActionMessage}.
 *
 * @author Graham
 */
@DecodesMessage(252)
public final class SecondObjectActionMessageDecoder implements MessageDecoder<ObjectActionMessage> {

	@Override
	public ObjectActionMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int y = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int x = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		return new ObjectActionMessage(InteractContextMenuAction.ACTION_TWO, id, new Position(x, y));
	}

}