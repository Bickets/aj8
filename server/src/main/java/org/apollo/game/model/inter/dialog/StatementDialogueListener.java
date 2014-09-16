package org.apollo.game.model.inter.dialog;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.IntStream.range;
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
		checkArgument(length < 0 || length >= STATEMENT_DIALOGUE_ID.length, "length : " + length + " is out of bounds.");
		range(0, length).forEach(i -> player.send(new SetInterfaceTextMessage(STATEMENT_DIALOGUE_ID[length - 1] + i + 1, lines[i])));
		return STATEMENT_DIALOGUE_ID[length - 1];
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