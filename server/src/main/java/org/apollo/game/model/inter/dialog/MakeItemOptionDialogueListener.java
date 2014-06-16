package org.apollo.game.model.inter.dialog;

import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_DIALOGUE_ID;
import static org.apollo.game.model.inter.dialog.DialogueConstants.MAKE_ITEM_MODEL_ID;

import java.util.Objects;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.msg.impl.InterfaceItemModelMessage;
import org.apollo.game.msg.impl.SetInterfaceTextMessage;

/**
 * A dialogue listener which manages the {@link DialogueType#MAKE_ITEM_OPTION}
 * dialogue type.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class MakeItemOptionDialogueListener implements DialogueListener {

    /**
     * An array of {@link Item}'s shown in this make item option dialogue.
     */
    private final Item[] items;

    /**
     * Constructs a new {@link MakeItemOptionDialogueListener}
     * 
     * @param items The items shown in this dialogue.
     * @throws NullPointerException If the {@code items} are null.
     */
    public MakeItemOptionDialogueListener(Item... items) {
	this.items = Objects.requireNonNull(items);
	if (items.length < 2 || items.length > 4) {
	    throw new DialogueException("length of items must be greater than 2 and less than 4, len: %d", items.length);
	}
    }

    @Override
    public abstract boolean optionClicked(DialogueOption option);

    @Override
    public final DialogueType type() {
	return DialogueType.MAKE_ITEM_OPTION;
    }

    @Override
    public final int execute(Player player) {
	String[] lines = lines();
	for (int i = 0; i < lines.length; i++) {
	    player.send(new InterfaceItemModelMessage(MAKE_ITEM_MODEL_ID[items.length - 2][i], items[i], getModelZoom()));
	    player.send(new SetInterfaceTextMessage(MAKE_ITEM_DIALOGUE_ID[lines.length - 2][i + 1], lines[i]));
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

    /**
     * Returns the zoom of the items model upon this statement. The default zoom
     * is <tt>210</tt>. This method may be overridden to provide a user specific
     * functionality.
     */
    public int getModelZoom() {
	return 210;
    }

    /* Do not allow method overriding for these methods. */
    @Override public final DialogueExpression expression() { return null; }

}