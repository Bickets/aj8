package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MOB_DIALOGUE_ID;

import org.apollo.game.event.impl.InterfaceModelAnimationEvent;
import org.apollo.game.event.impl.MobModelOnInterfaceEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.MobDefinition;

public abstract class MobStatementDialogueListener implements DialogueListener {

    private final int mobId;

    public MobStatementDialogueListener(int mobId) {
	this.mobId = mobId;
    }

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	int dialogueId = MOB_DIALOGUE_ID[lines.length - 1];
	int headChildId = dialogueId - 2;
	player.send(new MobModelOnInterfaceEvent(getMobId(), headChildId));
	player.send(new InterfaceModelAnimationEvent(expression().getAnimation(), headChildId));
	player.send(new SetInterfaceTextEvent(dialogueId - 1, MobDefinition.forId(getMobId()).getName()));
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(dialogueId + i, lines[i]));
	}
	return dialogueId -= 3;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.MOB_STATEMENT;
    }

    @Override
    public final int getMobId() {
	return mobId;
    }

    /* Do not allow method overriding for these methods. */
    @Override
    public final Item getItem() {
	return null;
    }

    @Override
    public final int getModelZoom() {
	return -1;
    }

    @Override
    public final String getTitle() {
	return null;
    }

    @Override
    public final boolean optionClicked(Player player, DialogueOption option) {
	return false;
    }
}