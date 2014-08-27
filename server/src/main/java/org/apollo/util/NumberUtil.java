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
     * Formats the specified number to be readable to the human eye. The number
     * may not be larger than that of 2<sup>31</sup>.
     *
     * @param number The number to format.
     * @return The formatted number, represented as a {@code String}.
     */
    public static String format(Number number) {
	String suffix = " KMB";
	StringBuilder bldr = new StringBuilder();
	NumberFormat decimalFormatter = new DecimalFormat("#,###.#");
	NumberFormat valueFormatter = NumberFormat.getInstance(Locale.US);
	int power = (int) Math.log10(number.doubleValue());
	double value = Math.floor(number.doubleValue() / (Math.pow(10, (power / 3) * 3)));
	bldr.append(decimalFormatter.format(value)).append(suffix.charAt(power / 3));
	bldr.append(" (").append(valueFormatter.format(number)).append(")");
	return bldr.toString();
    }

    /**
     * Suppresses the default-public constructor preventing this class from
     * being instantiated by other classes.
     *
     * @throws InstantiationError If this class is instantiated within itself.
     */
    private NumberUtil() {
	throw new InstantiationError("static-utility classes may not be instantiated.");
    }

}