package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.OPTION_DIALOGUE_ID;

import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialgoue listener which manages the {@link DialogueType#OPTION} dialogue
 * type.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class OptionDialogueListener implements DialogueListener {

	@Override
	public abstract void optionClicked(DialogueOption option);

	@Override
	public final int execute(Player player) {
		String[] lines = lines();
		int length = lines.length;
		if (length < 0 || length >= OPTION_DIALOGUE_ID.length) {
			throw new DialogueException("line length: (%d) - out of bounds", length);
		}

		int dialogueId = OPTION_DIALOGUE_ID[length - 1];
		player.send(new SetInterfaceTextMessage(dialogueId - 1, getTitle()));
		for (int i = 0; i < length; i++) {
			player.send(new SetInterfaceTextMessage(dialogueId + i, lines[i]));
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
	@Override
	public final DialogueExpression expression() {
		return null;
	}

}