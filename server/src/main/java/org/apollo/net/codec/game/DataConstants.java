package org.apollo.net.codec.game;

/**
 * A class holding data-related constants.
 *
 * @author Graham
 */
public final class DataConstants {

	/**
	 * An array of bit masks. The element {@code n} is equal to
	 * {@code 2<sup>n</sup> - 1}.
	 */
	public static final int[] BIT_MASK = new int[32];

	/**
	 * Initializes the {@link #BIT_MASK} array.
	 */
	static {
		for (int i = 0; i < BIT_MASK.length; i++) {
			BIT_MASK[i] = (1 << i) - 1;
		}
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws InstantiationError If this class is instantiated within itself.
	 */
	private DataConstants() {
		throw new InstantiationError("constant-container classes may not be instantiated.");
	}

}