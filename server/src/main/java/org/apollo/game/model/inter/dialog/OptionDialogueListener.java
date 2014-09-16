package org.apollo.game.model.inter.dialog;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.stream.IntStream.range;
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
		String[] lines = requireNonNull(lines());
		int length = lines.length;
		checkArgument(length < 0 || length >= OPTION_DIALOGUE_ID.length, "length : " + length + " is out of bounds.");
		int dialogueId = OPTION_DIALOGUE_ID[length - 1];
		player.send(new SetInterfaceTextMessage(dialogueId - 1, getTitle()));
		range(0, length).forEach(i -> player.send(new SetInterfaceTextMessage(OPTION_DIALOGUE_ID[length - 1] + i, lines[i])));
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

	/* Do not allow method overriding for these methods. */
	@Override
	public final DialogueExpression expression() {
		return null;
	}

}