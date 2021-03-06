package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.STATEMENT_DIALOGUE_ID;

import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * An abstract implementation of a {@link DialogueListener} which manages
 * dialogue statements that are not continuable.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class StatementDialogueListener implements DialogueListener {

	@Override
	public final int send(Player player) {
		String[] lines = getLines();
		int length = lines.length;

		for (int index = 0; index < length; index++) {
			player.send(new SetInterfaceTextMessage(STATEMENT_DIALOGUE_ID[length - 1] + index + 1, lines[index]));
		}

		return STATEMENT_DIALOGUE_ID[length - 1];
	}

	@Override
	public final int getMaximumEntries() {
		return STATEMENT_DIALOGUE_ID.length;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final DialogueExpression expression() {
		return null;
	}

	@Override
	public final void optionClicked(DialogueOption option) {

	}

}