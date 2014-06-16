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
import org.apollo.game.task.TaskScheduler;
import org.apollo.io.player.PlayerSerializerWorker;
import org.apollo.net.session.GameSession;
import org.apollo.service.Service;
import org.apollo.util.NamedThreadFactory;

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
     * The scheduled executor service.
     */
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("GameService"));

    /**
     * A queue of players to remove.
     */
    private final Queue<Player> oldPlayers = new ConcurrentLinkedQueue<>();

    /**
     * The client synchronizer.
     */
    private final ClientSynchronizer clientSynchronizer;

    /**
     * The player serializer.
     */
    private final PlayerSerializerWorker playerSerializer;

    /**
     * The world.
     */
    private final World world;

    /**
     * Constructs a new {@link GameService}.
     * 
     * @param world The world.
     * @param playerSerializer The player serializer
     */
    public GameService(World world, PlayerSerializerWorker playerSerializer) {
	this.world = world;
	this.playerSerializer = playerSerializer;

	clientSynchronizer = new ClientSynchronizer(world);
    }

    @Override
    public void start() {
	scheduledExecutor.scheduleAtFixedRate(new GamePulseHandler(this), GameConstants.PULSE_DELAY, GameConstants.PULSE_DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * Called every pulse.
     */
    public void pulse() {
	synchronized (this) {
	    int unregistered = 0;
	    Player old;
	    while (unregistered < UNREGISTERS_PER_CYCLE && (old = oldPlayers.poll()) != null) {
		playerSerializer.submitSaveRequest(old.getSession(), old);
		unregistered++;
	    }

	    for (Player p : world.getPlayerRepository()) {
		GameSession session = p.getSession();
		if (session != null) {
		    session.handlePendingMessages();
		}
	    }

	    TaskScheduler.getInstance().pulse();

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

    public World getWorld() {
	return world;
    }

}
