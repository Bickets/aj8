package org.apollo.game.msg.decoder;

import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Position;
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
@DecodesMessage(70)
public final class ThirdObjectActionMessageDecoder extends MessageDecoder<ObjectActionMessage> {

    @Override
    public ObjectActionMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	int y = (int) reader.getUnsigned(DataType.SHORT);
	int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	return new ObjectActionMessage(InterfaceOption.OPTION_THREE, id, new Position(x, y));
    }

}
