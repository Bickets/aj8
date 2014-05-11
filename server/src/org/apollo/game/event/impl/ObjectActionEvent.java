package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.InterfaceConstants.InterfaceOption;
import org.apollo.game.model.Position;

/**
 * An {@link Event} which represents some sort of action at an object.
 * 
 * @author Graham
 */
public class ObjectActionEvent extends Event {

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
     * Creates a new object action event.
     * 
     * @param option The interface option.
     * @param id The id of the object.
     * @param position The position of the object.
     */
    public ObjectActionEvent(InterfaceOption option, int id, Position position) {
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
