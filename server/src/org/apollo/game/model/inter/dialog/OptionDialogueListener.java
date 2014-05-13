package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.OPTION_DIALOGUE_ID;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;

/**
 * A dialgoue listener which manages the {@link DialogueType#OPTION} dialogue
 * type.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class OptionDialogueListener implements DialogueListener {

    @Override
    public abstract boolean optionClicked(Player player, DialogueOption option);

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	int dialogueId = OPTION_DIALOGUE_ID[lines.length - 1];
	player.send(new SetInterfaceTextEvent(dialogueId - 1, getTitle()));
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(dialogueId + i, lines[i]));
	}
	return dialogueId - 2;
    }

    /**
     * Returns the title of this dialogue, by default <tt>Choose an option</tt>
     * is returned. This method may be overridden to provide for a user specific
     * functionality.
     */
    public String getTitle() {
	return "Choose an option";
    }

    @Override
    public final DialogueType type() {
	return DialogueType.OPTION;
    }

    /* Do not allow method overriding for these methods. */
    @Override public final DialogueExpression expression() { return null; }

}