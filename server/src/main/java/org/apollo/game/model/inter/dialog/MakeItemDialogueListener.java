package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_INTERFACE_ID;
import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_MODEL_INTERFACE_ID;
import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_TITLE_INTERFACE_ID;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.msg.impl.InterfaceItemModelMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialogue listener which manages the {@link DialogueType#MAKE_ITEM} dialogue
 * type.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class MakeItemDialogueListener implements DialogueListener {

	/**
	 * The {@link Item} shown in this make item statement.
	 */
	private final Item item;

	/**
	 * Constructs a new {@link MakeItemDialogueListener}.
	 *
	 * @param item The item shown in this make item statement.
	 */
	public MakeItemDialogueListener(Item item) {
		this.item = item;
	}

	@Override
	public abstract void optionClicked(DialogueOption option);

	@Override
	public final DialogueType type() {
		return DialogueType.MAKE_ITEM;
	}

	@Override
	public final int execute(Player player) {
		player.send(new InterfaceItemModelMessage(MAKE_ITEM_MODEL_INTERFACE_ID, item, getModelZoom()));
		player.send(new SetInterfaceTextMessage(MAKE_ITEM_TITLE_INTERFACE_ID, getTitle()));
		return MAKE_ITEM_INTERFACE_ID;
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
	public final String[] lines() {
		return null;
	}

}