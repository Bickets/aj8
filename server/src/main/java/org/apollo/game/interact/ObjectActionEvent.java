package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;

/**
 * An event which manages object actions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class ObjectActionEvent implements Event {

	/**
	 * The id of the object.
	 */
	private final int id;

	/**
	 * The interface action clicked.
	 */
	private final InteractContextMenuAction action;

	/**
	 * The position of the object.
	 */
	private final Position position;

	/**
	 * Constructs a new {@link ObjectActionEvent} with the specified id, option
	 * and position.
	 *
	 * @param id The objects id.
	 * @param action The interface action clicked.
	 * @param position The position of the object.
	 */
	public ObjectActionEvent(int id, InteractContextMenuAction action, Position position) {
		this.id = id;
		this.action = action;
		this.position = position;
	}

	/**
	 * Returns the objects id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the clicked interface action.
	 */
	public InteractContextMenuAction getAction() {
		return action;
	}

	/**
	 * Returns the position of the object.
	 */
	public Position getPosition() {
		return position;
	}

}