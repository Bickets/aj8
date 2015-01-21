package org.apollo.game;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apollo.game.model.Player;
import org.apollo.game.model.World.RegistrationStatus;
import org.apollo.net.session.GameSession;
import org.apollo.service.Service;
import org.apollo.util.ThreadUtil;

/**
 * The {@link GameService} class schedules and manages the execution of the
 * {@link GamePulseHandler} class.
 *
 * @author Graham
 */
public final class GameService extends Service {

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
	private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(ThreadUtil.build("GameService"));

	/**
	 * A queue of players to remove.
	 */
	private final Queue<Player> oldPlayers = new ConcurrentLinkedQueue<>();

	@Override
	public void init() {
		scheduledExecutor.scheduleAtFixedRate(new GamePulseHandler(this), PULSE_DELAY, PULSE_DELAY, TimeUnit.MILLISECONDS);
	}

	/**
	 * Called every pulse.
	 */
	public void pulse() {
		synchronized (this) {
			for (int unregistered = 0; unregistered <= UNREGISTERS_PER_CYCLE; unregistered++) {
				Player player = oldPlayers.poll();
				if (player == null) {
					break;
				}
				getSerializerWorker().submitSaveRequest(player.getSession(), player);
			}

			for (Player p : getWorld().getPlayerRepository()) {
				GameSession session = p.getSession();
				if (session != null) {
					session.handlePendingMessages();
				}
			}

			getWorld().pulse();
			getClientSynchronizer().synchronize();
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
			return getWorld().register(player);
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
	 * Finalizes the un-registration of a player.
	 *
	 * @param player The player.
	 */
	public void finalizePlayerUnregistration(Player player) {
		synchronized (this) {
			getWorld().unregister(player);
		}
	}

}