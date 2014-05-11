package org.apollo.game.interact;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Player;

public final class ItemActionListenerDispatcher {

    private final Map<Integer, ItemActionListener> handlers = new HashMap<>();

    public void bind(ItemActionListener handler) {
	handlers.put(handler.getInterfaceId(), handler);
    }

    public void unbind(ItemActionListener handler) {
	handlers.remove(handler.getInterfaceId());
    }

    public void dispatch(int id, int slot, InterfaceOption option, int interfaceId, Player player) {
	ItemActionListener handler = handlers.get(interfaceId);
	if (handler != null) {
	    handler.handle(id, slot, option, interfaceId, player);
	}
    }

}
