package org.apollo.game.event.decoder;

import org.apollo.game.event.EventDecoder;
import org.apollo.game.event.annotate.DecodesEvent;
import org.apollo.game.event.impl.CharacterDesignEvent;
import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link EventDecoder} for the {@link CharacterDesignEvent}.
 * 
 * @author Graham
 */
@DecodesEvent(101)
public final class CharacterDesignEventDecoder extends EventDecoder<CharacterDesignEvent> {

    @Override
    public CharacterDesignEvent decode(GamePacket packet) {
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

	return new CharacterDesignEvent(new Appearance(gender, style, color));
    }

}
