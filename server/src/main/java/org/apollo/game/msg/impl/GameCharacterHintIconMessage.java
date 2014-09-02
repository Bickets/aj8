package org.apollo.game.msg.impl;

import org.apollo.game.model.GameCharacter;

/**
 * A hint icon displayed over a player or NPC.
 *
 * @author James Monger
 */
public final class GameCharacterHintIconMessage extends HintIconMessage {

    /**
     * The character.
     */
    private final GameCharacter character;

    /**
     * Creates the {@link GameCharacterHintIconMessage}.
     *
     * @param character The character.
     */
    public GameCharacterHintIconMessage(GameCharacter character) {
	super(getType(character));
	this.character = character;
    }

    /**
     * Returns a {@link HintIconType} for the specified {@link GameCharacter}.
     *
     * @param character The game character.
     * @return The {@code type} for the specified character.
     * @throws UnsupportedOperationException If the specified character is not a
     *             supported type.
     */
    private static HintIconType getType(GameCharacter character) {
	switch (character.type()) {
	case MOB:
	    return HintIconType.MOB;
	case PLAYER:
	    return HintIconType.PLAYER;
	default:
	    throw new UnsupportedOperationException("Unsupported game character: " + character);
	}
    }

    /**
     * Gets the character.
     *
     * @return The character.
     */
    public GameCharacter getCharacter() {
	return character;
    }

}