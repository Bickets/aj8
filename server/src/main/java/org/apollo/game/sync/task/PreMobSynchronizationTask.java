package org.apollo.game.sync.task;

import org.apollo.game.model.Mob;

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
	mob.getWalkingQueue().pulse();
    }

}