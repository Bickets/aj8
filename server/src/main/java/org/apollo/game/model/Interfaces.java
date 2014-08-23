package org.apollo.game.model;

import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inv.SynchronizationInventoryListener;

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
	    1644, 5608, 1151, -1, 5065, 5715, 2449, 904, 147, 962, };

    /**
     * Represents the option clicked on some drop-down interface menu.
     *
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     */
    public enum InterfaceOption {
	/**
	 * The first drop down menu option.
	 */
	OPTION_ONE(0),

	/**
	 * The second drop down menu option.
	 */
	OPTION_TWO(1),

	/**
	 * The third drop down menu option.
	 */
	OPTION_THREE(2),

	/**
	 * The fourth drop down menu option.
	 */
	OPTION_FOUR(3),

	/**
	 * The fifth drop down menu option.
	 */
	OPTION_FIVE(4);

	/**
	 * Represents the id of this option.
	 */
	private final int id;

	/**
	 * Constructs a new {@link InterfaceOption} with the specified id.
	 *
	 * @param id The id of this option.
	 */
	private InterfaceOption(int id) {
	    this.id = id;
	}

	/**
	 * Returns the id of this option.
	 */
	public final int getId() {
	    return id;
	}

	/**
	 * Converts an option to an amount.
	 *
	 * @param option The option.
	 * @return The amount.
	 * @throws IllegalArgumentException if the option is not legal.
	 */
	public static int optionToAmount(InterfaceOption option) {
	    switch (option) {
	    case OPTION_ONE:
		return 1;
	    case OPTION_TWO:
		return 5;
	    case OPTION_THREE:
		return 10;
	    case OPTION_FOUR:
		return Integer.MAX_VALUE;
	    case OPTION_FIVE:
		return -1;
	    }
	    throw new IllegalArgumentException();
	}
    }

    /**
     * Returns an {@link Inventory} for the specified interface id.
     *
     * @param player The player who owns the inventory.
     * @param id The interface id.
     * @return The inventory for the specified interface id, {@code null} if not
     *         supported or does not exist.
     * @throws IllegalArgumentException If the specified interface id does not
     *             exist or is not supported.
     */
    public static Inventory getInventoryForInterface(Player player, int id) {
	switch (id) {
	case SynchronizationInventoryListener.INVENTORY_ID:
	case BankConstants.SIDEBAR_INVENTORY_ID:
	    return player.getInventory();
	case SynchronizationInventoryListener.EQUIPMENT_ID:
	    return player.getEquipment();
	case BankConstants.BANK_INVENTORY_ID:
	    return player.getBank();
	}
	throw new IllegalArgumentException("Inventory for interface: " + id + " not supported!");
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
	return id == BankConstants.BANK_INVENTORY_ID;
    }

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private Interfaces() {
	throw new InstantiationError("constant-container classes may not be instantiated.");
    }

}