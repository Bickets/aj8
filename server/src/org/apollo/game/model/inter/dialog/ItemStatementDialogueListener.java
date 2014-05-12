package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MOB_DIALOGUE_ID;

import org.apollo.game.event.impl.InterfaceItemModelEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

public abstract class ItemStatementDialogueListener implements DialogueListener {

    private final Item item;

    public ItemStatementDialogueListener(Item item) {
	this.item = item;
    }

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	int dialogueId = MOB_DIALOGUE_ID[lines.length - 1];
	int headChildId = dialogueId - 2;
	player.send(new InterfaceItemModelEvent(headChildId, getItem(), getModelZoom()));
	player.send(new SetInterfaceTextEvent(dialogueId - 1, getTitle()));
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(dialogueId + i, lines[i]));
	}
	return dialogueId -= 3;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.ITEM_STATEMENT;
    }

    @Override
    public int getModelZoom() {
	/* The default model zoom, may be overridden. */
	return 210;
    }

    @Override
    public final Item getItem() {
	return item;
    }

    /* Do not allow method overriding for these methods. */
    @Override
    public final DialogueExpression expression() {
	return null;
    }

    @Override
    public final boolean optionClicked(Player player, DialogueOption option) {
	return false;
    }

    @Override
    public final int getMobId() {
	return -1;
    }
}