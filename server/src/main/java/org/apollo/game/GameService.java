package org.apollo.game;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.World.RegistrationStatus;
import org.apollo.game.sync.ClientSynchronizer;
import org.apollo.io.player.PlayerSerializerWorker;
import org.apollo.net.session.GameSession;
import org.apollo.service.Service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * The {@link GameService} class schedules and manages the execution of the
 * {@link GamePulseHandler} class.
 *
 * @author Graham
 */
public final class GameService implements Service {

	/**
	 * The number of times to unregister players per cycle. This is to ensure
	 * the saving threads don't get swamped with requests and slow everything
	 * down.
	 */
	private static final int UNREGISTERS_PER_CYCLE = 50;
	
	/**
	 * The delay between consecutive pulses, in milliseconds.
	 */
	public static final int PULSE_DELAY = 600;

	/**
	 * The scheduled executor service.
	 */
	private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameService").build());

	/**
	 * A queue of players to remove.
	 */
	private final Queue<Player> oldPlayers = new ConcurrentLinkedQueue<>();

	/**
	 * The {@link ClientSynchronizer} which manages the update sequence.
	 */
	private final ClientSynchronizer clientSynchronizer;

	/**
	 * The {@link PlayerSerializerWorker} for managing save and load requests.
	 */
	private final PlayerSerializerWorker serializerWorker;

	/**
	 * The {@link World} for managing world events.
	 */
	private final World world;

	/**
	 * Constructs a new {@link GameService}.
	 *
	 * @param world The world.
	 * @param serializerWorker The serializer worker.
	 */
	public GameService(World world, PlayerSerializerWorker serializerWorker) {
		this.world = world;
		this.serializerWorker = serializerWorker;

		clientSynchronizer = new ClientSynchronizer(world);
	}

	@Override
	public void start() {
		scheduledExecutor.scheduleAtFixedRate(new GamePulseHandler(this), PULSE_DELAY, PULSE_DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Called every pulse.
	 */
	public void pulse() {
		synchronized (this) {
			int unregistered = 0;
			Player old;
			while (unregistered < UNREGISTERS_PER_CYCLE && (old = oldPlayers.poll()) != null) {
				serializerWorker.submitSaveRequest(old.getSession(), old);
				unregistered++;
			}

			for (Player p : world.getPlayerRepository()) {
				GameSession session = p.getSession();
				if (session != null) {
					session.handlePendingMessages();
				}
			}

			world.pulse();

			clientSynchronizer.synchronize();
		}
	}

	/**
	 * Registers a player (may block!).
	 *
	 * @param player The player.
	 * @return A {@link RegistrationStatus}.
	 */
	public RegistrationStatus registerPlayer(Player player) {
		synchronized (this) {
			return world.register(player);
		}
	}

	/**
	 * Unregisters a player. Returns immediately. The player is unregistered at
	 * the start of the next cycle.
	 *
	 * @param player The player.
	 */
	public void unregisterPlayer(Player player) {
		oldPlayers.add(player);
	}

	/**
	 * Finalizes the unregistration of a player.
	 *
	 * @param player The player.
	 */
	public void finalizePlayerUnregistration(Player player) {
		synchronized (this) {
			world.unregister(player);
		}
	}

}