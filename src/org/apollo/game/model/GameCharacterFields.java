package org.apollo.game.model;

/**
 * This class is storage for miscellaneous variables related to {@link GameCharacters}.
 * <b>
 *      Please do not use this class to store character specific
 *      methods as that is not what this class is intended for.
 *      We don't want this to turn into the next client.java
 * </b>
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GameCharacterFields {

    /**
     * A flag indicating if the player is withdrawing items as notes.
     */
    private boolean withdrawingNotes = false;

    /**
     * Gets the withdrawing notes flag.
     * @return The flag.
     */
    public boolean isWithdrawingNotes() {
        return withdrawingNotes;
    }

    /**
     * Sets the withdrawing notes flag.
     * @param withdrawingNotes The flag.
     */
    public void setWithdrawingNotes(boolean withdrawingNotes) {
        this.withdrawingNotes = withdrawingNotes;
    }

}