package org.apollo.game.attribute;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a map of {@link AttributeKey<T>}s to {@link Attribute<T>}s.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class AttributeMap {

    /**
     * A map of attribute keys to attributes.
     */
    private final Map<AttributeKey<?>, Attribute<?>> attrs = new HashMap<>();

    /**
     * Returns the value of the attribute which is represented by the specified
     * {@link AttributeKey<T>}, if no key exists the initial value is returned
     * and the key is added.
     *
     * @param <T> The attributes value type reference.
     * @param key The attribute key, may not be {@code null}.
     * @return The value of the attribute.
     * @unchecked This method declares unchecked as the cast from
     *            {@code Attribute<?>} to {@code Attribute<T>} is unchecked.
     *            This method is safe across all compilers.
     * @throws NullPointerException If the specified key is {@code null}.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(AttributeKey<T> key) {
	requireNonNull(key);

	if (!contains(key)) {
	    return setAndGet(key, key.getInitial());
	}

	/* Will never be {@code null}. */
	Attribute<T> attr = (Attribute<T>) attrs.get(key);
	return attr.getValue();
    }

    /**
     * Sets a specified attribute key to a specified value and returns the
     * value.
     *
     * @param <T> The attributes value type reference.
     * @param key The attribute key, may not be {@code null}.
     * @param value The value of the attribute.
     * @return The value of the attribute.
     * @throws NullPointerException If the specified key or value is
     *             {@code null}.
     */
    public <T> T setAndGet(AttributeKey<T> key, T value) {
	requireNonNull(key);

	attrs.put(key, new Attribute<T>(key, value));
	return value;
    }

    /**
     * Sets a specified attribute key to a specified value.
     *
     * @param <T> The attributes value type reference.
     * @param key The attribute key, may not be {@code null}.
     * @param value The value of the attribute.
     * @throws NullPointerException If the specified key or value is
     *             {@code null}.
     */
    public <T> void set(AttributeKey<T> key, T value) {
	setAndGet(key, value); /* discard value, don't need it here. */
    }

    /**
     * Returns a flag which denotes whether or not an attribute key exists
     * within the {@link #attrs} map.
     *
     * @param <T> The attributes value type reference.
     * @param key The attribute key, may not be {@code null}.
     * @return {@code true} if and only if the specified key exists within the
     *         attribute map, otherwise {@code false}.
     * @throws NullPointerException If the specified key is {@code null}.
     */
    public <T> boolean contains(AttributeKey<T> key) {
	requireNonNull(key);

	return attrs.containsKey(key);
    }

}