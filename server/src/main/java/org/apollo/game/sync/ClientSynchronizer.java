package org.apollo.game.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.apollo.game.GameService;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.sync.task.MobSynchronizationTask;
import org.apollo.game.sync.task.PhasedSynchronizationTask;
import org.apollo.game.sync.task.PlayerSynchronizationTask;
import org.apollo.game.sync.task.PostMobSynchronizationTask;
import org.apollo.game.sync.task.PostPlayerSynchronizationTask;
import org.apollo.game.sync.task.PreMobSynchronizationTask;
import org.apollo.game.sync.task.PrePlayerSynchronizationTask;
import org.apollo.game.sync.task.SynchronizationTask;
import org.apollo.util.EntityRepository;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * The {@link ClientSynchronizer} manages the update sequence which keeps
 * clients synchronized with the in-game world, we achieve this by running a
 * thread pool. A {@link Phaser} is used to ensure that the synchronization is
 * complete, allowing control to return to the {@link GameService} that started
 * the synchronization. This class will scale well with machines that have
 * multiple cores/processors. The {@link SequentialClientSynchronizer} will work
 * better on machines with a single core/processor, however, both classes will
 * work.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ClientSynchronizer {
    
    /**
     * The executor service.
     */
    private final ExecutorService executor;

    /**
     * The phaser, used as a concurrent lock when synchronizing the world.
     */
    private final Phaser phaser = new Phaser(1);

    /**
     * The world to synchronize.
     */
    private final World world;

    /**
     * Creates the parallel client synchronizer backed by a thread pool with a
     * number of threads equal to the number of processing cores available (this
     * is found by the {@link Runtime#availableProcessors()} method.
     *
     * @param world The world.
     */
    public ClientSynchronizer(World world) {
	this.world = world;
	int processors = Runtime.getRuntime().availableProcessors();
	executor = Executors.newFixedThreadPool(processors, new ThreadFactoryBuilder().setNameFormat("Client-Synchronizer").build());
    }

    /**
     * Synchronizes the specified {@code world}, this method is thread-safe.
     */
    public void synchronize() {
	EntityRepository<Player> players = world.getPlayerRepository();
	EntityRepository<Mob> mobs = world.getMobRepository();

	int playerCount = players.size();
	int mobCount = mobs.size();

	phaser.bulkRegister(playerCount);
	for (Player player : players) {
	    SynchronizationTask task = new PrePlayerSynchronizationTask(player);
	    executor.submit(new PhasedSynchronizationTask(phaser, task));
	}
	phaser.arriveAndAwaitAdvance();

	phaser.bulkRegister(mobCount);
	for (Mob mob : mobs) {
	    SynchronizationTask task = new PreMobSynchronizationTask(mob);
	    executor.submit(new PhasedSynchronizationTask(phaser, task));
	}
	phaser.arriveAndAwaitAdvance();

	phaser.bulkRegister(playerCount);
	for (Player player : players) {
	    SynchronizationTask task = new PlayerSynchronizationTask(player, world);
	    executor.submit(new PhasedSynchronizationTask(phaser, task));
	}
	phaser.arriveAndAwaitAdvance();

	phaser.bulkRegister(playerCount);
	for (Player player : players) {
	    SynchronizationTask task = new MobSynchronizationTask(player, world);
	    executor.submit(new PhasedSynchronizationTask(phaser, task));
	}
	phaser.arriveAndAwaitAdvance();

	phaser.bulkRegister(playerCount);
	for (Player player : players) {
	    SynchronizationTask task = new PostPlayerSynchronizationTask(player);
	    executor.submit(new PhasedSynchronizationTask(phaser, task));
	}
	phaser.arriveAndAwaitAdvance();

	phaser.bulkRegister(mobCount);
	for (Mob mob : mobs) {
	    SynchronizationTask task = new PostMobSynchronizationTask(mob);
	    executor.submit(new PhasedSynchronizationTask(phaser, task));
	}
	phaser.arriveAndAwaitAdvance();
    }

}
