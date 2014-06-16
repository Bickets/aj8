package org.apollo.game.interact;

import org.apollo.game.model.Player;

public abstract class ButtonClickListener {

    private final int[] ids;

    public ButtonClickListener(int... ids) {
	this.ids = ids;
    }

    public final int[] getIds() {
	return ids;
    }

    public abstract void handle(int id, Player player);

}
