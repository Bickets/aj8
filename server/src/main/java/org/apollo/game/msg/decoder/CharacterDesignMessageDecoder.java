package org.apollo.game.msg.decoder;

import org.apollo.game.model.appearance.Appearance;
import org.apollo.game.model.appearance.Gender;
import org.apollo.game.msg.MessageDecoder;
import org.apollo.game.msg.annotate.DecodesMessage;
import org.apollo.game.msg.impl.CharacterDesignMessage;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MessageDecoder} for the {@link CharacterDesignMessage}.
 *
 * @author Graham
 */
@DecodesMessage(101)
public final class CharacterDesignMessageDecoder implements MessageDecoder<CharacterDesignMessage> {

	@Override
	public CharacterDesignMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);

		int genderIntValue = (int) reader.getUnsigned(DataType.BYTE);

		int[] style = new int[7];
		for (int i = 0; i < style.length; i++) {
			style[i] = (int) reader.getUnsigned(DataType.BYTE);
		}

		int[] color = new int[5];
		for (int i = 0; i < color.length; i++) {
			color[i] = (int) reader.getUnsigned(DataType.BYTE);
		}

		Gender gender = genderIntValue == Gender.MALE.toInteger() ? Gender.MALE : Gender.FEMALE;

		return new CharacterDesignMessage(new Appearance(gender, style, color));
	}

}