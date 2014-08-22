package org.apollo.game.model;

/**
 * Holds {@link Inventory}-related constants.
 *
 * @author Graham
 */
public final class InventoryConstants {

    /**
     * The capacity of the bank.
     */
    public static final int BANK_CAPACITY = 352;

    /**
     * The capacity of the inventory.
     */
    public static final int INVENTORY_CAPACITY = 28;

    /**
     * The capacity of the equipment inventory.
     */
    public static final int EQUIPMENT_CAPACITY = 14;

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private InventoryConstants() {
	throw new InstantiationError("constant-container classes may not be instantiated.");
    }

}