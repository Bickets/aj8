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
     * An attribute key representing a {@code boolean} which denotes whether or
     * not this characters client is within focus. Default value is {@code true}
     */
    private static final AttributeKey<Boolean> CLIENT_WINDOW_FOCUSED = AttributeKey.valueOf("client_window_focused", true);

    /**
     * Returns whether or not this character is withdrawing notes.
     */
    public boolean isWithdrawingNotes() {
	return character.getAttributes().get(WITHDRAWING_NOTES);
    }

    /**
     * Returns whether or not the client window is in focus.
     */
    public boolean isClientWindowFocused() {
	return character.getAttributes().get(CLIENT_WINDOW_FOCUSED);
    }

    /**
     * Set the client windows focused state.
     */
    public void setClientWindowFocused(boolean flag) {
	character.getAttributes().set(CLIENT_WINDOW_FOCUSED, flag);
    }

    /**
     * Sets the withdrawing notes state.
     */
    public void setWithdrawingNotes(boolean flag) {
	character.getAttributes().set(WITHDRAWING_NOTES, flag);
    }

}