package org.apollo.game.model.inter.trade;

/**
 * Represents constant values related to the trade inventory.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TradeConstants {

	/**
	 * The main trade window id.
	 */
	public static final int TRADE_WINDOW_ID = 3323;

	/**
	 * The sidebar interface id.
	 */
	public static final int SIDEBAR_ID = 3321;

	/**
	 * The confirm trade window id.
	 */
	public static final int CONFIRM_TRADE_WINDOW_ID = 3443;

	/**
	 * The confirm sidebar interface id.
	 */
	public static final int CONFIRM_SIDEBAR_ID = 3213;

	/**
	 * The title of the trade, where the interface shows who you are trading
	 * with.
	 */
	public static final int TRADING_WITH_MESSAGE_ID = 3417;

	/**
	 * The accept title id, where the interface shows that you have accepted and
	 * that you're waiting for the other player to accept.
	 */
	public static final int FIRST_SCREEN_MESSAGE_ID = 3431;

	/**
	 * The confirm accept title id, on the second trade screen, where the
	 * interface shows that you have accepted and that you're waiting for the
	 * other player to accept.
	 */
	public static final int SECOND_SCREEN_TITLE_ID = 3535;

	/**
	 * The title id which shows the items you have traded.
	 */
	public static final int VALUES_MESSAGE_ID = 3557;

	/**
	 * The title id which shows the items that your trade partner have traded,
	 */
	public static final int OTHER_VALUES_MESSAGE_ID = VALUES_MESSAGE_ID + 1;

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated
	 *             within itself.
	 */
	private TradeConstants() {
		throw new UnsupportedOperationException("constant-container classes may not be instantiated");
	}

}