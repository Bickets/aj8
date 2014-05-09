package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.OPTION_DIALOGUE_ID;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

public abstract class OptionDialogueListener implements DialogueListener {

    @Override
    public abstract boolean optionClicked(Player player, DialogueOption option);

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	int dialogueId = OPTION_DIALOGUE_ID[lines.length - 1];
	player.send(new SetInterfaceTextEvent(dialogueId - 1, getTitle()));
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(dialogueId + i, lines[i]));
	}
	return dialogueId - 2;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.OPTIONS;
    }

    /* Do not allow method overriding for these methods. */
    @Override
    public final DialogueExpression expression() {
	return null;
    }

    @Override
    public final int getMobId() {
	return -1;
    }

    @Override
    public final Item getItem() {
	return null;
    }

    @Override
    public final int getModelZoom() {
	return -1;
    }
}