package org.apollo.game.sync.task;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Position;

/**
 * A {@link SynchronizationTask} which does pre-synchronization work for the
 * specified {@link Mob}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PreMobSynchronizationTask extends SynchronizationTask {

    /**
     * The mob.
     */
    private final Mob mob;

    /**
     * Constructs a new {@link PreMobSynchronizationTask}.
     *
     * @param mob The mob.
     */
    public PreMobSynchronizationTask(Mob mob) {
	this.mob = mob;
    }

    @Override
    public void run() {
	updateFace();
	mob.getWalkingQueue().pulse();
    }

    /**
     * Updates a mobs face direction to its original if required.
     */
    private void updateFace() {
	Position mobPos = mob.getPosition();
	Position facePos = new Position(mobPos.getX(), mobPos.getY() + mob.getInitialFaceDirection().toInteger());
	if (mobPos.equals(facePos)) {
	    return;
	}

	mob.turnTo(facePos);
    }

}