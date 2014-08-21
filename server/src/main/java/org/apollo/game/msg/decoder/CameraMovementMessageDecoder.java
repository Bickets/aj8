package org.apollo.game.msg.decoder;

import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.CameraMovementMessage;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * Decodes the camera movement message.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@DecodesMessage(86)
public final class CameraMovementMessageDecoder implements MessageDecoder<CameraMovementMessage> {

    @Override
    public CameraMovementMessage decode(GamePacket packet) {
	GamePacketReader reader = new GamePacketReader(packet);
	int roll = (int) reader.getUnsigned(DataType.SHORT);
	int pitch = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	return new CameraMovementMessage(roll, pitch);
    }

}