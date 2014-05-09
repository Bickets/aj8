package org.apollo.game.model.inter.dialog;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceListener;

/**
 * An {@link InterfaceListener} which listens for dialogue events.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public interface DialogueListener extends InterfaceListener {

    /**
     * Returns the type of this dialogue specified by some {@code enum} constant
     * within {@link DialogueType}
     */
    DialogueType type();

    /**
     * Returns the lines of dialogue this dialogue contains. TODO: Perhaps
     * create a specific object for containing dialogue information?
     */
    String[] lines();

    /**
     * Executes any prerequisite logic for this dialogue listener, returns the
     * id of this dialogue, for tracking.
     * 
     * @param player The player who owns this dialogue listener.
     * @return The id of this dialogue, for tracking.
     */
    int execute(Player player);

    @Override
    default void interfaceClosed() {
	/* Method intended to be overridden. */
    }

    /**
     * An event which is intended to be fired after the "Click here to continue"
     * object is clicked, by default this method has no action.
     */
    default void continued() {
	/* Method intended to be overridden. */
    }

    /**
     * Returns the title of this dialogue, this only applies to the types of
     * {@link DialogueType#ITEM_STATEMENT} and {@link DialogueType#OPTIONS}, by
     * default this method returns a value of <tt>"Choose an option"</tt>
     */
    default String getTitle() {
	/* Method intended to be overridden. */
	return "Choose an option";
    }

    /**
     * Returns the facial expression expressed by either a {@link Player} or
     * {@link Mob} from within a dialogue, this only applies to the types of
     * {@link DialogueType#MOB_STATEMENT} and
     * {@link DialogueType#PLAYER_STATEMENT}. The default expression is
     * {@link DialogueExpression#NO_EXPRESSION}
     */
    default DialogueExpression expression() {
	/* Method intended to be overridden. */
	return DialogueExpression.NO_EXPRESSION;
    }

    /**
     * Returns the mob id used for only the type of
     * {@link DialogueType#MOB_STATEMENT}. The mob id is used to display the
     * mobs model within the dialogue, the default mob id is <tt>-1</tt>
     */
    default int getMobId() {
	/* Method intended to be overridden. */
	return -1;
    }

    /**
     * Returns an {@link Item} object used to display their model within the
     * dialogue for only the type of {@link DialogueType#ITEM_STATEMENT}. The
     * default item is {@code null}.
     */
    default Item getItem() {
	/* Method intended to be overridden. */
	return null;
    }

    /**
     * Returns the zoom of the specified {@link getItem()} for the type of
     * {@link DialogueType#ITEM_STATEMENT}. The default model zoom is
     * <tt>-1</tt>
     */
    default int getModelZoom() {
	/* Method intended to be overridden. */
	return -1;
    }

    /**
     * Returns the next dialogue listener within the chain of dialogue
     * listeners, if there is no next listener then {@code null} is returned, by
     * default this method returns {@code null}.
     */
    default DialogueListener next() {
	/* Method intended to be overridden. */
	return null;
    }

    /**
     * An event which is intended to be fired after an option was clicked during
     * the dialogue type of {@link DialogueType#OPTIONS}
     * 
     * @param player The player clicking the option.
     * @param option The option, only five option dialogues are supported.
     * @return A flag to determine whether or not the event was successful,
     *         {@code true} is so otherwise {@code false}.
     */
    default boolean optionClicked(Player player, DialogueOption option) {
	/* Method intended to be overridden. */
	return false;
    }

}