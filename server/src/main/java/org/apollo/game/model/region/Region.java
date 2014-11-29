package org.apollo.game.model.region;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apollo.game.model.Entity;
import org.apollo.game.model.Entity.EntityCategory;
import org.apollo.game.model.Position;

/**
 * Represents a single region.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Region {

	/**
	 * The size of one side of the region array.
	 */
	public static final int SIZE = 256;

	/**
	 * The size of a region.
	 */
	public static final int REGION_SIZE = 64;

	/**
	 * The maximum level a floor can be.
	 */
	public static final int MAXIMUM_HEIGHT_LEVEL = 4;

	/**
	 * The tiles within the region.
	 */
	private final Tile[][] tiles = new Tile[MAXIMUM_HEIGHT_LEVEL][REGION_SIZE * REGION_SIZE];

	/**
	 * A set of entities within this region.
	 */
	private final Set<Entity> entities = new HashSet<>();

	/**
	 * Constructs a new {@link Region}.
	 */
	protected Region() {
		for (int height = 0; height < MAXIMUM_HEIGHT_LEVEL; height++) {
			for (int regionId = 0; regionId < REGION_SIZE * REGION_SIZE; regionId++) {
				tiles[height][regionId] = new Tile();
			}
		}
	}

	/**
	 * Adds an {@link Entity} to this region.
	 *
	 * @param entity The entity to add.
	 */
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	/**
	 * Removes an {@link Entity} from this region.
	 *
	 * @param entity The entity to remove.
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}

	/**
	 * Tests whether or not the {@link #entities} {@link Set} contains the
	 * specified {@link Entity}.
	 *
	 * @param entity The entity.
	 * @return {@code true} if and only if the entities set contains the
	 *         specified entity otherwise {@code false}.
	 */
	public boolean contains(Entity entity) {
		return entities.contains(entity);
	}

	/**
	 * Gets the entities within this region.
	 *
	 * @return A {@link Set} of entities in this region.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity> Set<T> getEntities() {
		return (Set<T>) entities;
	}

	/**
	 * Gets the entities of the specified {@link EntityCategory} within this
	 * region.
	 *
	 * @param category The category of entity to get.
	 * @return A {@link Set} of entities in this region.
	 */
	public <T extends Entity> Set<T> getEntities(EntityCategory category) {
		Set<T> entities = getEntities();
		return entities.stream().filter(e -> e.getCategory() == category).collect(Collectors.toSet());
	}

	/**
	 * Gets the entities on the specified {@link Position}.
	 *
	 * @param position The position.
	 * @return A {@link Set} of entities on the specified position.
	 */
	public <T extends Entity> Set<T> getEntities(Position position) {
		Set<T> entities = getEntities();
		return entities.stream().filter(e -> e.getPosition().equals(position)).collect(Collectors.toSet());
	}

	/**
	 * Gets the entities on the specified {@link Position} if they meet the
	 * specified {@link EntityCategory}.
	 *
	 * @param position The position.
	 * @param category The category of the entity.
	 * @return A {@link Set} of entities on the specified position.
	 */
	public <T extends Entity> Set<T> getEntities(Position position, EntityCategory category) {
		Set<T> entities = getEntities(position);
		return entities.stream().filter(e -> e.getCategory() == category).collect(Collectors.toSet());
	}

	/**
	 * Gets a single tile in this region from the specified height, x and y
	 * coordinates.
	 *
	 * @param height The height.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The tile in this region for the specified attributes.
	 */
	public Tile getTile(int height, int x, int y) {
		return tiles[height][x + y * REGION_SIZE];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (entities == null ? 0 : entities.hashCode());
		result = prime * result + Arrays.hashCode(tiles);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Region other = (Region) obj;
		if (entities == null) {
			if (other.entities != null) {
				return false;
			}
		} else if (!entities.equals(other.entities)) {
			return false;
		}
		if (!Arrays.deepEquals(tiles, other.tiles)) {
			return false;
		}
		return true;
	}

}