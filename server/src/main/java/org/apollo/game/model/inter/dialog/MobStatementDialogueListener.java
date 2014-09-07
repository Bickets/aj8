package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MOB_DIALOGUE_ID;

import org.apollo.game.model.Player;
import org.apollo.game.model.def.MobDefinition;
import org.apollo.game.msg.impl.InterfaceModelAnimationMessage;
import org.apollo.game.msg.impl.MobModelOnInterfaceMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

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
		player.send(new MobModelOnInterfaceMessage(mobId, headChildId));
		player.send(new InterfaceModelAnimationMessage(expression().getAnimation(), headChildId));
		player.send(new SetInterfaceTextMessage(dialogueId - 1, MobDefinition.forId(mobId).getName()));
		for (int i = 0; i < lines.length; i++) {
			player.send(new SetInterfaceTextMessage(dialogueId + i, lines[i]));
		}
		return dialogueId -= 3;
	}

	@Override
	public final DialogueType type() {
		return DialogueType.MOB_STATEMENT;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final boolean optionClicked(DialogueOption option) {
		return false;
	}

}