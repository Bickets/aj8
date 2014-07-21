package org.apollo.game.model;

import org.apollo.game.attribute.AttributeKey;

/**
 * The sole purpose of this class is to store, get and modify attributes for
 * some {@link GameCharacter}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GameCharacterFields {

    /**
     * The game character who owns these attributes.
     */
    private final GameCharacter character;

    /**
     * Constructs a new {@link GameCharacterFields} with the specified game
     * character.
     * 
     * @param character The character.
     */
    protected GameCharacterFields(GameCharacter character) {
	this.character = character;
    }

    /**
     * An attribute key representing a {@code boolean} which denotes whether or
     * not this character is withdrawing noted items from their bank. Default
     * value is {@code false}.
     */
    private static final AttributeKey<Boolean> WITHDRAWING_NOTES = AttributeKey.valueOf("withdrawing_notes", false);

    /**
     * Returns the current {@code boolean} flag of the withdrawing notes
     * attribute key.
     * 
     * @return {@code true} if the withdrawing notes attribute key is
     *         {@code true} otherwise {@code false}.
     */
    public boolean isWithdrawingNotes() {
	return character.getAttributes().get(WITHDRAWING_NOTES);
    }

    /**
     * Sets the withdrawing notes attribute key to the specified {@code flag}.
     * 
     * @param flag The new value for the withdrawing notes attribute key.
     */
    public void setWithdrawingNotes(boolean flag) {
	character.getAttributes().set(WITHDRAWING_NOTES, flag);
    }

}