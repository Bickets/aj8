package org.apollo.game.model.inter.dialog;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.stream.IntStream.range;
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
		String[] lines = requireNonNull(lines());
		int length = lines.length;
		checkArgument(length >= 0 && length < MOB_DIALOGUE_ID.length, "length : " + length + " is out of bounds.");

		int dialogueId = MOB_DIALOGUE_ID[length - 1];
		int headChildId = dialogueId - 2;
		player.send(new MobModelOnInterfaceMessage(mobId, headChildId));
		player.send(new InterfaceModelAnimationMessage(expression().getAnimation(), headChildId));
		player.send(new SetInterfaceTextMessage(dialogueId - 1, MobDefinition.forId(mobId).getName()));

		range(0, length).forEach(i -> player.send(new SetInterfaceTextMessage(MOB_DIALOGUE_ID[length - 1] + i, lines[i])));

		return dialogueId -= 3;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final void optionClicked(DialogueOption option) {

	}

}