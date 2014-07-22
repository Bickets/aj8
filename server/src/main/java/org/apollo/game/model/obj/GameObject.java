package org.apollo.game.model.obj;

import org.apollo.game.model.Entity;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.GameObjectDefinition;

/**
 * Represents a game object within the world.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Major, structure taken from his apollo
 */
public final class GameObject extends Entity {

    /**
     * A hash of all attributes for this game object.
     */
    private final int hashCode;

    @Override
    public EntityType type() {
	return EntityType.GAME_OBJECT;
    }

    /**
     * Constructs a new {@link GameObject} with the specified id and position.
     * This game object has a default orientation of north and a default type of
     * general prop.
     * 
     * @param id The id of this game object.
     * @param position The position of this game object.
     */
    public GameObject(int id, Position position) {
	this(id, position, ObjectOrientation.NORTH);
    }

    /**
     * Constructs a new {@link GameObject} with the specified id, position and
     * orientation. this game object has a default type of general prop.
     * 
     * @param id The id of this game object.
     * @param position The position of this game object.
     * @param orientation The orientation of this object.
     */
    public GameObject(int id, Position position, ObjectOrientation orientation) {
	this(id, position, ObjectType.GENERAL_PROP, orientation);
    }

    /**
     * Constructs a new {@link GameObject} with the specified id, position,
     * orientation and type.
     * 
     * @param id The id of this game object.
     * @param position The position of this game object.
     * @param type The type of this object.
     * @param orientation The orientation of this object.
     */
    public GameObject(int id, Position position, ObjectType type, ObjectOrientation orientation) {
	super(position);
	this.hashCode = (id << 8) + (type.getId() << 2) + orientation.getId();
    }

    /**
     * Decodes and returns the id of this object from its hash code.
     */
    public int getId() {
	return hashCode >> 8;
    }

    /**
     * Decodes and returns the type of this object from its hash code.
     */
    public ObjectType getType() {
	return ObjectType.forId((hashCode >> 2) & 0x3F);
    }

    /**
     * Decodes and returns the orientation of this object from its hash code.
     */
    public ObjectOrientation getOrientation() {
	return ObjectOrientation.forId(hashCode & 0x3F);
    }

    /**
     * Returns the definition of this object.
     */
    public GameObjectDefinition getDefinition() {
	return GameObjectDefinition.forId(getId());
    }

    @Override
    public int hashCode() {
	return hashCode;
    }

}