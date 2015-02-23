package org.apollo.util;

import java.util.List;

import com.google.common.primitives.Chars;

/**
 * A utility class which contains language-related methods.
 *
 * @author Graham
 */
public final class LanguageUtil {

	/**
	 * A {@link List} of vowel {@code chars}.
	 */
	private static final List<Character> VOWELS = Chars.asList('a', 'e', 'i', 'o', 'u');

	/**
	 * Returns the indefinite article for the specified {@code String}.
	 * 
	 * @param string The {@code String} to find the indefinite article for.
	 * @param join A flag to determine whether or not to join the specified
	 *            {@code String} and the returned indefinite article.
	 * @return <b>Only the indefinite article if {@code join} is {@code false}
	 *         </b>. Otherwise the indefinite article followed by the specified
	 *         {@link String} will be joined, delimited by a space and returned.
	 */
	public static String getIndefiniteArticle(String string, boolean join) {
		char first = string.toLowerCase().charAt(0);
		String article = isVowel(first) ? "an" : "a";
		return article + (join ? " " + string : "");
	}

	/**
	 * Returns a flag denoting whether or not the specified {@code char} is a
	 * vowel.
	 * 
	 * @param character The character to check.
	 * @return {@code true} if and only if the specified {@code char} is a
	 *         vowel.
	 */
	public static boolean isVowel(char character) {
		return VOWELS.contains(character);
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated
	 *             within itself.
	 */
	private LanguageUtil() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}