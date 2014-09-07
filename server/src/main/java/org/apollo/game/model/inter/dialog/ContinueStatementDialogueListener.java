package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.CONTINUE_STATEMENT_DIALOGUE_ID;

import java.util.Objects;

import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialogue listener which manages the {@link DialogueType#CONTINUE_STATEMENT}
 * dialogue type.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class ContinueStatementDialogueListener implements DialogueListener {

	@Override
	public final int execute(Player player) {
		String[] lines = Objects.requireNonNull(lines());
		int length = lines.length;
		if (length < 0 || length > CONTINUE_STATEMENT_DIALOGUE_ID.length) {
			throw new DialogueException("line length: (%d) - out of bounds", length);
		}

		for (int i = 0; i < length; i++) {
			player.send(new SetInterfaceTextMessage(CONTINUE_STATEMENT_DIALOGUE_ID[length - 1][i + 1], lines[i]));
		}
		return CONTINUE_STATEMENT_DIALOGUE_ID[length - 1][0];
	}

	@Override
	public final DialogueType type() {
		return DialogueType.CONTINUE_STATEMENT;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final DialogueExpression expression() {
		return null;
	}

	@Override
	public final boolean optionClicked(DialogueOption option) {
		return false;
	}

}