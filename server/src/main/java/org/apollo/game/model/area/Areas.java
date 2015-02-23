package org.apollo.game.model.area;

import org.apollo.game.model.World;

/**
 * A static-utility class containing specific areas within the {@link World}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Areas {

	/**
	 * A {@link QuadArea} representing the area the wilderness is in.
	 */
	public static final Area WILDERNESS_AREA = new QuadArea(2945, 3522, 3390, 3972);

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated
	 *             within itself.
	 */
	private Areas() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}