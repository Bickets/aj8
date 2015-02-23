package org.apollo.game.model.inter;

import org.apollo.game.model.Entity;
import org.apollo.game.model.inv.InventoryConstants;

/**
 * A common static-utility class with helper methods and constants for
 * interfaces.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Interfaces {

	/**
	 * The character design interface id.
	 */
	public static final int CHARACTER_DESIGN_INTERFACE_ID = 3559;

	/**
	 * The tab interface ids. 6299 = music tab, music disabled 4445 = settings
	 * tab, music disabled 12855 = ancients magic
	 */
	public static final int[] TAB_INTERFACE_IDS = { 2423, 3917, 638, 3213,
			1644, 5608, 1151, -1, 5065, 5715, 2449, 904, 147, 962 };

	/**
	 * Represents an interface context menu action on some {@link Entity}.
	 * 
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	public enum InteractContextMenuAction {

		/**
		 * The first context menu action.
		 */
		ACTION_ONE,

		/**
		 * The second context menu action.
		 */
		ACTION_TWO,

		/**
		 * The third context menu action.
		 */
		ACTION_THREE,

		/**
		 * The fourth context menu action.
		 */
		ACTION_FOUR,

		/**
		 * The fifth context menu action.
		 */
		ACTION_FIVE
	}

	/**
	 * Represents an inventory withdraw or deposit option.
	 * 
	 * @author Ryley Kimmel <ryley.kimmel@live.com>
	 */
	public enum InventoryAmountOption {

		/**
		 * The first menu option, withdraw or deposit 1 item.
		 */
		OPTION_ONE(1),

		/**
		 * The second menu option, withdraw or deposit 5 items.
		 */
		OPTION_FIVE(5),

		/**
		 * The third menu option, withdraw or deposit 10 items.
		 */
		OPTION_TEN(10),

		/**
		 * The fourth menu option, withdraw or deposit all items.
		 */
		OPTION_ALL(Integer.MAX_VALUE),

		/**
		 * The fifth menu option, withdraw or deposit <tt>n</tt> items.
		 */
		OPTION_X(-1);

		/**
		 * The value this option represents.
		 */
		private final int value;

		/**
		 * Constructs a new {@link InventoryAmountOption} with the specified
		 * value.
		 * 
		 * @param value The value this options represents.
		 */
		private InventoryAmountOption(int value) {
			this.value = value;
		}

		/**
		 * Returns the value this option represents.
		 */
		public final int getValue() {
			return value;
		}

	}

	/**
	 * Returns {@code true} if the insert option is permitted for the specified
	 * interface id.
	 *
	 * @param id The interface id.
	 * @return {@code true} if inserting is permitted for the specified
	 *         interface id, otherwise return {@code false}.
	 */
	public static boolean insertPermitted(int id) {
		return id == InventoryConstants.BANK_INVENTORY_ID;
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated
	 *             within itself.
	 */
	private Interfaces() {
		throw new UnsupportedOperationException("constant-container classes may not be instantiated.");
	}

}