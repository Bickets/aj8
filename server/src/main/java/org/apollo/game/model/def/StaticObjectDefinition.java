package org.apollo.game.model.def;

import java.util.Collection;

import org.apollo.game.model.Position;
import org.apollo.game.model.obj.ObjectOrientation;
import org.apollo.game.model.obj.ObjectType;

import com.google.common.collect.Multimap;

/**
 * Represents a static object in the game world.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class StaticObjectDefinition {

	/**
	 * Represents all of the definitions.
	 */
	private static Multimap<Integer, StaticObjectDefinition> definitions;

	/**
	 * The object's id.
	 */
	private final int id;

	/**
	 * The object's position.
	 */
	private final Position position;

	/**
	 * The object type.
	 */
	private final ObjectType type;

	/**
	 * The object's orientation.
	 */
	private final ObjectOrientation orientation;

	/**
	 * Creates a new static object.
	 *
	 * @param id The object's id.
	 * @param position The position.
	 * @param type The type code of the object.
	 * @param orientation The orientation of the object.
	 */
	public StaticObjectDefinition(int id, Position position, ObjectType type, ObjectOrientation orientation) {
		this.id = id;
		this.position = position;
		this.type = type;
		this.orientation = orientation;
	}

	/**
	 * Initializes the static object definitions multimap.
	 * 
	 * @param definitions The object definitions multimap.
	 */
	public static void init(Multimap<Integer, StaticObjectDefinition> definitions) {
		StaticObjectDefinition.definitions = definitions;
	}

	/**
	 * Returns all of the static objects on the map for the specified id.
	 * 
	 * @param id The id.
	 * @return The object definition.
	 */
	public static Collection<StaticObjectDefinition> forId(int id) {
		return definitions.get(id);
	}

	/**
	 * Gets the id of the object.
	 *
	 * @return The object id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the position of this object.
	 *
	 * @return The object's position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the object's orientation.
	 *
	 * @return The orientation.
	 */
	public ObjectOrientation getOrientation() {
		return orientation;
	}

	/**
	 * Gets the type code of the object.
	 *
	 * @return The type.
	 */
	public ObjectType getType() {
		return type;
	}

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + id + ", type=" + type + ", rotation=" + orientation + "]";
	}

}