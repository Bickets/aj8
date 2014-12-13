package org.apollo.util;

/**
 * A utility-class for dealing with math functions not native to the
 * {@link Math} class.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class MathUtil {

	/**
	 * Returns the greater of three {@code int} values. That is, the result is
	 * the argument closer to the value of {@link Integer#MAX_VALUE}. If the
	 * arguments have the same value, the result is that same value.
	 *
	 * @param a The first {@code int} value to compare.
	 * @param b The second {@code int} value to compare.
	 * @param c The third {@code int} value to compare.
	 * @return The greater of the three {@code int} values.
	 */
	public static int max(int a, int b, int c) {
		int first = Math.max(a, b);
		int value = Math.max(first, c);
		return value;
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws InstantiationError If this class is instantiated within itself.
	 */
	private MathUtil() {
		throw new InstantiationError("static-utility classes may not be instantiated.");
	}

}