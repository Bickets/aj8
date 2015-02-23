package org.apollo.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A static utility class which contains helper methods for numbers.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class NumberUtil {

	/**
	 * A format for representing thousands, millions and billions.
	 */
	private static final String FORMAT = " KMB";

	/**
	 * The default number decimal format.
	 */
	private static final NumberFormat DECIMAL_FORMAT = new DecimalFormat("#,###.#");

	/**
	 * The default number format.
	 */
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);

	/**
	 * Formats the specified number to be readable to the human eye. The number
	 * may not be larger than that of 2<sup>31</sup>.
	 *
	 * @param number The number to format.
	 * @return The formatted number, represented as a {@code String}.
	 */
	public static String format(Number number) {
		StringBuilder bldr = new StringBuilder();
		int power = (int) Math.log10(number.doubleValue());
		double value = Math.floor(number.doubleValue() / Math.pow(10, power / 3 * 3));
		bldr.append(DECIMAL_FORMAT.format(value)).append(FORMAT.charAt(power / 3));
		bldr.append(" (").append(NUMBER_FORMAT.format(number)).append(")");
		return bldr.toString();
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private NumberUtil() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}