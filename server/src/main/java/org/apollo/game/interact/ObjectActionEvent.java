package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

public final class ObjectActionEvent implements Event {

    private final Player player;
    private final int id;
    private final InterfaceOption option;
    private final Position position;

    public ObjectActionEvent(Player player, int id, InterfaceOption option, Position position) {
	this.player = player;
	this.id = id;
	this.option = option;
	this.position = position;
    }

    public Player getPlayer() {
	return player;
    }

    public int getId() {
	return id;
    }

    public InterfaceOption getOption() {
	return option;
    }

    public Position getPosition() {
	return position;
    }

}
