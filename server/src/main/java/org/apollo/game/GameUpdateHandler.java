package org.apollo.game;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.msg.impl.SystemUpdateMessage;
import org.apollo.game.task.Task;

/**
 * Manages game update tasks.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GameUpdateHandler extends Task {

	/**
	 * The initial amount of seconds until the game update.
	 */
	private final int seconds;

	/**
	 * The world we are updating.
	 */
	private final World world;

	/**
	 * The remaining seconds until the game update.
	 */
	private int remainingSeconds;

	/**
	 * A flag to denote whether an update is running or not.
	 */
	private boolean active;

	/**
	 * Constructs a new {@link GameUpdateHandler} with the specified world and
	 * amount of seconds until the game update.
	 *
	 * @param world The world we are updating.
	 * @param seconds The amount of seconds until the game update.
	 */
	public GameUpdateHandler(World world, int seconds) {
		super(2, true);
		this.seconds = seconds * 50 / 30;
		this.world = world;

		remainingSeconds = seconds;
	}

	@Override
	public void execute() {
		if (active && remainingSeconds-- <= 0) {
			world.getMobRepository().forEach(world::unregister);
			world.getPlayerRepository().forEach(Player::logout);
			stop();
		}
	}

	/**
	 * Starts the game update.
	 */
	private void startUpdate() {
		active = true;
		world.submit(this);
		world.getPlayerRepository().forEach(plr -> plr.send(new SystemUpdateMessage(seconds)));
	}

	/**
	 * Schedules a pending game update, the server will perform update
	 * procedures after 60 minutes has passed.
	 */
	public void schedule() {
		if (active) {
			throw new IllegalArgumentException("Attempted to schedule update while an update is active!");
		}

		world.submit(new Task(100, true) {

			/**
			 * The count down until the update is scheduled, in minutes.
			 */
			private int countDown = 0;

			@Override
			public void execute() {
				switch (countDown) {
				case 60:
				case 40:
				case 30:
				case 20:
				case 10:
					world.sendGlobalMessage("The next Apollo update will start in approximately " + countDown + " minutes.");
					break;
				}

				if (countDown-- <= 0) {
					startUpdate();
					super.stop();
				}
			}
		});
	}

	/**
	 * Returns {@code true} if an update is currently active otherwise
	 * {@code false}.
	 */
	public boolean isActive() {
		return active;
	}

}