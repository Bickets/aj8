package org.apollo.game.interact;

import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

public final class InteractionHandler {

    private final ButtonClickListenerDispatcher buttonDispatcher = new ButtonClickListenerDispatcher();
    private final ObjectActionListenerDispatcher objectDispatcher = new ObjectActionListenerDispatcher();
    private final ItemActionListenerDispatcher itemDispatcher = new ItemActionListenerDispatcher();

    public void dispatch(int id, Player player) {
	buttonDispatcher.dispatch(id, player);
    }

    public void dispatch(int id, InterfaceOption option, Player player, Position position) {
	objectDispatcher.dispatch(id, option, player, position);
    }

    public void dispatch(int id, int slot, InterfaceOption option, int interfaceId, Player player) {
	itemDispatcher.dispatch(id, slot, option, interfaceId, player);
    }

    public void bind(ButtonClickListener handler) {
	buttonDispatcher.bind(handler);
    }

    public void bind(ObjectActionListener handler) {
	objectDispatcher.bind(handler);
    }

    public void bind(ItemActionListener handler) {
	itemDispatcher.bind(handler);
    }

}
