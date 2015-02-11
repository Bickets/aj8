package org.apollo.game.msg.impl;

import org.apollo.game.model.Position;
import org.apollo.game.model.inter.Interfaces.InteractContextMenuAction;
import org.apollo.game.msg.Message;

/**
 * A {@link Message} which represents some sort of action at an object.
 *
 * @author Graham
 */
public final class ObjectActionMessage extends ActionMessage {

	/**
	 * The object's id.
	 */
	private final int id;

	/**
	 * The object's position.
	 */
	private final Position position;

	/**
	 * Creates a new object action message.
	 *
	 * @param action The interface action clicked.
	 * @param id The id of the object.
	 * @param position The position of the object.
	 */
	public ObjectActionMessage(InteractContextMenuAction action, int id, Position position) {
		super(action);
		this.id = id;
		this.position = position;
	}

	/**
	 * Gets the id of the object.
	 *
	 * @return The id of the object.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the position of the object.
	 *
	 * @return The position of the object.
	 */
	public Position getPosition() {
		return position;
	}

}