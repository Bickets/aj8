package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * Represents an event which sends a {@link Mob}'s model to a specified
 * interface.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobModelOnInterfaceEvent extends Event {

    /**
     * The id of the mob.
     */
    private final int mobId;

    /**
     * The interface id to send the mob's model to.
     */
    private final int interfaceId;

    /**
     * Constructs a new {@link MobModelOnInterfaceEvent}.
     * 
     * @param mobId The mob's id.
     * @param interfaceId The interface id.
     */
    public MobModelOnInterfaceEvent(int mobId, int interfaceId) {
	this.mobId = mobId;
	this.interfaceId = interfaceId;
    }

    /**
     * Returns the mob's id.
     */
    public int getMobId() {
	return mobId;
    }

    /**
     * Returns the interface id.
     */
    public int getInterfaceId() {
	return interfaceId;
    }

}
