package org.apollo.game.msg.impl;

import org.apollo.game.model.Mob;
import org.apollo.game.msg.Message;

/**
 * Represents a message which sends a {@link Mob}'s model to a specified
 * interface.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobModelOnInterfaceMessage extends Message {

    /**
     * The id of the mob.
     */
    private final int mobId;

    /**
     * The interface id to send the mob's model to.
     */
    private final int interfaceId;

    /**
     * Constructs a new {@link MobModelOnInterfaceMessage}.
     *
     * @param mobId The mob's id.
     * @param interfaceId The interface id.
     */
    public MobModelOnInterfaceMessage(int mobId, int interfaceId) {
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
