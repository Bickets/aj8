package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.STATEMENT_DIALOGUE_ID;

import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialogue listener which manages the {@link DialogueType#STATEMENT} dialogue
 * type.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class StatementDialogueListener implements DialogueListener {

	@Override
	public final int execute(Player player) {
		String[] lines = lines();
		int length = lines.length;
		if (length < 0 || length >= STATEMENT_DIALOGUE_ID.length) {
			throw new DialogueException("line length: (%d) - out of bounds", length);
		}

		for (int i = 0; i < lines.length; i++) {
			player.send(new SetInterfaceTextMessage(STATEMENT_DIALOGUE_ID[length - 1] + i + 1, lines[i]));
		}
		return STATEMENT_DIALOGUE_ID[length - 1];
	}

	@Override
	public final DialogueType type() {
		return DialogueType.STATEMENT;
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