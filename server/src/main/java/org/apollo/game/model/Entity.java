package org.apollo.game.model;

import org.apollo.game.model.obj.GameObject;

/**
 * Represents an entity within the game world.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public abstract class Entity {

	/**
	 * Represents the category this entity falls under.
	 *
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	public enum EntityCategory {
		/**
		 * Represents a {@link Player} entity.
		 */
		PLAYER,

		/**
		 * Represents a {@link Mob} entity.
		 */
		MOB,

		/**
		 * Represents a {@link GroundItem} entity.
		 */
		GROUND_ITEM,

		/**
		 * Represents a {@link GameObject} entity.
		 */
		GAME_OBJECT
	}

	/**
	 * The current position of this entity.
	 */
	private Position position;

	/**
	 * Constructs a new {@link Entity} with the specified position.
	 *
	 * @param position The position of this entity.
	 */
	public Entity(Position position) {
		setPosition(position);
	}

	/**
	 * Returns the category of this entity.
	 */
	public abstract EntityCategory getCategory();

	/**
	 * Returns the size of this entity in tiles.
	 */
	public abstract int getSize();

	/**
	 * Returns the position of this entity.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position of this entity.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

}