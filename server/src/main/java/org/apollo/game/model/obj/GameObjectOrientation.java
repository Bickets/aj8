package org.apollo.game.model.obj;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the orientation of a {@link GameObject}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public enum GameObjectOrientation {

	/**
	 * The north orientation.
	 */
	NORTH(1),

	/**
	 * The south orientation.
	 */
	SOUTH(3),

	/**
	 * The east orientation.
	 */
	EAST(2),

	/**
	 * The west orientation.
	 */
	WEST(0);

	/**
	 * The id of the orientation.
	 */
	private final int id;

	/**
	 * Constructs a new {@link GameObjectOrientation} with the specified
	 * orientation id.
	 *
	 * @param id The orientation id.
	 */
	private GameObjectOrientation(int id) {
		this.id = id;
	}

	/**
	 * Returns the id of this orientation.
	 */
	public final int getId() {
		return id;
	}

	/**
	 * A mutable {@link Map} of {@code int} keys to
	 * {@link GameObjectOrientation} values.
	 */
	private static final Map<Integer, GameObjectOrientation> values = new HashMap<>();

	/**
	 * Populates the {@link #values} cache.
	 */
	static {
		for (GameObjectOrientation orientation : values()) {
			values.put(orientation.getId(), orientation);
		}
	}

	/**
	 * Returns a {@link GameObjectOrientation} wrapped in an {@link Optional}
	 * for the specified {@code id}.
	 * 
	 * @param id The game object orientation id.
	 * @return The optional game object orientation.
	 */
	public static Optional<GameObjectOrientation> valueOf(int id) {
		return Optional.ofNullable(values.get(id));
	}

}