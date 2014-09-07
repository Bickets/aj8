package org.apollo.game.attribute;

import static java.util.Objects.requireNonNull;

/**
 * Represents a key for an {@link Attribute<T>}
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <T> The attributes values type reference.
 */
public final class AttributeKey<T> {

    /**
     * Represents the name of this attribute key. An attributes key is an
     * identifier that encapsulates this attributes name. This key is used for
     * representing an attribute through some collection.
     */
    private final String name;

    /**
     * Returns the initial value of the specified type reference.
     */
    private final T initial;

    /**
     * Constructs a new {@link AttributeKey<T>} with the specified name.
     *
     * @param name The name of this attribute, may not be {@code null}.
     * @param initial The initial value of the specified type reference.
     * @throws NullPointerException If the specified name or initial is
     *             {@code null}.
     *
     *             <p>
     *             This constructor should not be invoked directly, use
     *             {@link #valueOf(String)} instead!
     *             </p>
     */
    private AttributeKey(String name, T initial) {
	this.name = requireNonNull(name);
	this.initial = initial;
    }

    /**
     * Returns this attribute keys name.
     */
    protected String getName() {
	return name;
    }

    /**
     * Returns the initial value of the specified type reference.
     */
    protected T getInitial() {
	return initial;
    }

    /**
     * Constructs a new {@link AttributeKey<T>} with the specified name.
     *
     * @param name The name of this attribute, may not be {@code null}.
     * @param initial The initial value of the specified type reference.
     * @throws NullPointerException If the specified name is {@code null}.
     * @see {@link #AttributeKey(String)}
     */
    public static <T> AttributeKey<T> valueOf(String name, T initial) {
	return new AttributeKey<T>(name, initial);
    }

}