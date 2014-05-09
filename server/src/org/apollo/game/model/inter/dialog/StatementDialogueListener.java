package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.STATEMENT_DIALOGUE_ID;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

public abstract class StatementDialogueListener implements DialogueListener {

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(STATEMENT_DIALOGUE_ID[lines.length - 1] + i + 1, lines[i]));
	}
	return STATEMENT_DIALOGUE_ID[lines.length - 1];
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

    @Override
    public final boolean optionClicked(Player player, DialogueOption option) {
	return false;
    }

    @Override
    public final String getTitle() {
	return null;
    }
}