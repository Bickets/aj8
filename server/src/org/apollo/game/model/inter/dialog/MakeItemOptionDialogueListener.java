package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_DIALOGUE_ID;
import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_MODEL_ID;

import org.apollo.game.event.impl.InterfaceItemModelEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

public final class MakeItemOptionDialogueListener implements DialogueListener {

    private final Item[] items;

    public MakeItemOptionDialogueListener(Item... items) {
	this.items = items;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.MAKE_ITEM_OPTION;
    }

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	for (int i = 0; i < lines.length; i++) {
	    player.send(new InterfaceItemModelEvent(MAKE_ITEM_MODEL_ID[items.length - 2][i], items[i], getModelZoom()));
	    player.send(new SetInterfaceTextEvent(MAKE_ITEM_DIALOGUE_ID[lines.length - 2][i + 1], lines[i]));
	}
	return MAKE_ITEM_DIALOGUE_ID[items.length - 2][0];
    }

    @Override
    public String[] lines() {
	String[] lines = new String[items.length];
	for (int i = 0; i < lines.length; i++) {
	    lines[i] = items[i].getDefinition().getName();
	}
	return lines;
    }

    @Override
    public int getModelZoom() {
	/* Default model zoom, may be overridden. */
	return 210;
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

    @Override
    public final Item getItem() {
	return null;
    }

    @Override
    public final String getTitle() {
	return null;
    }
}
