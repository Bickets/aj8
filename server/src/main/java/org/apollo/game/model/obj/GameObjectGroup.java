package org.apollo.game.model.obj;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the group a {@link GameObject} belongs to.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum GameObjectGroup {

	/**
	 * The wall object group.
	 */
	WALL(0),

	/**
	 * The wall decoration group.
	 */
	WALL_DECORATION(1),

	/**
	 * Unknown group 2.
	 */
	GROUP_2(2),

	/**
	 * Unknown group 3.
	 */
	GROUP_3(3);

	/**
	 * Represents the object groups for every type of object based on its index.
	 */
	public static final int[] OBJECT_GROUPS = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2,
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };

	/**
	 * The id of this object group.
	 */
	private final int id;

	/**
	 * Constructs a new {@link GameObjectGroup} with the specified id.
	 *
	 * @param id The if of the object group.
	 */
	private GameObjectGroup(int id) {
		this.id = id;
	}

	/**
	 * Returns the id of this group.
	 */
	public final int getId() {
		return id;
	}

	/**
	 * A mutable {@link Map} of {@code int} keys to {@link GameObjectGroup}
	 * values.
	 */
	private static final Map<Integer, GameObjectGroup> values = new HashMap<>();

	/**
	 * Populates the {@link #values} cache.
	 */
	static {
		for (GameObjectGroup group : values()) {
			values.put(group.getId(), group);
		}
	}

	/**
	 * Returns a {@link GameObjectGroup} wrapped in an {@link Optional} for the
	 * specified {@code type}.
	 * 
	 * @param type The game object type.
	 * @return The optional game object group.
	 */
	public static Optional<GameObjectGroup> valueOf(GameObjectType type) {
		int id = OBJECT_GROUPS[type.getId()];
		return valueOf(id);
	}

	/**
	 * Returns a {@link GameObjectGroup} wrapped in an {@link Optional} for the
	 * specified {@code id}.
	 * 
	 * @param id The game object group id.
	 * @return The optional game object group.
	 */
	private static Optional<GameObjectGroup> valueOf(int id) {
		return Optional.ofNullable(values.get(id));
	}

}