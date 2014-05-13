package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MOB_DIALOGUE_ID;

import org.apollo.game.event.impl.InterfaceModelAnimationEvent;
import org.apollo.game.event.impl.MobModelOnInterfaceEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.MobDefinition;

/**
 * A dialogue listener which manages the {@link DialogueType#MOB_STATEMENT}
 * dialogue.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class MobStatementDialogueListener implements DialogueListener {

    /**
     * The identifier of the mob speaking the dialogue.
     */
    private final int mobId;

    /**
     * Constructs a new {@link MobStatementDialogueListener}.
     * 
     * @param mobId The mobs identifier.
     */
    public MobStatementDialogueListener(int mobId) {
	this.mobId = mobId;
    }

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	int dialogueId = MOB_DIALOGUE_ID[lines.length - 1];
	int headChildId = dialogueId - 2;
	player.send(new MobModelOnInterfaceEvent(mobId, headChildId));
	player.send(new InterfaceModelAnimationEvent(expression().getAnimation(), headChildId));
	player.send(new SetInterfaceTextEvent(dialogueId - 1, MobDefinition.forId(mobId).getName()));
	for (int i = 0; i < lines.length; i++) {
	    player.send(new SetInterfaceTextEvent(dialogueId + i, lines[i]));
	}
	return dialogueId -= 3;
    }

    @Override
    public final DialogueType type() {
	return DialogueType.MOB_STATEMENT;
    }

    /* Do not allow method overriding for these methods. */
    @Override  public final boolean optionClicked(Player player, DialogueOption option) { return false; }

}