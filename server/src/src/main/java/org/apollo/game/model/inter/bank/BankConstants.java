package org.apollo.game.model.inter.bank;

/**
 * Contains bank-related constants.
 *
 * @author Graham
 */
public final class BankConstants {

	/**
	 * The bank window id.
	 */
	public static final int BANK_WINDOW_ID = 5292;

	/**
	 * The sidebar id.
	 */
	public static final int SIDEBAR_ID = 2005;

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private BankConstants() {
		throw new UnsupportedOperationException("constant-container classes may not be instantiated.");
	}

}