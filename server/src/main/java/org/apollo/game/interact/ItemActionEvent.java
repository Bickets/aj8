package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;
import org.apollo.game.model.Interfaces.InterfaceOption;

public final class ItemActionEvent implements Event {

    private final Player player;
    private final int interfaceId;
    private final int id;
    private final int slot;
    private final InterfaceOption option;

    public ItemActionEvent(Player player, int interfaceId, int id, int slot, InterfaceOption option) {
	this.player = player;
	this.interfaceId = interfaceId;
	this.id = id;
	this.slot = slot;
	this.option = option;
    }

    public Player getPlayer() {
	return player;
    }

    public int getInterfaceId() {
	return interfaceId;
    }

    public int getId() {
	return id;
    }

    public int getSlot() {
	return slot;
    }

    public InterfaceOption getOption() {
	return option;
    }

}