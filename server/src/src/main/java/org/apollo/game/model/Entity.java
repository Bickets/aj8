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
	protected Position position;

	/**
	 * The world this entity is in.
	 */
	protected final World world;

	/**
	 * Constructs a new {@link Entity} with the specified position.
	 *
	 * @param position The position of this entity.
	 * @param world The world this entity is in.
	 */
	protected Entity(Position position, World world) {
		this.position = position;
		this.world = world;
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
	 * Returns the world this entity is in.
	 */
	public World getWorld() {
		return world;
	}

}