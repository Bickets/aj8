package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Interfaces.InterfaceOption;
import org.apollo.game.model.Mob;
import org.apollo.game.model.Player;

/**
 * An event which is invoked when interacting with some {@link Mob}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobActionEvent implements Event {

	/**
	 * The player interacting with the mob.
	 */
	private final Player player;

	/**
	 * The mob being interacted with.
	 */
	private final Mob mob;

	/**
	 * The clicked interface option.
	 */
	private final InterfaceOption option;

	/**
	 * Constructs a new {@link MobActionEvent}.
	 *
	 * @param player The player interacting with the mob.
	 * @param mob The mob who is being interacted with.
	 * @param option The clicked interface option.
	 */
	public MobActionEvent(Player player, Mob mob, InterfaceOption option) {
		this.player = player;
		this.mob = mob;
		this.option = option;
	}

	/**
	 * Returns the player interacting with the mob.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the mob being interacted with.
	 */
	public Mob getMob() {
		return mob;
	}

	/**
	 * Returns the clicked interface option.
	 */
	public InterfaceOption getOption() {
		return option;
	}

}