package org.apollo.game.interact;

import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

public abstract class ObjectActionListener {

    private final int[] ids;

    public ObjectActionListener(int... ids) {
	this.ids = ids;
    }

    public final int[] getIds() {
	return ids;
    }

    public abstract void handle(int id, InterfaceOption option, Player player, Position position);

}
