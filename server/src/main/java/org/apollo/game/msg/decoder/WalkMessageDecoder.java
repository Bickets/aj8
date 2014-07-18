package org.apollo.game.msg.decoder;

import org.apollo.game.model.Position;
import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.WalkMessage;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link WalkMessage}.
 *
 * @author Graham
 */
@DecodesMessage({ 98, 164, 248 })
public final class WalkMessageDecoder implements MessageDecoder<WalkMessage> {

    @Override
    public WalkMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);

	int length = packet.getLength();

	/*
	 * The force walk packet has an extra unused 14 bytes, let's get rid of
	 * them.
	 */
	if (packet.getOpcode() == 248) {
	    length -= 14;
	}

	int steps = (length - 5) / 2;
	int[][] path = new int[steps][2];

	int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	for (int i = 0; i < steps; i++) {
	    path[i][0] = (int) reader.getSigned(DataType.BYTE);
	    path[i][1] = (int) reader.getSigned(DataType.BYTE);
	}
	int y = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	boolean run = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE) == 1;

	Position[] positions = new Position[steps + 1];
	positions[0] = new Position(x, y);
	for (int step = 0; step < steps; step++) {
	    positions[step + 1] = new Position(path[step][0] + x, path[step][1] + y);
	}

	return new WalkMessage(positions, run);
    }

}
