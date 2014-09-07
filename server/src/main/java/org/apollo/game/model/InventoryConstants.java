package org.apollo.game.model;

/**
 * Holds {@link Inventory}-related constants.
 *
 * @author Graham
 */
public final class InventoryConstants {

	/**
	 * The inventory interface id.
	 */
	public static final int INVENTORY_ID = 3214;

	/**
	 * The trade inventory id.
	 */
	public static final int TRADE_INVENTORY_ID = 3415;

	/**
	 * The bank inventory id.
	 */
	public static final int BANK_INVENTORY_ID = 5382;

	/**
	 * The sidebar inventory id.
	 */
	public static final int BANK_SIDEBAR_INVENTORY_ID = 2006;

	/**
	 * The trade inventory id for the other player.
	 */
	public static final int OTHER_TRADE_INVENTORY_ID = TRADE_INVENTORY_ID + 1;

	/**
	 * The inventory displayed on your sidebar.
	 */
	public static final int TRADE_SIDEBAR_INVENTORY_ID = 3322;

	/**
	 * The equipment inventory id.
	 */
	public static final int EQUIPMENT_INVENTORY_ID = 1688;

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
	 * The capacity of the trade inventory.
	 */
	public static final int TRADE_CAPACITY = 28;

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