package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.PLAYER_DIALOGUE_ID;

import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.InterfaceModelAnimationMessage;
import org.apollo.game.msg.impl.PlayerModelOnInterfaceMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialogue listener which manages the {@link DialogueType#PLAYER_STATEMENT}
 * dialogue type.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class PlayerStatementDialogueListener implements DialogueListener {

	@Override
	public final int execute(Player player) {
		String[] lines = lines();
		int length = lines.length;
		if (length < 0 || length >= PLAYER_DIALOGUE_ID.length) {
			throw new DialogueException("line length: (%d) - out of bounds", length);
		}

		int dialogueId = PLAYER_DIALOGUE_ID[length - 1];
		int headChildId = dialogueId - 2;
		player.send(new PlayerModelOnInterfaceMessage(headChildId));
		player.send(new InterfaceModelAnimationMessage(expression().getAnimation(), headChildId));
		player.send(new SetInterfaceTextMessage(dialogueId - 1, player.getDisplayName()));
		for (int i = 0; i < length; i++) {
			player.send(new SetInterfaceTextMessage(dialogueId + i, lines[i]));
		}
		return dialogueId -= 3;
	}

	@Override
	public final DialogueType type() {
		return DialogueType.PLAYER_STATEMENT;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final void optionClicked(DialogueOption option) {

	}

}