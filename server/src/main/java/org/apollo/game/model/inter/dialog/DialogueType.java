package org.apollo.game.model.inter.dialog;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;

/**
 * Represents the type of dialogue we are managing.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum DialogueType {
    /**
     * The option dialogue. This dialogue specifies options, from 2 to a maximum
     * of 5.
     */
    OPTION,

    /**
     * The statement dialogue. This dialogue declares a multi-line statement
     * that has no continue option.
     */
    STATEMENT,

    /**
     * The continue statement dialogue. This dialogue declares a multi-line
     * statement that has a continue option.
     */
    CONTINUE_STATEMENT,

    /**
     * The mob statement dialogue. This dialogue declares a multi-line statement
     * spoken by a specified {@link Mob}.
     */
    MOB_STATEMENT,

    /**
     * The item statement dialogue. This dialogue declares a multi-line
     * statement with an item in-place of the speaker.
     */
    ITEM_STATEMENT,

    /**
     * The destroy item statement dialogue. This dialogue declares a multi-line
     * statement which notifies a {@link Player} that the item they have tried
     * to drop or destroy is of high value or is not obtainable by regular
     * means. This dialogue is merely used as a fail-safe to confirm that the
     * player Performing the action does actually intend to do so.
     */
    DESTROY_ITEM_STATEMENT,

    /**
     * The player statement dialogue. This dialogue declares a multi-line
     * statement spoken by a specified {@link Player}.
     */
    PLAYER_STATEMENT,

    /**
     * The make item statement dialogue. This dialogue shows the model of a
     * specified item with the option to make it and how many.
     */
    MAKE_ITEM,

    /**
     * The make item option statement dialogue. This dialogue shows multiple (up
     * to 4) item models which you have a choice to make and how many.
     */
    MAKE_ITEM_OPTION

}