package org.apollo.util;

import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * A static utility container for Google JSON serialization.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GsonUtil {

	/**
	 * Constructs a shared {@link Gson} object.
	 */
	private static final Gson GSON = create();

	/**
	 * Builds a {@link Gson} object that pretty-prints JSON output.
	 *
	 * @return An instance of {@link Gson}, never {@code null}.
	 */
	public static Gson create() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		return builder.create();
	}

	/**
	 * Returns this {@link Gson} object.
	 */
	public static Gson getGson() {
		return GSON;
	}

	/**
	 * Turns a {@link Object} into a JSON {@code String}.
	 *
	 * @param object The object.
	 * @return The JSON <code>String</code>.
	 */
	public static String toJson(Object object) {
		return GSON.toJson(object);
	}

	/**
	 * Returns a type reference from the specified JSON <code>String</code>.
	 *
	 * @param json The JSON <code>String</code>.
	 * @param type The type reference class.
	 * @return The normal output.
	 */
	public static <T> T fromJson(String json, Class<T> type) {
		return GSON.fromJson(json, type);
	}

	/**
	 * Returns a type reference from the specified JSON <code>String</code>.
	 *
	 * @param json The JSON <code>String</code>.
	 * @param type The type reference class.
	 * @return The normal output.
	 */
	public static <T> T fromJson(String json, Type type) {
		return GSON.fromJson(json, type);
	}

	/**
	 * Returns a type reference from the specified {@link Reader}.
	 *
	 * @param reader The reader.
	 * @param type The type reference class.
	 * @return The normal output.
	 */
	public static <T> T fromJson(Reader reader, Class<T> type) {
		return GSON.fromJson(reader, type);
	}

	/**
	 * Returns a type reference from the specified {@link Reader}.
	 *
	 * @param reader The reader.
	 * @param type The type reference class.
	 * @return The normal output.
	 */
	public static <T> T fromJson(Reader reader, Type type) {
		return GSON.fromJson(reader, type);
	}

	/**
	 * Returns a complex type reference derived from the specified type
	 * reference. It is under assumption that the specified type reference is
	 * the 'from' type and will be used to create a {@link TypeToken} to return
	 * this information from the {@link #fromJson(Reader, Type)} method.
	 *
	 * @param reader The reader.
	 * @return The normal output.
	 */
	public static <T> T fromJson(Reader reader) {
		TypeToken<T> token = new TypeToken<T>() {
		};
		return fromJson(reader, token.getType());
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated
	 *             within itself.
	 */
	private GsonUtil() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}