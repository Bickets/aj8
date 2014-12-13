package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Interfaces.InterfaceOption;
import org.apollo.game.model.Mob;

/**
 * An event which is invoked when interacting with some {@link Mob}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MobActionEvent implements Event {

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
	 * @param mob The mob who is being interacted with.
	 * @param option The clicked interface option.
	 */
	public MobActionEvent(Mob mob, InterfaceOption option) {
		this.mob = mob;
		this.option = option;
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