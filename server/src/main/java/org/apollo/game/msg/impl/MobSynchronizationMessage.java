package org.apollo.game.msg.impl;

import java.util.List;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Position;
import org.apollo.game.msg.Message;
import org.apollo.game.sync.seg.SynchronizationSegment;

/**
 * A representation of a message used to synchronize mobs in the world.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public class MobSynchronizationMessage extends Message {

    /**
     * The mobs position.
     */
    private final Position position;

    /**
     * A list of the mobs synchronization segments.
     */
    private final List<SynchronizationSegment> segments;

    /**
     * An integer representing the amount of local mobs.
     */
    private final int localMobs;

    /**
     * Constructs a new {@link MobSynchronizationMessage}.
     *
     * @param position The position of the {@link Mob}.
     * @param segments The mobs list of synchronization segments.
     * @param localMobs The amount of local mobs.
     */
    public MobSynchronizationMessage(Position position, List<SynchronizationSegment> segments, int localMobs) {
	this.position = position;
	this.segments = segments;
	this.localMobs = localMobs;
    }

    /**
     * Returns the number of local mobs.
     *
     * @return The number of local mobs
     */
    public int getLocalMobs() {
	return localMobs;
    }

    /**
     * Returns the mobs position.
     *
     * @return The mobs position.
     */
    public Position getPosition() {
	return position;
    }

    /**
     * Returns the mobs list of synchronization segments.
     *
     * @return The mobs list of synchronization segments.
     */
    public List<SynchronizationSegment> getSegments() {
	return segments;
    }

}
