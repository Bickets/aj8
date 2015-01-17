package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.OPTION_DIALOGUE_ID;

import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * An abstract implementation of a {@link DialogueListener} which manages
 * dialogue widgets that show a series of selectable options.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class OptionDialogueListener implements DialogueListener {

	@Override
	public abstract void optionClicked(DialogueOption option);

	@Override
	public final int send(Player player) {
		String[] lines = getLines();

		int length = lines.length;
		int dialogueId = OPTION_DIALOGUE_ID[length - 1];

		player.send(new SetInterfaceTextMessage(dialogueId - 1, getTitle()));

		for (int index = 0; index < length; index++) {
			player.send(new SetInterfaceTextMessage(OPTION_DIALOGUE_ID[length - 1] + index, lines[index]));
		}

		return dialogueId - 2;
	}

	@Override
	public final int getMaximumEntries() {
		return OPTION_DIALOGUE_ID.length;
	}

	/**
	 * Returns the title of this dialogue, by default <tt>Choose an option</tt>
	 * is returned. This method may be overridden to provide for a user specific
	 * functionality.
	 */
	public String getTitle() {
		return "Choose an option";
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final DialogueExpression expression() {
		return null;
	}

}