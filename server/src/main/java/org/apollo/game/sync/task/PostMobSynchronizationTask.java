package org.apollo.game.sync.task;

import org.apollo.game.model.Mob;

/**
 * A {@link SynchronizationTask} which does post-synchronization work for the
 * specified {@link Mob}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class PostMobSynchronizationTask extends SynchronizationTask {

    /**
     * The mob to perform post synchronization for.
     */
    private final Mob mob;

    /**
     * Constructs a new {@link PostMobSynchronizationTask}.
     *
     * @param mob The mob.
     */
    public PostMobSynchronizationTask(Mob mob) {
	this.mob = mob;
    }

    @Override
    public void run() {
	if (mob.isTeleporting()) {
	    mob.setTeleporting(false);
	}

	if (mob.hasRegionChanged()) {
	    mob.setRegionChanged(false);
	}

	if (mob.getBlockSet().size() > 0) {
	    mob.resetBlockSet();
	}
    }

}