package org.apollo.game.interact;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.Interfaces.InterfaceOption;

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
	 * The interface option clicked.
	 */
	private final InterfaceOption option;

	/**
	 * The position of the object.
	 */
	private final Position position;

	/**
	 * Constructs a new {@link ObjectActionEvent} with the specified id, option
	 * and position.
	 *
	 * @param id The objects id.
	 * @param option The interface option clicked.
	 * @param position The position of the object.
	 */
	public ObjectActionEvent(int id, InterfaceOption option, Position position) {
		this.id = id;
		this.option = option;
		this.position = position;
	}

	/**
	 * Returns the objects id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the clicked interface option.
	 */
	public InterfaceOption getOption() {
		return option;
	}

	/**
	 * Returns the position of the object.
	 */
	public Position getPosition() {
		return position;
	}

}