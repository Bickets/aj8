package org.apollo.game.model;

import java.util.Set;

import org.apollo.game.model.area.Area;
import org.apollo.game.model.grounditem.GroundItem;
import org.apollo.game.model.obj.GameObject;
import org.apollo.game.model.region.Region;

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
	 * Returns the region this {@link Entity} is currently in.
	 */
	public final Region getRegion() {
		return world.getRegionRepository().getRegion(getPosition());
	}

	/**
	 * Returns a {@link Set} of surrounding {@link Entity}s
	 */
	public final <T extends Entity> Set<T> getSurrounding() {
		return getRegion().getEntities();
	}

	/**
	 * Returns a {@link Set} of surrounding {@link Entity}s based on their
	 * {@link EntityCategory}.
	 *
	 * @param category The category of the entity that is surrounding this
	 *            entity.
	 * @return A {@link Set} of {@link Entity}s of the specified category.
	 */
	public final <T extends Entity> Set<T> getSurrounding(EntityCategory category) {
		return getRegion().getEntities(category);
	}

	/**
	 * Tests whether or not this {@link Entity} is within an {@link Area} with
	 * no padding.
	 * 
	 * @param area The {@link Area} to test if this entity is within.
	 * @return {@code true} if and only if this entity is within the specified
	 *         {@code area} otherwise {@code false}.
	 */
	public boolean withinArea(Area area) {
		return withinArea(area, 0);
	}

	/**
	 * Tests whether or not this {@link Entity} is within an {@link Area}.
	 * 
	 * @param area The {@link Area} to test if this entity is within.
	 * @param padding The padding around the area.
	 * @return {@code true} if and only if this entity is within the specified
	 *         {@code area} otherwise {@code false}.
	 */
	public boolean withinArea(Area area, int padding) {
		return area.withinArea(position.getX(), position.getY(), padding);
	}

	/**
	 * Returns the position of this entity.
	 */
	public final Position getPosition() {
		return position;
	}

	/**
	 * Returns the world this entity is in.
	 */
	public final World getWorld() {
		return world;
	}

}