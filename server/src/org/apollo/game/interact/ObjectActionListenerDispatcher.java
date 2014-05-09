package org.apollo.game.interact;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

public final class ObjectActionListenerDispatcher {

    private final Map<Integer, ObjectActionListener> handlers = new HashMap<>();

    public void bind(ObjectActionListener handler) {
	for (int id : handler.getIds()) {
	    handlers.put(id, handler);
	}
    }

    public void unbind(ObjectActionListener handler) {
	for (int id : handler.getIds()) {
	    handlers.remove(id);
	}
    }

    public void dispatch(int id, int option, Player player, Position position) {
	ObjectActionListener handler = handlers.get(id);
	if (handler != null) {
	    handler.handle(id, option, player, position);
	}
    }

}
