package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Mob;
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;

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
	 * The clicked interface action.
	 */
	private final InteractContextMenuAction action;

	/**
	 * Constructs a new {@link MobActionEvent}.
	 *
	 * @param mob The mob who is being interacted with.
	 * @param action The clicked interface action.
	 */
	public MobActionEvent(Mob mob, InteractContextMenuAction action) {
		this.mob = mob;
		this.action = action;
	}

	/**
	 * Returns the mob being interacted with.
	 */
	public Mob getMob() {
		return mob;
	}

	/**
	 * Returns the clicked interface action.
	 */
	public InteractContextMenuAction getAction() {
		return action;
	}

}