package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_DIALOGUE_ID;
import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_MODEL_ID;

import java.util.Arrays;
import java.util.Objects;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.InterfaceItemModelMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * An abstract implementation of a {@link DialogueListener} which manages
 * dialogue widgets that show 2 or more item models and ask how many you would
 * like to make.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class MakeItemOptionDialogueListener implements DialogueListener {

	/**
	 * An array of {@link Item}'s shown in this make item option dialogue.
	 */
	private final Item[] items;

	/**
	 * Constructs a new {@link MakeItemOptionDialogueListener}.
	 *
	 * @param items The items shown in this dialogue.
	 * @throws NullPointerException If the {@code items} are null.
	 */
	public MakeItemOptionDialogueListener(Item... items) {
		this.items = Objects.requireNonNull(items);
	}

	@Override
	public abstract void optionClicked(DialogueOption option);

	@Override
	public final int send(Player player) {
		String[] lines = getLines();

		for (int index = 0; index < lines.length; index++) {
			player.send(new InterfaceItemModelMessage(MAKE_ITEM_MODEL_ID[items.length - 2][index], items[index], getModelZoom()));
			player.send(new SetInterfaceTextMessage(MAKE_ITEM_DIALOGUE_ID[lines.length - 2][index + 1], lines[index]));
		}

		return MAKE_ITEM_DIALOGUE_ID[items.length - 2][0];
	}

	@Override
	public final int getMinimumEntries() {
		return 2;
	}

	@Override
	public final int getMaximumEntries() {
		return 6;
	}

	@Override
	public String[] getLines() {
		String[] lines = new String[items.length];
		Arrays.setAll(lines, index -> items[index].getDefinition().getName());
		return lines;
	}

	/**
	 * Returns the zoom of the items model upon this statement. The default zoom
	 * is <tt>150</tt>. This method may be overridden to provide a user specific
	 * functionality.
	 */
	public int getModelZoom() {
		return 150;
	}

	/* Do not allow method overriding for these methods. */
	@Override
	public final DialogueExpression expression() {
		return null;
	}

}