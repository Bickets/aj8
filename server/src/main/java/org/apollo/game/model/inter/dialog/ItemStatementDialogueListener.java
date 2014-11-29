package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MOB_DIALOGUE_ID;

import java.util.Objects;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.msg.impl.InterfaceItemModelMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialogue listener which manages the {@link DialogueType#ITEM_STATEMENT}
 * dialogue type.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class ItemStatementDialogueListener implements DialogueListener {

	/**
	 * The {@link Item} shown in the statement.
	 */
	private final Item item;

	/**
	 * Constructs a new {@link ItemStatementDialogueListener}.
	 *
	 * @param item The item to be shown in the statement.
	 * @throws NullPointerException If the specified item is null.
	 */
	public ItemStatementDialogueListener(Item item) {
		this.item = Objects.requireNonNull(item);
	}

	@Override
	public final int send(Player player) {
		String[] lines = getLines();
		int length = lines.length;
		int dialogueId = MOB_DIALOGUE_ID[length - 1];

		player.send(new InterfaceItemModelMessage(dialogueId - 2, item, getModelZoom()));
		player.send(new SetInterfaceTextMessage(dialogueId - 1, getTitle()));

		for (int index = 0; index < length; index++) {
			player.send(new SetInterfaceTextMessage(MOB_DIALOGUE_ID[length - 1] + index, lines[index]));
		}

		return dialogueId -= 3;
	}

	@Override
	public final int getMaximumEntries() {
		return MOB_DIALOGUE_ID.length;
	}

	/**
	 * Returns the title of this item statement. By default the items name is
	 * returned. This method may be overridden to provide a user specific
	 * functionality.
	 *
	 * @see {@link ItemDefinition#getName()}
	 */
	public String getTitle() {
		return item.getDefinition().getName();
	}

	/**
	 * Returns the zoom of the items model upon this statement. The default zoom
	 * is <tt>210</tt>. This method may be overridden to provide a user specific
	 * functionality.
	 */
	public int getModelZoom() {
		return 210;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final DialogueExpression expression() {
		return null;
	}

	@Override
	public final void optionClicked(DialogueOption option) {

	}

}