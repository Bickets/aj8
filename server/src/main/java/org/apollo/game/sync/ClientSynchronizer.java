package org.apollo.game.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import org.apollo.game.GameService;
import org.apollo.game.model.GameCharacterRepository;
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
import org.apollo.util.ThreadUtil;

/**
 * The {@link ClientSynchronizer} manages the update sequence which keeps
 * clients synchronized with the in-game world, we achieve this by running a
 * thread pool. A {@link Phaser} is used to ensure that the synchronization is
 * complete, allowing control to return to the {@link GameService} that started
 * the synchronization. This class will scale well with machines that have
 * multiple cores/processors.
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
	 * is found by {@link ThreadUtil#AVAILABLE_PROCESSORS}.
	 *
	 * @param world The world.
	 */
	public ClientSynchronizer(World world) {
		this.world = world;
		executor = Executors.newFixedThreadPool(ThreadUtil.AVAILABLE_PROCESSORS, ThreadUtil.build("Client-Synchronizer"));
	}

	/**
	 * Synchronizes the specified {@code world}, this method is thread-safe.
	 */
	public void synchronize() {
		GameCharacterRepository<Player> players = world.getPlayerRepository();
		GameCharacterRepository<Mob> mobs = world.getMobRepository();

		int playerCount = players.size();
		int mobCount = mobs.size();

		phaser.bulkRegister(playerCount);
		players.forEach(player -> executor.submit(new PhasedSynchronizationTask(phaser, new PrePlayerSynchronizationTask(player))));
		phaser.arriveAndAwaitAdvance();

		phaser.bulkRegister(mobCount);
		mobs.forEach(mob -> executor.submit(new PhasedSynchronizationTask(phaser, new PreMobSynchronizationTask(mob))));
		phaser.arriveAndAwaitAdvance();

		phaser.bulkRegister(playerCount);
		players.forEach(player -> executor.submit(new PhasedSynchronizationTask(phaser, new PlayerSynchronizationTask(player, world))));
		phaser.arriveAndAwaitAdvance();

		phaser.bulkRegister(playerCount);
		players.forEach(player -> executor.submit(new PhasedSynchronizationTask(phaser, new MobSynchronizationTask(player, world))));
		phaser.arriveAndAwaitAdvance();

		phaser.bulkRegister(playerCount);
		players.forEach(player -> executor.submit(new PhasedSynchronizationTask(phaser, new PostPlayerSynchronizationTask(player))));
		phaser.arriveAndAwaitAdvance();

		phaser.bulkRegister(mobCount);
		mobs.forEach(mob -> executor.submit(new PhasedSynchronizationTask(phaser, new PostMobSynchronizationTask(mob))));
		phaser.arriveAndAwaitAdvance();
	}

}