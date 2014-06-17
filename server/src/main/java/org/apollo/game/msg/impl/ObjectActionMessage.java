package org.apollo.game.msg.impl;

import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Position;
import org.apollo.game.msg.Message;

/**
 * A {@link Message} which represents some sort of action at an object.
 *
 * @author Graham
 */
public class ObjectActionMessage extends Message {

    /**
     * The interface option.
     */
    private final InterfaceOption option;

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
     * @param option The interface option.
     * @param id The id of the object.
     * @param position The position of the object.
     */
    public ObjectActionMessage(InterfaceOption option, int id, Position position) {
	this.option = option;
	this.id = id;
	this.position = position;
    }

    /**
     * Gets the interface option
     *
     * @return The interface option.
     */
    public InterfaceOption getOption() {
	return option;
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
