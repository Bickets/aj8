package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.CONTINUE_STATEMENT_DIALOGUE_ID;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;

/**
 * A dialogue listener which manages the {@link DialogueType#CONTINUE_STATEMENT} dialogue type.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class ContinueStatementDialogueListener implements DialogueListener {

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(CONTINUE_STATEMENT_DIALOGUE_ID[lines.length - 1][i + 1], lines[i]));
	}
	return CONTINUE_STATEMENT_DIALOGUE_ID[lines.length - 1][0];
    }

    @Override
    public final DialogueType type() {
	return DialogueType.CONTINUE_STATEMENT;
    }

    /* Do not allow method overriding for these methods. */
    @Override public final DialogueExpression expression() { return null; }
    @Override public final boolean optionClicked(Player player, DialogueOption option) { return false; }

}