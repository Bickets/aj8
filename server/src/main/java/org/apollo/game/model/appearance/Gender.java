package org.apollo.game.model.appearance;

/**
 * An enumeration containing the two genders (male and female).
 *
 * @author Graham
 */
public enum Gender {

	/**
	 * The male gender.
	 */
	MALE(0),

	/**
	 * The female gender.
	 */
	FEMALE(1);

	/**
	 * An integer representation used by the client.
	 */
	private final int intValue;

	/**
	 * Creates the gender.
	 *
	 * @param intValue The integer representation.
	 */
	private Gender(int intValue) {
		this.intValue = intValue;
	}

	/**
	 * Converts this gender to an integer.
	 *
	 * @return The integer representation used by the client.
	 */
	public int toInteger() {
		return intValue;
	}

	/**
	 * Returns the gender value for the specified int value of the gender.
	 *
	 * @param intValue The integer value of the gender.
	 * @return The gender for the specified integer value.
	 * @throws IllegalArgumentException If the specified integer value does not
	 *             represent a gender.
	 */
	public static Gender valueOf(int intValue) {
		switch (intValue) {
		case 0:
			return MALE;
		case 1:
			return FEMALE;
		}
		throw new IllegalArgumentException("int value " + intValue + " does not represent the value of any gender.");
	}

}