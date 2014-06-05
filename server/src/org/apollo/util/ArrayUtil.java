package org.apollo.util;

public final class ArrayUtil {

    public static int[] parseIntegerArray(String[] array) {
	int[] intArray = new int[array.length];

	for (int index = 0; index < intArray.length; index++) {
	    String value = array[index];

	    if (value.matches("\\d+")) {
		intArray[index] = Integer.parseInt(value);
		continue;
	    }

	    throw new NumberFormatException("[index, value] [" + index + ", " + value + "] is not a number!");
	}

	return intArray;
    }

}
