package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;

public final class ButtonActionEvent implements Event {

    private final Player player;
    private final int id;

    public ButtonActionEvent(Player player, int id) {
	this.player = player;
	this.id = id;
    }

    public Player getPlayer() {
	return player;
    }

    public int getId() {
	return id;
    }

}