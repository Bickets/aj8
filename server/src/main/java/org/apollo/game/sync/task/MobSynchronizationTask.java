package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.impl.MobSynchronizationMessage;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.seg.AddCharacterSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.RemoveCharacterSegment;
import org.apollo.game.sync.seg.SynchronizationSegment;
import org.apollo.util.EntityRepository;

/**
 * A {@link SynchronizationTask} which synchronizes the specified {@link Mob}
 * for a {@link Player}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobSynchronizationTask extends SynchronizationTask {

    /**
     * The maximum number of mobs to load per cycle. This prevents the update
     * packet from becoming too large (the client uses a 5000 byte buffer) and
     * also stops old spec PCs from crashing when they login or teleport.
     */
    private static final int NEW_MOBS_PER_CYCLE = 20;

    /**
     * The player.
     */
    private final Player player;

    /**
     * The world.
     */
    private final World world;

    /**
     * Constructs a new {@link MobSynchronizationTask}.
     *
     * @param player The player.
     * @param world The world.
     */
    public MobSynchronizationTask(Player player, World world) {
	this.player = player;
	this.world = world;
    }

    @Override
    public void run() {
	SynchronizationBlockSet blockSet = player.getBlockSet();
	Set<Mob> localMobs = player.getLocalMobs();
	int oldLocalMobs = localMobs.size();
	List<SynchronizationSegment> segments = new ArrayList<>();

	Iterator<Mob> it = localMobs.iterator();
	while (it.hasNext()) {
	    Mob mob = it.next();
	    if (!mob.isActive() || mob.isTeleporting() || mob.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()) {
		it.remove();
		segments.add(new RemoveCharacterSegment());
	    } else {
		segments.add(new MovementSegment(mob.getBlockSet(), mob.getDirections()));
	    }
	}

	int added = 0;

	EntityRepository<Mob> repository = world.getMobRepository();
	// lambda does not work here
	// due to variables needing to be final.
	for (Mob mob : repository) {
	    if (localMobs.size() >= 255) {
		player.flagExcessiveMobs();
		break;
	    } else if (added >= NEW_MOBS_PER_CYCLE) {
		break;
	    }

	    if (mob.getPosition().isWithinDistance(player.getPosition(), player.getViewingDistance()) && !localMobs.contains(mob)) {
		localMobs.add(mob);
		added++;
		blockSet = mob.getBlockSet();
		segments.add(new AddCharacterSegment(blockSet, mob, mob.getIndex(), mob.getDefinition().getId(), mob.getPosition()));
	    }
	}
	player.send(new MobSynchronizationMessage(player.getPosition(), segments, oldLocalMobs));
    }

}
