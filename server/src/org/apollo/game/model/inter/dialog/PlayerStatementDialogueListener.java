package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.PLAYER_DIALOGUE_ID;

import org.apollo.game.event.impl.InterfaceModelAnimationEvent;
import org.apollo.game.event.impl.PlayerModelOnInterfaceEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;

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
	int dialogueId = PLAYER_DIALOGUE_ID[lines.length - 1];
	int headChildId = dialogueId - 2;
	player.send(new PlayerModelOnInterfaceEvent(headChildId));
	player.send(new InterfaceModelAnimationEvent(expression().getAnimation(), headChildId));
	player.send(new SetInterfaceTextEvent(dialogueId - 1, player.getName()));
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(dialogueId + i, lines[i]));
	}
	return dialogueId -= 3;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.PLAYER_STATEMENT;
    }

    /* Do not allow method overriding for these methods. */
    @Override public final boolean optionClicked(Player player, DialogueOption option) { return false; }

}