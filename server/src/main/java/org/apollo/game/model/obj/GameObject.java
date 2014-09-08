package org.apollo.game.model.obj;

import static org.apollo.game.model.obj.ObjectOrientation.NORTH;
import static org.apollo.game.model.obj.ObjectOrientation.SOUTH;

import org.apollo.game.model.Entity;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.GameObjectDefinition;

/**
 * Represents a game object within the world.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GameObject extends Entity {

	/**
	 * Represents the id of this object.
	 */
	private final int id;

	/**
	 * Represents the type of this object.
	 */
	private final ObjectType type;

	/**
	 * Represents the orientation of this object.
	 */
	private final ObjectOrientation orientation;

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
		this.id = id;
		this.type = type;
		this.orientation = orientation;
	}

	/**
	 * Decodes and returns the id of this object.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the type of this object.
	 */
	public ObjectType getType() {
		return type;
	}

	/**
	 * Returns the orientation of this object.
	 */
	public ObjectOrientation getOrientation() {
		return orientation;
	}

	/**
	 * Returns the definition of this object.
	 */
	public GameObjectDefinition getDefinition() {
		return GameObjectDefinition.forId(id);
	}

	/**
	 * Returns the amount of between this game objects position and the
	 * specified position.
	 *
	 * @param position The starting position.
	 * @return The amount of between this game objects position and the
	 *         specified position.
	 */
	public int getTileOffset(Position position) {
		if (size() <= 1) {
			return 1;
		}

		GameObjectDefinition def = getDefinition();
		int distanceX = Math.abs(position.getX() - getPosition().getX());
		int distanceY = Math.abs(position.getY() - getPosition().getY());
		int total = distanceX > distanceY ? def.getSizeX() : def.getSizeY();
		return total;
	}

	/**
	 * Calculates the center position of this object.
	 *
	 * @return The center position of this object.
	 */
	public Position getCenterPosition() {
		GameObjectDefinition def = getDefinition();
		int width = def.getSizeX();
		int length = def.getSizeY();
		if (orientation == NORTH || orientation == SOUTH) {
			width = def.getSizeY();
			length = def.getSizeX();
		}
		int centerX = getPosition().getX() + width / 2;
		int centerY = getPosition().getY() + length / 2;
		return new Position(centerX, centerY);
	}

	/**
	 * Calculates the turn to position from the specified position for this game
	 * object.
	 *
	 * @param from The from position.
	 * @return The position to turn to.
	 */
	public Position getTurnToPosition(Position from) {
		GameObjectDefinition def = getDefinition();

		int width = def.getSizeX();
		int length = def.getSizeY();
		if (orientation == NORTH || orientation == SOUTH) {
			width = def.getSizeY();
			length = def.getSizeX();
		}

		int turnToX = from.getX();
		int turnToY = from.getY();

		Position position = getPosition();

		/* Within the width of the object */
		if (from.getX() >= position.getX() && from.getX() < position.getX() + width) {
			turnToY = position.getY();
		}

		/* Within the length of the object */
		if (from.getY() >= position.getY() && from.getY() < position.getY() + width) {
			turnToX = position.getX();
		}

		/* Upper left corner */
		if (from.getX() < position.getX() && from.getY() >= position.getY() + length) {
			turnToX = position.getX();
			turnToY = position.getY() + length - 1;
		}

		/* Upper right corner */
		if (from.getX() >= position.getX() + width && from.getY() >= position.getY() + length) {
			turnToX = position.getX() + width - 1;
			turnToY = position.getY() + length - 1;
		}

		/* Lower left corner */
		if (from.getX() < position.getX() + width && from.getY() < position.getY()) {
			turnToX = position.getX();
			turnToY = position.getY();
		}

		/* Lower right corner */
		if (from.getX() >= position.getX() + width && from.getY() < position.getY()) {
			turnToX = position.getX() + width - 1;
			turnToY = position.getY();
		}

		return new Position(turnToX, turnToY);
	}

	/**
	 * Returns {@code true} if and only if this object actually exists within
	 * the world otherwise {@code false}.
	 */
	public boolean exists() {
		return getDefinition().valid(getPosition());
	}

	@Override
	public int hashCode() {
		return type.getId() << 2 | orientation.getId() & 0x3F;
	}

	@Override
	public int size() {
		return getDefinition().getSize();
	}

	@Override
	public EntityType type() {
		return EntityType.GAME_OBJECT;
	}

}