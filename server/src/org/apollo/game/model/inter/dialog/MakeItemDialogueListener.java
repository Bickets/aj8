package org.apollo.game.model.inter.dialog;

import org.apollo.game.event.impl.InterfaceItemModelEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

public abstract class MakeItemDialogueListener implements DialogueListener {

    private final Item item;

    public MakeItemDialogueListener(Item item) {
	this.item = item;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.MAKE_ITEM;
    }

    @Override
    public final int execute(Player player) {
	player.send(new InterfaceItemModelEvent(1746, getItem(), getModelZoom()));
	/*
	 * TODO: If required add a function which delimits more lines prior to
	 * title.
	 */
	player.send(new SetInterfaceTextEvent(2799, "\\n\\n\\n\\n" + getTitle()));
	return 4429;
    }

    @Override
    public final Item getItem() {
	return item;
    }

    @Override
    public String getTitle() {
	/* Default title, may be overridden. */
	return item.getDefinition().getName();
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
    public final String[] lines() {
	return null;
    }
}