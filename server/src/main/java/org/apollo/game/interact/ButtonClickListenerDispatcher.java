package org.apollo.game.interact;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;

public final class ButtonClickListenerDispatcher {

    private final Map<Integer, ButtonClickListener> handlers = new HashMap<>();

    public ButtonClickListenerDispatcher() {

    }

    public void bind(ButtonClickListener handler) {
	for (int id : handler.getIds()) {
	    handlers.put(id, handler);
	}
    }

    public void unbind(ButtonClickListener handler) {
	for (int id : handler.getIds()) {
	    handlers.remove(id);
	}
    }

    public void dispatch(int id, Player player) {
	ButtonClickListener handler = handlers.get(id);
	if (handler != null) {
	    handler.handle(id, player);
	}
    }

}
